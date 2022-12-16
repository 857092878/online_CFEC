package com.rui.online.mapper;

import com.rui.online.VO.chart.UserCorrect;
import com.rui.online.VO.student.question.answer.QuestionPageStudentRequestVM;
import com.rui.online.domain.other.ExamPaperAnswerUpdate;
import com.rui.online.domain.other.KeyValue;
import com.rui.online.pojo.ExamPaperQuestionCustomerAnswer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author purui
 * @since 2022-10-15
 */
@Mapper
public interface ExamPaperQuestionCustomerAnswerMapper extends BaseMapper<ExamPaperQuestionCustomerAnswer> {

    List<KeyValue> selectCountByDate(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    int updateScore(List<ExamPaperAnswerUpdate> examPaperAnswerUpdates);

    List<ExamPaperQuestionCustomerAnswer> studentPage(QuestionPageStudentRequestVM requestVM);

    void insertList(List<ExamPaperQuestionCustomerAnswer> examPaperQuestionCustomerAnswers);

    List<UserCorrect> selectUserCorrect();
}
