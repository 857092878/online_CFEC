package com.rui.online.mapper;

import com.rui.online.VO.admin.question.QuestionPageRequestVM;
import com.rui.online.VO.chart.QuestionByGrade;
import com.rui.online.pojo.Question;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author purui
 * @since 2022-10-15
 */
@Mapper
public interface QuestionMapper extends BaseMapper<Question> {

    List<Question> page(QuestionPageRequestVM requestVM);

    List<QuestionByGrade> selectGradeQuestion();
}
