package com.rui.online.service;

import com.github.pagehelper.PageInfo;
import com.rui.online.VO.chart.UserCorrect;
import com.rui.online.VO.student.exam.ExamPaperSubmitItemVM;
import com.rui.online.VO.student.question.answer.QuestionPageStudentRequestVM;
import com.rui.online.domain.other.ExamPaperAnswerUpdate;
import com.rui.online.pojo.ExamPaperQuestionCustomerAnswer;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author purui
 * @since 2022-10-15
 */
public interface IExamPaperQuestionCustomerAnswerService extends IService<ExamPaperQuestionCustomerAnswer> {

    Integer selectAllCount();

    List<Integer> selectMothCount();

    List<ExamPaperQuestionCustomerAnswer> selectListByPaperAnswerId(Integer id);

    ExamPaperSubmitItemVM examPaperQuestionCustomerAnswerToVM(ExamPaperQuestionCustomerAnswer qa);

    int updateScore(List<ExamPaperAnswerUpdate> examPaperAnswerUpdates);

    PageInfo<ExamPaperQuestionCustomerAnswer> studentPage(QuestionPageStudentRequestVM model);

    ExamPaperQuestionCustomerAnswer selectById(Integer id);

    void insertList(List<ExamPaperQuestionCustomerAnswer> examPaperQuestionCustomerAnswers);

    List<UserCorrect> selectUserCorrect();
}
