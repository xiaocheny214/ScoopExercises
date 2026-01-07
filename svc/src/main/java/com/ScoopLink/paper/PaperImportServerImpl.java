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
import com.ScoopLink.util.PaperImportUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    
    @Override
    @Transactional
    public boolean importPaper(PaperImportTemplate importTemplate) {
        try {
            // 1. 创建试卷
            Paper paper = importTemplate.getPaperInfo();
            Paper createdPaper = paperServer.CreatePaper(paper);
            if (createdPaper == null) {
                return false;
            }

            // 2. 批量创建选择题
            List<MultipleChoiceQuestion> mcqList = importTemplate.getMultipleChoiceQuestions();
            if (mcqList != null && !mcqList.isEmpty()) {
                for (MultipleChoiceQuestion mcq : mcqList) {
                    // 设置试卷ID
                    mcq.setPaperId(paper.getId());
                }
                multipleChoiceQuestionServer.CreateMultipleChoiceQuestions(mcqList);
            }

            // 3. 批量创建解答题
            List<EssayQuestion> eqList = importTemplate.getEssayQuestions();
            if (eqList != null && !eqList.isEmpty()) {
                for (EssayQuestion eq : eqList) {
                    // 设置试卷ID
                    eq.setPaperId(paper.getId());
                }
                essayQuestionServer.CreateEssayQuestions(eqList);
            }

            // 4. 批量创建分析题
            List<AnalysisQuestion> aqList = importTemplate.getAnalysisQuestions();
            if (aqList != null && !aqList.isEmpty()) {
                for (AnalysisQuestion aq : aqList) {
                    // 设置试卷ID
                    aq.setPaperId(paper.getId());
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
}