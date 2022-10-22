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
@TableName("t_exam_paper")
public class ExamPaper implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private Integer subjectId;

    private Integer paperType;

    private Integer gradeLevel;

    private Integer score;

    private Integer questionCount;

    private Integer suggestTime;

    private Date limitStartTime;

    private Date limitEndTime;

    private Integer frameTextContentId;

    private Integer createUser;

    private Date createTime;

    private Boolean deleted;

    private Integer taskExamId;


}
