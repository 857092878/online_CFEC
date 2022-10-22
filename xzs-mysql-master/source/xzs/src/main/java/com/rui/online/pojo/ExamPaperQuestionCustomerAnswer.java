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
@TableName("t_exam_paper_question_customer_answer")
public class ExamPaperQuestionCustomerAnswer implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer questionId;

    private Integer examPaperId;

    private Integer examPaperAnswerId;

    private Integer questionType;

    private Integer subjectId;

    private Integer customerScore;

    private Integer questionScore;

    private Integer questionTextContentId;

    private String answer;

    private Integer textContentId;

    private Boolean doRight;

    private Integer createUser;

    private Date createTime;

    private Integer itemOrder;


}
