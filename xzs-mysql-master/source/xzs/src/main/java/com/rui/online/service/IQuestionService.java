package com.rui.online.service;

import com.github.pagehelper.PageInfo;
import com.rui.online.VO.admin.question.QuestionEditRequestVM;
import com.rui.online.VO.admin.question.QuestionPageRequestVM;
import com.rui.online.pojo.Question;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author purui
 * @since 2022-10-15
 */
public interface IQuestionService extends IService<Question> {

    Integer selectAllCount();

    QuestionEditRequestVM getQuestionEditRequestVM(Integer questionId);

    QuestionEditRequestVM getQuestionEditRequestVM(Question question);

    PageInfo<Question> page(QuestionPageRequestVM requestVM);

    Question insertFullQuestion(QuestionEditRequestVM model, Integer userId);

    Question updateFullQuestion(QuestionEditRequestVM model);

    Question selectById(Integer id);

    void updateByIdFilter(Question question);
}
