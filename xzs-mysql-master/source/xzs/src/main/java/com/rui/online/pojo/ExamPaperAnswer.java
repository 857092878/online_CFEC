package com.rui.online.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author purui
 * @since 2022-10-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_exam_paper_answer")
public class ExamPaperAnswer implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer examPaperId;

    private String paperName;

    private Integer paperType;

    private Integer subjectId;

    private Integer systemScore;

    private Integer userScore;

    private Integer paperScore;

    private Integer questionCorrect;

    private Integer questionCount;

    private Integer doTime;

    private Integer status;

    private Integer createUser;

    private Date createTime;

    private Integer taskExamId;


}
