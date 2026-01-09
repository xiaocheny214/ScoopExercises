package com.ScoopLink.score;

import com.ScoopLink.manageQuestion.analysisQuestion.dto.AnalysisQuestion;
import com.ScoopLink.manageQuestion.analysisQuestion.server.AnalysisQuestionServer;
import com.ScoopLink.manageQuestion.essayQuestions.dto.EssayQuestion;
import com.ScoopLink.manageQuestion.essayQuestions.server.EssayQuestionServer;
import com.ScoopLink.manageQuestion.multipleChoiceQuestion.dto.MultipleChoiceQuestion;
import com.ScoopLink.manageQuestion.multipleChoiceQuestion.server.MultipleChoiceQuestionServer;
import com.ScoopLink.manageQuestion.papers.dto.Paper;
import com.ScoopLink.manageQuestion.papers.server.PaperServer;
import com.ScoopLink.manageQuestion.question.dto.QuestionType;
import com.ScoopLink.manageQuestion.question.server.QuestionTypeServer;
import com.ScoopLink.scoreCalculation.dto.Score;
import com.ScoopLink.scoreCalculation.server.ScoreServer;
import com.ScoopLink.userAnswers.dto.UserAnswer;
import com.ScoopLink.userAnswers.dto.submitPaper;
import com.ScoopLink.userAnswers.server.SubmitAnswer;
import com.ScoopLink.userAnswers.server.UserAnswersServer;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ScoreCalculationService implements SubmitAnswer {

    @Resource
    private MultipleChoiceQuestionServer multipleChoiceQuestionServer;
    
    @Resource
    private EssayQuestionServer essayQuestionServer;
    
    @Resource
    private AnalysisQuestionServer analysisQuestionServer;
    
    @Resource
    private UserAnswersServer userAnswersServer;
    
    @Resource
    private ScoreServer scoreServer;

    @Resource
    private PaperServer paperServer;

    @Resource
    private QuestionTypeServer questionTypeServer;


    @Override
    @Transactional
    public UserAnswer SubmitAnswer(UserAnswer userAnswer) {
        if(userAnswer == null || userAnswer.getUserId() == null || userAnswer.getPaperId() == null || userAnswer.getQuestionId() == null || userAnswer.getQuestionType() == null || userAnswer.getUserAnswer() == null) {
            return null;
        }
        // 检查问题类型是否存在
        if (!isValidQuestionType(userAnswer.getQuestionType())) {
            return null;
        }

        String correctAnswer =getCorrectAnswer(userAnswer.getQuestionId(), userAnswer.getQuestionType());
        if(correctAnswer == null) {
               return null;
        }
        //校验用户答题是否正确
        boolean isCorrect =checkAnswer(userAnswer.getUserAnswer(), correctAnswer, userAnswer.getQuestionType());
        
        //计算用户分数
        int questionScore = getQuestionScore(userAnswer.getQuestionId(), userAnswer.getQuestionType());
        int scoreObtained = isCorrect ? questionScore : 0;
        //更新用户答题记录中的得分和正确性状态
        userAnswer.setScoreObtained(scoreObtained);
        userAnswer.setCorrect(isCorrect);
        userAnswer.setAnswerTime(LocalDateTime.now());
        //更新用户答题记录
        updateUserAnswer(userAnswer);
        //创建或更新分数记录表
        createOrUpdateScoreRecord(userAnswer.getUserId(), userAnswer.getPaperId(), scoreObtained, isCorrect);
        return userAnswer;
    }


    /**
     * 批量提交用户答案
     */
    @Override
    @Transactional
    public UserAnswer SubmitAnswers(List<UserAnswer> userAnswerList) {
        if(userAnswerList == null || userAnswerList.isEmpty()) {
            return null;
        }
        //过滤掉无效的用户答案
        List<UserAnswer>validUserAnswers = userAnswerList.stream()
                .filter(userAnswer -> userAnswer != null &&
                        userAnswer.getUserId() != null &&
                        userAnswer.getQuestionId() != null &&
                        userAnswer.getQuestionType() != null &&
                        userAnswer.getUserAnswer() != null &&
                        isValidQuestionType(userAnswer.getQuestionType()))
                .collect(Collectors.toList());
        if(validUserAnswers.isEmpty()) {
            return null;
        }
        //批量提交用户答案，找到得分最高的作为最终结果
        UserAnswer userAnswer = validUserAnswers.stream().map(this::SubmitAnswer).toList().stream().sorted((a, b) -> a.getScoreObtained().compareTo(b.getScoreObtained())).findFirst().orElse(null);
        return userAnswer;
    }

    @Override
    public Score SubmitPaper(submitPaper submitPaper) {
        //校验用户是否提交了所有题目
        try {
            List<Long>answerIds = submitPaper.getAnswerIds();
            boolean allAnswered = answerIds.stream().allMatch(answerId -> userAnswersServer.GetUserAnswer(answerId) != null);
            if(!allAnswered) {
               throw new IllegalAccessException("用户未回答所有题目");
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        //通过试卷id来查询分数
        Score score = scoreServer.GetScoreByPaperId(submitPaper.getPaperId());
        return score != null ? score : new Score();
    }

    /**
     * 根据问题ID和问题类型获取正确答案
     */
    private String getCorrectAnswer(Long questionId, String questionType) {
        switch (questionType) {
            case "MULTIPLE_CHOICE":
                MultipleChoiceQuestion mcq = multipleChoiceQuestionServer.GetMultipleChoiceQuestion(questionId);
                return mcq != null ? mcq.getCorrectAnswer() : null;
            case "ESSAY":
                EssayQuestion eq = essayQuestionServer.GetEssayQuestion(questionId);
                return eq != null ? eq.getReferenceAnswer() : null;
            case "ANALYSIS":
                AnalysisQuestion aq = analysisQuestionServer.GetAnalysisQuestion(questionId);
                return aq != null ? aq.getCorrectAnswer() : null;
            default:
                return null;
        }
    }

    /**
     * 检查用户答案是否正确
     */
    private boolean checkAnswer(String userAnswer, String correctAnswer, String questionType) {
        if (userAnswer == null || correctAnswer == null) {
            return userAnswer == correctAnswer;
        }

        switch (questionType) {
            case "MULTIPLE_CHOICE":
                // 选择题：严格匹配答案，支持多个选项（如"A,C,D"）
                return compareMultipleChoiceAnswers(userAnswer, correctAnswer);

            case "ESSAY":
                // 解答题：使用相似度匹配，关键词匹配，以及部分匹配
                return compareEssayAnswers(userAnswer, correctAnswer);

            case "ANALYSIS":
                // 分析题：使用更复杂的评分逻辑，考虑答案的完整性
                return compareAnalysisAnswers(userAnswer, correctAnswer);

            default:
                return false;
        }
    }

    /**
     * 比较选择题答案
     * @param userAnswer 用户答案
     * @param correctAnswer 正确答案
     * @return 是否正确
     */
    private boolean compareMultipleChoiceAnswers(String userAnswer, String correctAnswer) {
        // 去除空格并转换为小写进行比较
        String userAns = userAnswer.trim().toLowerCase().replaceAll("\\s+", "");
        String correctAns = correctAnswer.trim().toLowerCase().replaceAll("\\s+", "");

        // 如果答案中包含逗号，表示多选题，需要比较选项集合
        if (userAns.contains(",") || correctAns.contains(",")) {
            // 将答案分割成选项列表并排序后比较
            Set<String> userOptions = Arrays.stream(userAns.split(","))
                    .map(String::trim)
                    .collect(Collectors.toSet());
            Set<String> correctOptions = Arrays.stream(correctAns.split(","))
                    .map(String::trim)
                    .collect(Collectors.toSet());

            return userOptions.equals(correctOptions);
        } else {
            // 单选题直接比较
            return userAns.equals(correctAns);
        }
    }

    /**
     * 比较解答题答案
     * @param userAnswer 用户答案
     * @param correctAnswer 正确答案
     * @return 是否正确
     */
    private boolean compareEssayAnswers(String userAnswer, String correctAnswer) {
        // 基础相似度检查
        double similarity = calculateSimilarity(userAnswer, correctAnswer);

        // 如果相似度超过阈值（如70%），认为答案正确
        return similarity >= 0.7;
    }

    /**
     * 比较分析题答案
     * @param userAnswer 用户答案
     * @param correctAnswer 正确答案
     * @return 是否正确
     */
    private boolean compareAnalysisAnswers(String userAnswer, String correctAnswer) {
        // 分析题需要更全面的评估，包括关键词匹配和相似度
        double similarity = calculateSimilarity(userAnswer, correctAnswer);
        boolean containsKeywords = containsKeyWords(userAnswer, correctAnswer);

        // 综合判断：相似度超过阈值且包含关键信息
        return similarity >= 0.6 || containsKeywords;
    }

    /**
     * 计算两个字符串的相似度（使用编辑距离算法）
     * @param str1 字符串1
     * @param str2 字符串2
     * @return 相似度（0.0-1.0）
     */
    private double calculateSimilarity(String str1, String str2) {
        if (str1.equals(str2)) {
            return 1.0;
        }

        // 使用Levenshtein距离算法计算相似度
        int maxLength = Math.max(str1.length(), str2.length());
        if (maxLength == 0) {
            return 1.0;
        }

        int distance = computeLevenshteinDistance(str1, str2);
        return 1.0 - ((double) distance / maxLength);
    }

    /**
     * 计算Levenshtein编辑距离
     * @param str1 字符串1
     * @param str2 字符串2
     * @return 编辑距离
     */
    private int computeLevenshteinDistance(String str1, String str2) {
        int[][] dp = new int[str1.length() + 1][str2.length() + 1];

        for (int i = 0; i <= str1.length(); i++) {
            dp[i][0] = i;
        }

        for (int j = 0; j <= str2.length(); j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= str1.length(); i++) {
            for (int j = 1; j <= str2.length(); j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]);
                }
            }
        }

        return dp[str1.length()][str2.length()];
    }

    /**
     * 检查用户答案是否包含正确答案中的关键词
     * @param userAnswer 用户答案
     * @param correctAnswer 正确答案
     * @return 是否包含关键词
     */
    private boolean containsKeyWords(String userAnswer, String correctAnswer) {
        // 提取正确答案中的关键词（这里简化处理，实际应用中可以使用NLP技术）
        Set<String> keywords = extractKeyWords(correctAnswer);

        String lowerUserAnswer = userAnswer.toLowerCase();

        // 检查是否包含大部分关键词
        int matchedKeywords = 0;
        for (String keyword : keywords) {
            if (lowerUserAnswer.contains(keyword.toLowerCase())) {
                matchedKeywords++;
            }
        }

        // 如果匹配了至少一半的关键词，认为答案包含关键信息
        return keywords.isEmpty() ? false : (double) matchedKeywords / keywords.size() >= 0.5;
    }

    /**
     * 从答案中提取关键词（简化实现）
     * @param answer 答案
     * @return 关键词集合
     */
    private Set<String> extractKeyWords(String answer) {
        // 这里简化处理，实际应用中可以使用更复杂的NLP技术
        // 暂时返回一些长度大于2的词汇
        String[] words = answer.split("[\\s\\p{Punct}]+");
        return Arrays.stream(words)
                .filter(word -> word.length() > 2) // 过滤掉太短的词
                .collect(Collectors.toSet());
    }


    /**
     * 获取问题的总分
     */
    private int getQuestionScore(Long questionId, String questionType) {
        switch (questionType) {
            case "MULTIPLE_CHOICE":
                MultipleChoiceQuestion mcq = multipleChoiceQuestionServer.GetMultipleChoiceQuestion(questionId);
                return mcq != null ? mcq.getScore() : 0;
            case "ESSAY":
                EssayQuestion eq = essayQuestionServer.GetEssayQuestion(questionId);
                return eq != null ? eq.getScore() : 0;
            case "ANALYSIS":
                AnalysisQuestion aq = analysisQuestionServer.GetAnalysisQuestion(questionId);
                return aq != null ? aq.getScore() : 0;
            default:
                return 0;
        }
    }
    
    /**
     * 更新用户答题记录
     */
    private void updateUserAnswer(UserAnswer userAnswer) {
        //先判断是否存在该记录
        UserAnswer existingAnswer = userAnswersServer.GetUserAnswer(userAnswer.getId());
        if (existingAnswer == null) {
            // 如果不存在，创建新记录
            userAnswersServer.CreateUserAnswers(userAnswer);
        } else {
            // 如果存在，更新记录
            userAnswersServer.UpdateUserAnswer(userAnswer);
        }
    }
    
    /**
     * 创建或更新分数记录
     */
    private void createOrUpdateScoreRecord(Long userId, Long paperId, int currentScore, boolean isCorrect) {
        // 查找现有的分数记录
        List<Score> existingScores = scoreServer.GetScoreList().stream()
                .filter(score -> score.getUserId().equals(userId) && score.getPaperId().equals(paperId))
                .toList();

        //获取当前试卷的总分最大分数以及答题数量
        Paper paper = paperServer.GetPaper(paperId);
        int totalScore = paper.getTotalScore();
        int questionCount = paper.getQuestionCount();

        Score score;
        if (existingScores.isEmpty()) {
            // 创建新的分数记录
            score = new Score();
            score.setUserId(userId);
            score.setPaperId(paperId);
            score.setTotalScore(currentScore);
            score.setCreateTime(LocalDateTime.now());
            score.setAnsweredCount(1);
            score.setStatus(0);
            score.setTotalCount(questionCount);
            score.setMaxScore(totalScore);

            scoreServer.CreateScore(score);
        } else {
            // 更新现有的分数记录 - 添加空值安全检查
            score = existingScores.get(0);
            score.setTotalScore(score.getTotalScore() + currentScore); // 累加分数
            score.setCreateTime(LocalDateTime.now());

            // 安全处理answeredCount字段，避免空指针异常
            Integer currentAnsweredCount = score.getAnsweredCount();
            int newAnsweredCount = (currentAnsweredCount != null ? currentAnsweredCount : 0) + 1;
            score.setAnsweredCount(newAnsweredCount);

            // 安全处理totalCount字段
            Integer currentTotalCount = score.getTotalCount();
            int safeTotalCount = currentTotalCount != null ? currentTotalCount : questionCount;
            score.setTotalCount(safeTotalCount);

            score.setStatus(newAnsweredCount >= safeTotalCount ? 1 : 0);

            scoreServer.UpdateScore(score);
        }
    }



    private boolean isValidQuestionType(String questionType) {
        //从数据库中拿到所有题目类型来比较
        Set<String> collect = questionTypeServer.GetAllQuestionTypes().stream()
                .map(QuestionType::getTypeCode)
                .collect(Collectors.toSet());

        return questionType != null && collect.contains(questionType);
    }


}