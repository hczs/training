package com.hc.qa.service.impl;

import com.hc.qa.entity.Question;
import com.hc.qa.mapper.QuestionMapper;
import com.hc.qa.service.QuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author houcheng
 * @since 2021-04-02
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {

    @Override
    public boolean updateAnswerNum(String questionId) {
        Question question = baseMapper.selectById(questionId);
        question.setAnswerNum(question.getAnswerNum() + 1);
        return baseMapper.updateById(question) > 0;
    }
}
