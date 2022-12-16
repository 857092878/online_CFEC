package com.rui.online.mapper;

import com.rui.online.VO.admin.exam.ExamPaperPageRequestVM;
import com.rui.online.VO.student.dashboard.PaperFilter;
import com.rui.online.VO.student.dashboard.PaperInfo;
import com.rui.online.VO.student.exam.ExamPaperPageVM;
import com.rui.online.pojo.ExamPaper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
public interface ExamPaperMapper extends BaseMapper<ExamPaper> {

    List<ExamPaper> page(ExamPaperPageRequestVM requestVM);

    List<ExamPaper> taskExamPage(ExamPaperPageRequestVM requestVM);

    int clearTaskPaper(@Param("paperIds") List<Integer> paperIds);

    void updateTaskPaper(Integer id, List<Integer> paperIds);

    List<PaperInfo> indexPaper(PaperFilter paperFilter);

    List<ExamPaper> studentPage(ExamPaperPageVM requestVM);
}
