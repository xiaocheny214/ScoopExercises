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
            // 1. 创建试卷基本信息
            Paper paperInfo = importTemplate.getPaperInfo();
            if (paperInfo == null) {
                throw new IllegalArgumentException("试卷信息不能为空");
            }
            
            // 设置默认值
            paperInfo.setCreateTime(java.time.LocalDateTime.now());
            
            // 保存试卷基本信息
            if (!paperServer.CreatePaper(paperInfo)) {
                throw new RuntimeException("创建试卷失败");
            }
            
            // 2. 保存选择题
            List<MultipleChoiceQuestion> mcqList = importTemplate.getMultipleChoiceQuestions();
            if (mcqList != null && !mcqList.isEmpty()) {
                for (MultipleChoiceQuestion mcq : mcqList) {
                    mcq.setPaperId(paperInfo.getId()); // 关联到刚创建的试卷

                }
                // 调用选择题服务保存题目
                multipleChoiceQuestionServer.CreateMultipleChoiceQuestions(mcqList);
            }
            
            // 3. 保存问答题
            List<EssayQuestion> eqList = importTemplate.getEssayQuestions();
            if (eqList != null && !eqList.isEmpty()) {
                for (EssayQuestion eq : eqList) {
                    eq.setPaperId(paperInfo.getId()); // 关联到刚创建的试卷
                }
                // 调用问答题服务保存题目
                essayQuestionServer.CreateEssayQuestions(eqList);
            }

            // 4. 保存分析题
             List<AnalysisQuestion> aqList = importTemplate.getAnalysisQuestions();
            if (aqList != null && !aqList.isEmpty()) {
                for (AnalysisQuestion aq : aqList) {
                    aq.setPaperId(paperInfo.getId()); // 关联到刚创建的试卷

                }
                // 调用分析题服务保存题目
                analysisQuestionServer.CreateAnalysisQuestions(aqList);
            }
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean importPaperFromJson(String jsonString) {
        // 使用Jackson或Gson解析JSON字符串为PaperImportTemplate对象
        // 然后调用importPaper方法
        try {
            // 这里需要引入Jackson或Gson依赖
            // ObjectMapper mapper = new ObjectMapper();
            // PaperImportTemplate template = mapper.readValue(jsonString, PaperImportTemplate.class);
            // return importPaper(template);
            return false; // 临时返回
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean importPaperFromExcel(String filePath) {
        // 实现Excel导入逻辑
        // 读取Excel文件，解析为PaperImportTemplate对象
        // 然后调用importPaper方法
        try {
            // 这里需要引入Apache POI依赖来处理Excel文件
            // 解析Excel并转换为PaperImportTemplate
            return false; // 临时返回
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
