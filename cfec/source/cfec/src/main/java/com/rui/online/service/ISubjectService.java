package com.rui.online.service;

import com.github.pagehelper.PageInfo;
import com.rui.online.VO.admin.education.SubjectPageRequestVM;
import com.rui.online.VO.chart.SubjectQuestionNum;
import com.rui.online.pojo.Subject;
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
public interface ISubjectService extends IService<Subject> {

    List<Subject> allSubject();

    PageInfo<Subject> page(SubjectPageRequestVM requestVM);

    Subject selectById(Integer id);

    void insertByFilter(Subject subject);

    void updateByIdFilter(Subject subject);

    Integer levelBySubjectId(Integer subjectId);

    List<Subject> getSubjectByLevel(Integer userLevel);

    List<SubjectQuestionNum> selectSubjectQuestionNum();
}
