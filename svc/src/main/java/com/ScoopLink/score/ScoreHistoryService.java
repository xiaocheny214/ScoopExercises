package com.ScoopLink.score;

import com.ScoopLink.dto.PageResponse;
import com.ScoopLink.manageQuestion.analysisQuestion.dto.AnalysisQuestion;
import com.ScoopLink.manageQuestion.analysisQuestion.dto.AnswerAnalysis;
import com.ScoopLink.manageQuestion.analysisQuestion.server.AnalysisQuestionServer;
import com.ScoopLink.manageQuestion.essayQuestions.dto.AnswerEassy;
import com.ScoopLink.manageQuestion.essayQuestions.dto.EssayQuestion;
import com.ScoopLink.manageQuestion.essayQuestions.server.EssayQuestionServer;
import com.ScoopLink.manageQuestion.multipleChoiceQuestion.dto.AnswerChoice;
import com.ScoopLink.manageQuestion.multipleChoiceQuestion.dto.MultipleChoiceQuestion;
import com.ScoopLink.manageQuestion.multipleChoiceQuestion.server.MultipleChoiceQuestionServer;
import com.ScoopLink.manageQuestion.papers.dto.Paper;
import com.ScoopLink.manageQuestion.papers.server.PaperServer;
import com.ScoopLink.manageQuestion.question.dto.Question;
import com.ScoopLink.manageQuestion.questionBank.dto.QuestionBank;
import com.ScoopLink.manageQuestion.questionBank.server.QuestionBankServer;
import com.ScoopLink.scoreCalculation.dto.Score;
import com.ScoopLink.scoreCalculation.dto.ScoreInfo;
import com.ScoopLink.scoreCalculation.dto.ScoreRequest;
import com.ScoopLink.scoreCalculation.dto.UserScore;
import com.ScoopLink.scoreCalculation.server.ScoreServer;
import com.ScoopLink.scoreCalculation.server.StatisticsScore;
import com.ScoopLink.userAnswers.dto.UserAnswer;
import com.ScoopLink.userAnswers.server.UserAnswersServer;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ScoreHistoryService implements StatisticsScore {

    @Resource
    private ScoreServer scoreServer;
    @Resource
    private MultipleChoiceQuestionServer multipleChoiceQuestionServer;
    @Resource
    private AnalysisQuestionServer analysisQuestionServer;
    @Resource
    private EssayQuestionServer essayQuestionServer;
    @Resource
    private ScoreMapper scoreMapper;
    @Resource
    private UserAnswersServer userAnswersServer;
    @Resource
    private QuestionBankServer questionBankServer;
    @Resource
    private PaperServer paperServer;

    @Override
    public PageResponse<UserScore> getScores(ScoreRequest request) {
        // 设置默认值
        int page = request.getPage() == null || request.getPage() < 0 ? 0 : request.getPage();
        int size = request.getSize() == null || request.getSize() <= 0 ? 10 : request.getSize();

        // 计算偏移量
        int offset = page * size;

        // 查询数据
        List<Score> scores = scoreMapper.selectWithPaging(
                offset,
                size,
                request.getUserId(),
                request.getPaperId(),
                request.getBankId(),
                request.getStartDate(),
                request.getEndDate()
        );
        // 查询总数
        long total = scoreMapper.selectCount(
                request.getUserId(),
                request.getPaperId(),
                request.getBankId(),
                request.getStartDate(),
                request.getEndDate()
        );

        //封装数据
        List<UserScore> userScoreStream = scores.stream().map(score -> {
            Paper paper = paperServer.GetPaper(score.getPaperId());
            QuestionBank bank = questionBankServer.GetQuestionBank(paper.getBankId());

            // 封装数据
            UserScore userScore = new UserScore();
            userScore.setScore(score);
            userScore.setBankName(bank.getName());
            userScore.setPaperName(paper.getTitle());
            return userScore;
        }).collect(Collectors.toList());

        // 返回分页结果
        return PageResponse.of(page, size, total, userScoreStream);
    }


    /**
     * 获取分数详情
     * @param scoreId 分数ID
     * @return 分数详情
     */
    @Override
    public ScoreInfo GetScoreInfo(Long scoreId) {
        //根据分数ID获取分数
        Score score = scoreServer.GetScore(scoreId);
        if (score == null) {
            return new ScoreInfo();
        }
        //根据试卷ID获取试卷名称和题库名称
        Long paperId = score.getPaperId();
        Paper paper = paperServer.GetPaper(paperId);
        String paperName = paper != null ? paper.getTitle() : "";
        String bankName = questionBankServer.GetQuestionBank(paper.getBankId()) != null ? questionBankServer.GetQuestionBank(paper.getBankId()).getName() : "";

        //根据试卷ID获取该试卷下的所有题目
        Long userId = score.getUserId();
        List<MultipleChoiceQuestion> mcqList = multipleChoiceQuestionServer.GetMultipleChoiceQuestionsByPaperId(paperId);
        List<AnalysisQuestion> aqList = analysisQuestionServer.GetAnalysisQuestionsByPaperId(paperId);
        List<EssayQuestion> eqList = essayQuestionServer.GetEssayQuestionsByPaperId(paperId);
        
        // 根据分数记录获取作答次数，然后根据用户ID、试卷ID和作答次数获取用户答案
        Long attemptNum = score.getAttemptNum();
        List<UserAnswer> userAnswerList;
        if (attemptNum != null) {
            userAnswerList = userAnswersServer.GetUserAnswerByUserIdAndPaperIdAndAttemptNum(userId, paperId, attemptNum);
        } else {
            userAnswerList = userAnswersServer.GetUserAnswerByUserIdAndPaperId(userId, paperId);
        }
        
        // 创建问题对象来存储用户答案详情
        Question question = new Question();
        
        // 将用户答案与题目匹配并转换为对应的AnswerChoice、AnswerAnalysis、AnswerEassy对象
        if (mcqList != null && !mcqList.isEmpty()) {
            List<AnswerChoice> answerChoiceList = new ArrayList<>();
            for (MultipleChoiceQuestion mcq : mcqList) {
                // 查找对应的用户答案
                UserAnswer userAnswer = userAnswerList.stream()
                    .filter(ua -> ua.getQuestionId().equals(mcq.getId()) && ua.getQuestionType().equals("MULTIPLE_CHOICE"))
                    .findFirst()
                    .orElse(null);
                
                if (userAnswer != null) {
                    AnswerChoice answerChoice = new AnswerChoice();
                    answerChoice.setQuestionId(mcq.getId());
                    answerChoice.setQuestionText(mcq.getQuestionText());
                    answerChoice.setQuestionType("MULTIPLE_CHOICE");
                    answerChoice.setCorrectAnswer(mcq.getCorrectAnswer());
                    answerChoice.setUserAnswer(userAnswer.getUserAnswer());
                    answerChoice.setCorrect(userAnswer.isCorrect());
                    answerChoice.setScoreObtained(userAnswer.getScoreObtained());
                    answerChoice.setMaxScore(mcq.getScore());
                    
                    // 构建选项映射
                    Map<String, String> options = new java.util.HashMap<>();
                    if (mcq.getOptionA() != null) options.put("A", mcq.getOptionA());
                    if (mcq.getOptionB() != null) options.put("B", mcq.getOptionB());
                    if (mcq.getOptionC() != null) options.put("C", mcq.getOptionC());
                    if (mcq.getOptionD() != null) options.put("D", mcq.getOptionD());
                    if (mcq.getOptionE() != null) options.put("E", mcq.getOptionE());
                    if (mcq.getOptionF() != null) options.put("F", mcq.getOptionF());
                    answerChoice.setOptions(options);
                    
                    answerChoiceList.add(answerChoice);
                }
            }
            question.setMultipleChoice(answerChoiceList);
        }
        
        if (aqList != null && !aqList.isEmpty()) {
            List<AnswerAnalysis> answerAnalysisList = new java.util.ArrayList<>();
            for (AnalysisQuestion aq : aqList) {
                // 查找对应的用户答案
                UserAnswer userAnswer = userAnswerList.stream()
                    .filter(ua -> ua.getQuestionId().equals(aq.getId()) && ua.getQuestionType().equals("ANALYSIS"))
                    .findFirst()
                    .orElse(null);
                
                if (userAnswer != null) {
                    AnswerAnalysis answerAnalysis = new AnswerAnalysis();
                    answerAnalysis.setQuestionId(aq.getId());
                    answerAnalysis.setQuestionText(aq.getQuestionText());
                    answerAnalysis.setQuestionType("ANALYSIS");
                    answerAnalysis.setCorrectAnswer(aq.getCorrectAnswer());
                    answerAnalysis.setUserAnswer(userAnswer.getUserAnswer());
                    answerAnalysis.setCorrect(userAnswer.isCorrect());
                    answerAnalysis.setScoreObtained(userAnswer.getScoreObtained());
                    answerAnalysis.setMaxScore(aq.getScore());
                    
                    answerAnalysisList.add(answerAnalysis);
                }
            }
            question.setAnalysis(answerAnalysisList);
        }
        
        if (eqList != null && !eqList.isEmpty()) {
            List<AnswerEassy> answerEassyList = new java.util.ArrayList<>();
            for (EssayQuestion eq : eqList) {
                // 查找对应的用户答案
                UserAnswer userAnswer = userAnswerList.stream()
                    .filter(ua -> ua.getQuestionId().equals(eq.getId()) && ua.getQuestionType().equals("ESSAY"))
                    .findFirst()
                    .orElse(null);
                
                if (userAnswer != null) {
                    AnswerEassy answerEassy = new AnswerEassy();
                    answerEassy.setQuestionId(eq.getId());
                    answerEassy.setQuestionText(eq.getQuestionText());
                    answerEassy.setQuestionType("ESSAY");
                    answerEassy.setReferenceAnswer(eq.getReferenceAnswer());
                    answerEassy.setUserAnswer(userAnswer.getUserAnswer());
                    answerEassy.setCorrect(userAnswer.isCorrect());
                    answerEassy.setScoreObtained(userAnswer.getScoreObtained());
                    answerEassy.setMaxScore(eq.getScore());
                    
                    answerEassyList.add(answerEassy);
                }
            }
            question.setEssay(answerEassyList);
        }
        
        //拼装分数详情
        ScoreInfo scoreInfo = new ScoreInfo();
        UserScore userScore = new UserScore();
        userScore.setScore(score);
        userScore.setPaperName(paperName);
        userScore.setBankName(bankName );
        scoreInfo.setScoreInfo(userScore);
        scoreInfo.setUserAnswer(question);
        
        return scoreInfo;
    }
    /**
     * 删除分数记录
     * @param scoreId 分数ID
     * @return 删除结果
     */
    @Override
    @Transactional
    public boolean deleteScore(Long scoreId) {
        //删除分数记录的同时删去用户的答题记录
        Score score = scoreMapper.selectById(scoreId);
        int deleteCount = scoreMapper.deleteById(scoreId);
        if (deleteCount > 0) {
            List<UserAnswer> userAnswerList = userAnswersServer.GetUserAnswerByUserIdAndPaperId(score.getUserId(), score.getPaperId());
            for (UserAnswer userAnswer : userAnswerList) {
                userAnswersServer.DeleteUserAnswer(userAnswer.getId());
            }
            return true;
        }
        return false;
    }
}
