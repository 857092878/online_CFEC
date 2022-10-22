package com.rui.online.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 *
 * </p>
 *
 * @author purui
 * @since 2022-10-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_text_content")
@NoArgsConstructor
@AllArgsConstructor
public class TextContent implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String content;

    private Date createTime;

    public TextContent(String content, Date createTime) {
        this.content = content;
        this.createTime = createTime;
    }


}
