package com.rui.online.mapper;

import com.rui.online.VO.admin.education.SubjectPageRequestVM;
import com.rui.online.VO.chart.SubjectQuestionNum;
import com.rui.online.pojo.Subject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

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
public interface SubjectMapper extends BaseMapper<Subject> {

    List<Subject> page(SubjectPageRequestVM requestVM);

    List<SubjectQuestionNum> selectSubjectQuestionNum();
}
