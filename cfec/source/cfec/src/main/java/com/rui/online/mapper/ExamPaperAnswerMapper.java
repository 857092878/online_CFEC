package com.rui.online.mapper;

import com.rui.online.VO.admin.paper.ExamPaperAnswerPageRequestVM;
import com.rui.online.VO.student.exampaper.ExamPaperAnswerPageVM;
import com.rui.online.pojo.ExamPaperAnswer;
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
public interface ExamPaperAnswerMapper extends BaseMapper<ExamPaperAnswer> {

    List<ExamPaperAnswer> adminPage(ExamPaperAnswerPageRequestVM requestVM);

    ExamPaperAnswer getByPidUid(@Param("pid") Integer paperId, @Param("uid") Integer uid);

    List<ExamPaperAnswer> studentPage(ExamPaperAnswerPageVM requestVM);

    Integer answerByMonth(Date startTime, Date endTime);
}
