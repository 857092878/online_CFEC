package com.rui.online.service;

import com.github.pagehelper.PageInfo;
import com.rui.online.VO.admin.exam.ExamPaperEditRequestVM;
import com.rui.online.VO.admin.exam.ExamPaperPageRequestVM;
import com.rui.online.VO.student.dashboard.PaperFilter;
import com.rui.online.VO.student.dashboard.PaperInfo;
import com.rui.online.VO.student.exam.ExamPaperPageVM;
import com.rui.online.pojo.ExamPaper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rui.online.pojo.User;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author purui
 * @since 2022-10-15
 */
public interface IExamPaperService extends IService<ExamPaper> {

    Integer selectAllCount();

    PageInfo<ExamPaper> page(ExamPaperPageRequestVM requestVM);

    ExamPaperEditRequestVM examPaperToVM(Integer id);

    ExamPaper savePaperFromVM(ExamPaperEditRequestVM model, User currentUser);

    ExamPaper selectById(Integer id);

    void updateByIdFilter(ExamPaper examPaper);

    PageInfo<ExamPaper> taskExamPage(ExamPaperPageRequestVM model);

    List<PaperInfo> indexPaper(PaperFilter fixedPaperFilter);

    PageInfo<ExamPaper> studentPage(ExamPaperPageVM model);
}
