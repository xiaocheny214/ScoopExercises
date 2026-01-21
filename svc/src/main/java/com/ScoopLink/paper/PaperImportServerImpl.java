package com.ScoopLink.paper;

import com.ScoopLink.manageQuestion.analysisQuestion.dto.AnalysisQuestion;
import com.ScoopLink.manageQuestion.analysisQuestion.server.AnalysisQuestionServer;
import com.ScoopLink.manageQuestion.papers.dto.Paper;
import com.ScoopLink.manageQuestion.papers.dto.PaperImportTemplate;
import com.ScoopLink.manageQuestion.multipleChoiceQuestion.dto.MultipleChoiceQuestion;
import com.ScoopLink.manageQuestion.essayQuestions.dto.EssayQuestion;
import com.ScoopLink.manageQuestion.papers.server.PaperImportServer;
import com.ScoopLink.manageQuestion.papers.server.PaperServer;
import com.ScoopLink.manageQuestion.multipleChoiceQuestion.server.MultipleChoiceQuestionServer;
import com.ScoopLink.manageQuestion.essayQuestions.server.EssayQuestionServer;
import com.ScoopLink.manageQuestion.question.dto.QuestionType;
import com.ScoopLink.manageQuestion.question.server.QuestionTypeServer;
import com.ScoopLink.util.PaperImportUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaperImportServerImpl implements PaperImportServer {
    
    @Resource
    private PaperServer paperServer;
    
    @Resource
    private MultipleChoiceQuestionServer multipleChoiceQuestionServer;
    
    @Resource
    private EssayQuestionServer essayQuestionServer;

    @Resource
    private AnalysisQuestionServer analysisQuestionServer;

    @Resource
    private QuestionTypeServer questionTypeServer;
    
    @Override
    @Transactional
    public boolean importPaper(PaperImportTemplate importTemplate) {
        try {
            // 为了计算最终的总分和题目数量，我们先收集所有题目
            Paper paper = importTemplate.getPaperInfo();
            Integer totalScore = 0;
            Integer questionCount = 0;

            // 获取题目类型
            List<QuestionType> qtList = questionTypeServer.GetActiveQuestionTypes();
            List<Long> multipleChoice = qtList.stream().filter(qt -> qt.getTypeCode().equals("MULTIPLE_CHOICE"))
                    .map(QuestionType::getId)
                    .collect(Collectors.toList());
            List<Long> essay = qtList.stream().filter(qt -> qt.getTypeCode().equals("ESSAY"))
                    .map(QuestionType::getId)
                    .collect(Collectors.toList());
            List<Long> analysis = qtList.stream().filter(qt -> qt.getTypeCode().equals("ANALYSIS"))
                    .map(QuestionType::getId)
                    .collect(Collectors.toList());
            List<Long> singleChoice = qtList.stream().filter(qt -> qt.getTypeCode().equals("SINGLE_CHOICE"))
                    .map(QuestionType::getId)
                    .collect(Collectors.toList());

            // 首先计算总分和题目数量
            List<MultipleChoiceQuestion> mcqList = importTemplate.getMultipleChoiceQuestions();
            if (mcqList != null && !mcqList.isEmpty()) {
                for (MultipleChoiceQuestion mcq : mcqList) {
                    totalScore += mcq.getScore();
                    questionCount++;
                }
            }

            List<EssayQuestion> eqList = importTemplate.getEssayQuestions();
            if (eqList != null && !eqList.isEmpty()) {
                for (EssayQuestion eq : eqList) {
                    totalScore += eq.getScore();
                    questionCount++;
                }
            }

            List<AnalysisQuestion> aqList = importTemplate.getAnalysisQuestions();
            if (aqList != null && !aqList.isEmpty()) {
                for (AnalysisQuestion aq : aqList) {
                    totalScore += aq.getScore();
                    questionCount++;
                }
            }

            // 创建试卷以获取ID
            paper.setCreateTime(LocalDateTime.now());
            paper.setTotalScore(totalScore);
            paper.setQuestionCount(questionCount);
            Paper createdPaper = paperServer.CreatePaper(paper);

            // 2. 批量创建选择题
            if (mcqList != null && !mcqList.isEmpty()) {
                for (MultipleChoiceQuestion mcq : mcqList) {
                    // 设置试卷ID和题目类型ID
                    mcq.setPaperId(createdPaper.getId());
                    if(mcq.getCorrectAnswer().contains(",")) {
                        mcq.setQuestionTypeId(multipleChoice.get(0));
                    } else {
                        mcq.setQuestionTypeId(singleChoice.get(0));
                    }
                }
                multipleChoiceQuestionServer.CreateMultipleChoiceQuestions(mcqList);
            }

            // 3. 批量创建解答题
            if (eqList != null && !eqList.isEmpty()) {
                for (EssayQuestion eq : eqList) {
                    // 设置试卷ID和题目类型ID
                    eq.setPaperId(createdPaper.getId());
                    eq.setQuestionTypeId(essay.get(0));
                }
                essayQuestionServer.CreateEssayQuestions(eqList);
            }

            // 4. 批量创建分析题
            if (aqList != null && !aqList.isEmpty()) {
                for (AnalysisQuestion aq : aqList) {
                    // 设置试卷ID和题目类型ID
                    aq.setPaperId(createdPaper.getId());
                    aq.setQuestionTypeId(analysis.get(0));
                }
                analysisQuestionServer.CreateAnalysisQuestions(aqList);
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    @Transactional
    public boolean importPaperFromJson(String jsonString) {
        try {
            PaperImportTemplate template = PaperImportUtil.parseFromJson(jsonString);
            return importPaper(template);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    @Transactional
    public boolean importPaperFromExcel(String filePath) {
        try {
            PaperImportTemplate template = PaperImportUtil.parseFromExcel(filePath);
            return importPaper(template);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 获取最新的试卷（按创建时间倒序排列，取第一条）
     */
    public Paper getLatestPaper(){
        List<Paper> papers = paperServer.GetPaperList();
        return papers.stream()
                .sorted(Comparator.comparing(Paper::getCreateTime).reversed())
                .findFirst()
                .orElse(null);
    }
}