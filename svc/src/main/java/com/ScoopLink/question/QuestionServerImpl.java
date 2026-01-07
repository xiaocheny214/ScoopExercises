package com.ScoopLink.question;

import com.ScoopLink.manageQuestion.analysisQuestion.dto.AnalysisQuestion;
import com.ScoopLink.manageQuestion.analysisQuestion.server.AnalysisQuestionServer;
import com.ScoopLink.manageQuestion.essayQuestions.dto.EssayQuestion;
import com.ScoopLink.manageQuestion.essayQuestions.server.EssayQuestionServer;
import com.ScoopLink.manageQuestion.multipleChoiceQuestion.dto.MultipleChoiceQuestion;
import com.ScoopLink.manageQuestion.multipleChoiceQuestion.server.MultipleChoiceQuestionServer;
import com.ScoopLink.manageQuestion.question.dto.QuestionRequest;
import com.ScoopLink.manageQuestion.question.dto.QuestionResponse;
import com.ScoopLink.manageQuestion.question.dto.QuestionType;
import com.ScoopLink.manageQuestion.question.server.QuestionServer;

import com.ScoopLink.manageQuestion.question.server.QuestionTypeServer;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuestionServerImpl implements QuestionServer {

    @Resource
    private MultipleChoiceQuestionServer multipleChoiceQuestionServer;

    @Resource
    private AnalysisQuestionServer analysisQuestionServer;

    @Resource
    private EssayQuestionServer essayQuestionServer;

    @Resource
    private QuestionTypeServer questionTypeServer;

    @Override
    @Transactional
    public QuestionResponse createQuestion(QuestionRequest request) {
        try {
            QuestionType questionType = getQuestionType(request);
            if (questionType == null) {
                return createErrorResponse("题目类型不存在");
            }

            switch (questionType.getTypeCode()) {
                case "MULTIPLE_CHOICE":
                    return createMultipleChoiceQuestion(request, questionType);
                case "ANALYSIS":
                    return createAnalysisQuestion(request, questionType);
                case "ESSAY":
                    return createEssayQuestion(request, questionType);
                default:
                    return createErrorResponse("不支持的题目类型: " + questionType.getTypeCode());
            }
        } catch (Exception e) {
            return createErrorResponse("创建题目失败: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public List<QuestionResponse> createQuestions(List<QuestionRequest> requests) {
        List<QuestionResponse> responses = new ArrayList<>();
        for (QuestionRequest request : requests) {
            responses.add(createQuestion(request));
        }
        return responses;
    }

    @Override
    public QuestionResponse getQuestion(Long id, Long questionTypeId) {
        try {
            QuestionType questionType = questionTypeServer.GetQuestionType(questionTypeId);
            if (questionType == null) {
                return createErrorResponse("题目类型不存在");
            }

            switch (questionType.getTypeCode()) {
                case "MULTIPLE_CHOICE":
                    return convertToResponse(multipleChoiceQuestionServer.GetMultipleChoiceQuestion(id), questionType);
                case "ANALYSIS":
                    return convertToResponse(analysisQuestionServer.GetAnalysisQuestion(id), questionType);
                case "ESSAY":
                    return convertToResponse(essayQuestionServer.GetEssayQuestion(id), questionType);
                default:
                    return createErrorResponse("不支持的题目类型: " + questionType.getTypeCode());
            }
        } catch (Exception e) {
            return createErrorResponse("获取题目失败: " + e.getMessage());
        }
    }

    @Override
    public List<QuestionResponse> getQuestionsByPaperId(Long paperId) {
        List<QuestionResponse> responses = new ArrayList<>();

        try {
            // 获取所有题目类型
            List<QuestionType> questionTypes = questionTypeServer.GetAllQuestionTypes();

            // 遍历所有题目类型，获取对应类型的题目
            for (QuestionType questionType : questionTypes) {
                List<QuestionResponse> typeResponses = getQuestionsByPaperIdAndType(paperId, questionType);
                responses.addAll(typeResponses);
            }

            // 按排序顺序排序
            responses.sort((r1, r2) -> {
                Integer order1 = r1.getSortOrder() != null ? r1.getSortOrder() : 0;
                Integer order2 = r2.getSortOrder() != null ? r2.getSortOrder() : 0;
                return order1.compareTo(order2);
            });

        } catch (Exception e) {
            // 记录错误但不中断整个流程
            System.err.println("获取试卷题目失败: " + e.getMessage());
        }

        return responses;
    }


    /**
     * 根据试卷ID和题目类型获取题目列表
     */
    private List<QuestionResponse> getQuestionsByPaperIdAndType(Long paperId, QuestionType questionType) {
        List<QuestionResponse> responses = new ArrayList<>();

        try {
            switch (questionType.getTypeCode()) {
                case "MULTIPLE_CHOICE":
                    List<MultipleChoiceQuestion> mcqList = multipleChoiceQuestionServer.GetMultipleChoiceQuestionsByPaperId(paperId);
                    for (MultipleChoiceQuestion mcq : mcqList) {
                        responses.add(convertToResponse(mcq, questionType));
                    }
                    break;

                case "ANALYSIS":
                    List<AnalysisQuestion> aqList = analysisQuestionServer.GetAnalysisQuestionsByPaperId(paperId);
                    for (AnalysisQuestion aq : aqList) {
                        responses.add(convertToResponse(aq, questionType));
                    }
                    break;

                case "ESSAY":
                    List<EssayQuestion> eqList = essayQuestionServer.GetEssayQuestionsByPaperId(paperId);
                    for (EssayQuestion eq : eqList) {
                        responses.add(convertToResponse(eq, questionType));
                    }
                    break;

                default:
                    // 忽略不支持的题目类型
                    break;
            }
        } catch (Exception e) {
            // 记录错误但不中断其他类型的查询
            System.err.println("获取" + questionType.getTypeName() + "题目失败: " + e.getMessage());
        }

        return responses;
    }

    @Override
    public List<QuestionResponse> getQuestionsByType(Long questionTypeId) {
        // 类似getQuestionsByPaperId，需要扩展具体服务接口
        return new ArrayList<>();
    }

    @Override
    @Transactional
    public QuestionResponse updateQuestion(QuestionRequest request) {
        try {
            QuestionType questionType = getQuestionType(request);
            if (questionType == null) {
                return createErrorResponse("题目类型不存在");
            }

            switch (questionType.getTypeCode()) {
                case "MULTIPLE_CHOICE":
                    return updateMultipleChoiceQuestion(request, questionType);
                case "ANALYSIS":
                    return updateAnalysisQuestion(request, questionType);
                case "ESSAY":
                    return updateEssayQuestion(request, questionType);
                default:
                    return createErrorResponse("不支持的题目类型: " + questionType.getTypeCode());
            }
        } catch (Exception e) {
            return createErrorResponse("更新题目失败: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public QuestionResponse deleteQuestion(Long id, Long questionTypeId) {
        try {
            QuestionType questionType = questionTypeServer.GetQuestionType(questionTypeId);
            if (questionType == null) {
                return createErrorResponse("题目类型不存在");
            }

            boolean success = false;
            switch (questionType.getTypeCode()) {
                case "MULTIPLE_CHOICE":
                    success = multipleChoiceQuestionServer.DeleteMultipleChoiceQuestion(id);
                    break;
                case "ANALYSIS":
                    success = analysisQuestionServer.DeleteAnalysisQuestion(id);
                    break;
                case "ESSAY":
                    success = essayQuestionServer.DeleteEssayQuestion(id);
                    break;
                default:
                    return createErrorResponse("不支持的题目类型: " + questionType.getTypeCode());
            }

            QuestionResponse response = new QuestionResponse();
            response.setSuccess(success);
            response.setId(id);
            response.setQuestionTypeId(questionTypeId);
            response.setQuestionTypeCode(questionType.getTypeCode());
            response.setQuestionTypeName(questionType.getTypeName());
            return response;
        } catch (Exception e) {
            return createErrorResponse("删除题目失败: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public List<QuestionResponse> deleteQuestions(List<Long> ids, Long questionTypeId) {
        List<QuestionResponse> responses = new ArrayList<>();
        for (Long id : ids) {
            responses.add(deleteQuestion(id, questionTypeId));
        }
        return responses;
    }

    // 私有辅助方法
    private QuestionType getQuestionType(QuestionRequest request) {
        if (request.getQuestionTypeCode() != null) {
            return questionTypeServer.GetQuestionTypeByCode(request.getQuestionTypeCode());
        } else if (request.getQuestionTypeId() != null) {
            return questionTypeServer.GetQuestionType(request.getQuestionTypeId());
        }
        return null;
    }

    private QuestionResponse createMultipleChoiceQuestion(QuestionRequest request, QuestionType questionType) {
        MultipleChoiceQuestion mcq = new MultipleChoiceQuestion();
        mcq.setPaperId(request.getPaperId());
        mcq.setQuestionText(request.getQuestionText());
        mcq.setScore(request.getScore());
        mcq.setSortOrder(request.getSortOrder());

        // 设置选择题特有字段
        if (request.getExtraFields() != null) {
            Map<String, Object> extra = request.getExtraFields();
            mcq.setOptionA((String) extra.get("optionA"));
            mcq.setOptionB((String) extra.get("optionB"));
            mcq.setOptionC((String) extra.get("optionC"));
            mcq.setOptionD((String) extra.get("optionD"));
            mcq.setOptionE((String) extra.get("optionE"));
            mcq.setOptionF((String) extra.get("optionF"));
            mcq.setCorrectAnswer((String) extra.get("correctAnswer"));
            mcq.setExplanation((String) extra.get("explanation"));
        }

        boolean success = multipleChoiceQuestionServer.CreateMultipleChoiceQuestion(mcq);
        return convertToResponse(mcq, questionType, success);
    }

    private QuestionResponse createAnalysisQuestion(QuestionRequest request, QuestionType questionType) {
        AnalysisQuestion aq = new AnalysisQuestion();
        aq.setPaperId(request.getPaperId());
        aq.setQuestionText(request.getQuestionText());
        aq.setScore(request.getScore());
        aq.setSortOrder(request.getSortOrder());

        if (request.getExtraFields() != null) {
            Map<String, Object> extra = request.getExtraFields();
            aq.setCorrectAnswer((String) extra.get("correctAnswer"));
            aq.setExplanation((String) extra.get("explanation"));
        }

        boolean success = analysisQuestionServer.CreateAnalysisQuestion(aq);
        return convertToResponse(aq, questionType, success);
    }

    private QuestionResponse createEssayQuestion(QuestionRequest request, QuestionType questionType) {
        EssayQuestion eq = new EssayQuestion();
        eq.setPaperId(request.getPaperId());
        eq.setQuestionText(request.getQuestionText());
        eq.setScore(request.getScore());
        eq.setSortOrder(request.getSortOrder());

        if (request.getExtraFields() != null) {
            Map<String, Object> extra = request.getExtraFields();
            eq.setReferenceAnswer((String) extra.get("referenceAnswer"));
            eq.setExplanation((String) extra.get("explanation"));
        }

        boolean success = essayQuestionServer.CreateEssayQuestion(eq);
        return convertToResponse(eq, questionType, success);
    }

    private QuestionResponse updateMultipleChoiceQuestion(QuestionRequest request, QuestionType questionType) {
        MultipleChoiceQuestion mcq = multipleChoiceQuestionServer.GetMultipleChoiceQuestion(request.getId());
        if (mcq == null) {
            return createErrorResponse("题目不存在");
        }

        // 更新字段
        if (request.getQuestionText() != null) mcq.setQuestionText(request.getQuestionText());
        if (request.getScore() != null) mcq.setScore(request.getScore());
        if (request.getSortOrder() != null) mcq.setSortOrder(request.getSortOrder());

        if (request.getExtraFields() != null) {
            Map<String, Object> extra = request.getExtraFields();
            if (extra.get("optionA") != null) mcq.setOptionA((String) extra.get("optionA"));
            if (extra.get("optionB") != null) mcq.setOptionB((String) extra.get("optionB"));
            if (extra.get("optionC") != null) mcq.setOptionC((String) extra.get("optionC"));
            if (extra.get("optionD") != null) mcq.setOptionD((String) extra.get("optionD"));
            if (extra.get("optionE") != null) mcq.setOptionE((String) extra.get("optionE"));
            if (extra.get("optionF") != null) mcq.setOptionF((String) extra.get("optionF"));
            if (extra.get("correctAnswer") != null) mcq.setCorrectAnswer((String) extra.get("correctAnswer"));
            if (extra.get("explanation") != null) mcq.setExplanation((String) extra.get("explanation"));
        }

        boolean success = multipleChoiceQuestionServer.UpdateMultipleChoiceQuestion(mcq);
        return convertToResponse(mcq, questionType, success);
    }

    private QuestionResponse updateAnalysisQuestion(QuestionRequest request, QuestionType questionType) {
        AnalysisQuestion aq = analysisQuestionServer.GetAnalysisQuestion(request.getId());
        if (aq == null) {
            return createErrorResponse("题目不存在");
        }

        if (request.getQuestionText() != null) aq.setQuestionText(request.getQuestionText());
        if (request.getScore() != null) aq.setScore(request.getScore());
        if (request.getSortOrder() != null) aq.setSortOrder(request.getSortOrder());

        if (request.getExtraFields() != null) {
            Map<String, Object> extra = request.getExtraFields();
            if (extra.get("correctAnswer") != null) aq.setCorrectAnswer((String) extra.get("correctAnswer"));
            if (extra.get("explanation") != null) aq.setExplanation((String) extra.get("explanation"));
        }

        boolean success = analysisQuestionServer.UpdateAnalysisQuestion(aq);
        return convertToResponse(aq, questionType, success);
    }

    private QuestionResponse updateEssayQuestion(QuestionRequest request, QuestionType questionType) {
        EssayQuestion eq = essayQuestionServer.GetEssayQuestion(request.getId());
        if (eq == null) {
            return createErrorResponse("题目不存在");
        }

        if (request.getQuestionText() != null) eq.setQuestionText(request.getQuestionText());
        if (request.getScore() != null) eq.setScore(request.getScore());
        if (request.getSortOrder() != null) eq.setSortOrder(request.getSortOrder());

        if (request.getExtraFields() != null) {
            Map<String, Object> extra = request.getExtraFields();
            if (extra.get("referenceAnswer") != null) eq.setReferenceAnswer((String) extra.get("referenceAnswer"));
            if (extra.get("explanation") != null) eq.setExplanation((String) extra.get("explanation"));
        }

        boolean success = essayQuestionServer.UpdateEssayQuestion(eq);
        return convertToResponse(eq, questionType, success);
    }

    private QuestionResponse convertToResponse(MultipleChoiceQuestion mcq, QuestionType questionType) {
        return convertToResponse(mcq, questionType, true);
    }

    private QuestionResponse convertToResponse(MultipleChoiceQuestion mcq, QuestionType questionType, boolean success) {
        QuestionResponse response = new QuestionResponse();
        response.setId(mcq.getId());
        response.setQuestionTypeId(questionType.getId());
        response.setQuestionTypeCode(questionType.getTypeCode());
        response.setQuestionTypeName(questionType.getTypeName());
        response.setPaperId(mcq.getPaperId());
        response.setQuestionText(mcq.getQuestionText());
        response.setScore(mcq.getScore());
        response.setSortOrder(mcq.getSortOrder());
        response.setSuccess(success);

        Map<String, Object> details = new HashMap<>();
        details.put("optionA", mcq.getOptionA());
        details.put("optionB", mcq.getOptionB());
        details.put("optionC", mcq.getOptionC());
        details.put("optionD", mcq.getOptionD());
        details.put("optionE", mcq.getOptionE());
        details.put("optionF", mcq.getOptionF());
        details.put("correctAnswer", mcq.getCorrectAnswer());
        details.put("explanation", mcq.getExplanation());
        response.setDetails(details);

        return response;
    }

    private QuestionResponse convertToResponse(AnalysisQuestion aq, QuestionType questionType) {
        return convertToResponse(aq, questionType, true);
    }

    private QuestionResponse convertToResponse(AnalysisQuestion aq, QuestionType questionType, boolean success) {
        QuestionResponse response = new QuestionResponse();
        response.setId(aq.getId());
        response.setQuestionTypeId(questionType.getId());
        response.setQuestionTypeCode(questionType.getTypeCode());
        response.setQuestionTypeName(questionType.getTypeName());
        response.setPaperId(aq.getPaperId());
        response.setQuestionText(aq.getQuestionText());
        response.setScore(aq.getScore());
        response.setSortOrder(aq.getSortOrder());
        response.setSuccess(success);

        Map<String, Object> details = new HashMap<>();
        details.put("correctAnswer", aq.getCorrectAnswer());
        details.put("explanation", aq.getExplanation());
        response.setDetails(details);

        return response;
    }

    private QuestionResponse convertToResponse(EssayQuestion eq, QuestionType questionType) {
        return convertToResponse(eq, questionType, true);
    }

    private QuestionResponse convertToResponse(EssayQuestion eq, QuestionType questionType, boolean success) {
        QuestionResponse response = new QuestionResponse();
        response.setId(eq.getId());
        response.setQuestionTypeId(questionType.getId());
        response.setQuestionTypeCode(questionType.getTypeCode());
        response.setQuestionTypeName(questionType.getTypeName());
        response.setPaperId(eq.getPaperId());
        response.setQuestionText(eq.getQuestionText());
        response.setScore(eq.getScore());
        response.setSortOrder(eq.getSortOrder());
        response.setSuccess(success);

        Map<String, Object> details = new HashMap<>();
        details.put("referenceAnswer", eq.getReferenceAnswer());
        details.put("explanation", eq.getExplanation());
        response.setDetails(details);

        return response;
    }

    private QuestionResponse createErrorResponse(String errorMessage) {
        QuestionResponse response = new QuestionResponse();
        response.setSuccess(false);
        response.setErrorMessage(errorMessage);
        return response;
    }
}