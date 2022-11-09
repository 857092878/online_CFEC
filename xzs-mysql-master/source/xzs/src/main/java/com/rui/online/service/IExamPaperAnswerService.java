package com.rui.online.service;

import com.github.pagehelper.PageInfo;
import com.rui.online.VO.admin.paper.ExamPaperAnswerPageRequestVM;
import com.rui.online.VO.student.exam.ExamPaperSubmitVM;
import com.rui.online.VO.student.exampaper.ExamPaperAnswerPageVM;
import com.rui.online.domain.ExamPaperAnswerInfo;
import com.rui.online.pojo.ExamPaperAnswer;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rui.online.pojo.User;

import java.util.Date;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author purui
 * @since 2022-10-15
 */
public interface IExamPaperAnswerService extends IService<ExamPaperAnswer> {

    Integer selectAllCount();

    PageInfo<ExamPaperAnswer> adminPage(ExamPaperAnswerPageRequestVM model);

    ExamPaperAnswerInfo calculateExamPaperAnswer(ExamPaperSubmitVM examPaperSubmitVM, User user);

    ExamPaperAnswer selectById(Integer id);

    ExamPaperSubmitVM examPaperAnswerToVM(Integer id);

    String judge(ExamPaperSubmitVM examPaperSubmitVM);

    PageInfo<ExamPaperAnswer> studentPage(ExamPaperAnswerPageVM requestVM);

    void insertByFilter(ExamPaperAnswer examPaperAnswer);

    Integer allAnswer();

    Integer answerByMonth(Date startTime, Date endTime);
}
