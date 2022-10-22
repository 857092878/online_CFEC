package com.rui.online.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rui.online.VO.admin.education.SubjectPageRequestVM;
import com.rui.online.pojo.Subject;
import com.rui.online.mapper.SubjectMapper;
import com.rui.online.service.ISubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author purui
 * @since 2022-10-15
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements ISubjectService {
    @Autowired
    private SubjectMapper subjectMapper;

    @Override
    public List<Subject> allSubject() {
        return subjectMapper.selectList(null);
    }

    @Override
    public PageInfo<Subject> page(SubjectPageRequestVM requestVM) {
        return PageHelper.startPage(requestVM.getPageIndex(), requestVM.getPageSize(), "id desc").doSelectPageInfo(() ->
                subjectMapper.page(requestVM)
        );
    }

    @Override
    public Subject selectById(Integer id) {
        return subjectMapper.selectById(id);
    }

    @Override
    public void insertByFilter(Subject subject) {
        subjectMapper.insert(subject);
    }

    @Override
    public void updateByIdFilter(Subject subject) {
        subjectMapper.updateById(subject);
    }

    @Override
    public Integer levelBySubjectId(Integer id) {
        return subjectMapper.selectById(id).getLevel();
    }

    @Override
    public List<Subject> getSubjectByLevel(Integer userLevel) {
        return subjectMapper.selectList(new LambdaQueryWrapper<Subject>().eq(Subject::getLevel,userLevel).orderByAsc(Subject::getItemOrder));
    }
}
