package com.ScoopLink.questionBank;

import com.ScoopLink.manageQuestion.questionBank.dto.QuestionBank;
import com.ScoopLink.manageQuestion.questionBank.server.QuestionBankServer;
import com.ScoopLink.paper.PaperMapper;
import com.ScoopLink.questionBankMapper.QuestionBankMapper;
import com.ScoopLink.dto.PageRequest;
import com.ScoopLink.dto.PageResponse;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionBankServerImpl implements QuestionBankServer {

    @Resource
    private QuestionBankMapper questionBankMapper;
    @Resource
    private PaperMapper paperMapper;
    
    @Override
    public QuestionBank GetQuestionBank(Long id) {
        return questionBankMapper.selectById(id);
    }

    @Override
    @Transactional
    public QuestionBank CreateQuestionBank(QuestionBank questionBank) {

        questionBank.setCreateTime(LocalDateTime.now());

        // 设置创建者ID - 这里需要从当前登录用户获取，暂时使用默认值1
        // TODO: 从认证信息中获取当前用户ID
        if (questionBank.getCreatorId() == null) {
            questionBank.setCreatorId(1L); // 临时使用默认值
        }
        int result = questionBankMapper.insertQuestionBank(questionBank);
        if (result > 0) {
            return questionBank;
        }
        return new QuestionBank();
    }

    @Override
    public List<QuestionBank> GetQuestionBankList() {
        return questionBankMapper.selectAll();
    }

    @Override
    public PageResponse<QuestionBank> GetQuestionBankPage(PageRequest pageRequest) {
        // 计算偏移量
        int offset = pageRequest.getPage() * pageRequest.getSize();
        
        // 获取总数
        int total = questionBankMapper.countAll();
        
        // 获取分页数据
        List<QuestionBank> banks = questionBankMapper.selectWithPaging(offset, pageRequest.getSize());
        
        // 创建并返回分页响应
        return PageResponse.of(pageRequest.getPage(), pageRequest.getSize(), total, banks);
    }

    @Override
    @Transactional(rollbackFor =Exception.class)
    public QuestionBank UpdateQuestionBank(QuestionBank questionBank) {
        // 检查是否存在该题库
        QuestionBank bank = questionBankMapper.selectById(questionBank.getId());
        if (bank == null) {
            return new QuestionBank();
        }
        // 更新题库信息
        questionBank.setCreatorId(bank.getCreatorId());
        int result = questionBankMapper.updateQuestionBank(questionBank);
        return result > 0 ? questionBank : new QuestionBank();
    }

    @Override
    @Transactional(rollbackFor =Exception.class)
    public boolean DeleteQuestionBank(Long id) {
        // 检查是否存在该题库
        QuestionBank bank = questionBankMapper.selectById(id);
        if (bank == null) {
            return false;
        }
        // 检查是否有试卷引用该题库
        int paperCount = paperMapper.selectCountByQuestionBankId(id);
        if (paperCount > 0) {
            return false;
        }

        int result = questionBankMapper.deleteById(id);
        return result > 0;
    }

    @Override
    @Transactional(rollbackFor =Exception.class)
    public boolean DeleteQuestionBankList(List<Long> ids) {
        // 批量删除题库
        // 过滤查不到的id
        List<Long> existIds = ids.stream()
                .filter(id -> questionBankMapper.selectById(id) != null)
                .collect(Collectors.toList());
       int result = questionBankMapper.deleteByIds(existIds);
        return result > 0;
    }
}