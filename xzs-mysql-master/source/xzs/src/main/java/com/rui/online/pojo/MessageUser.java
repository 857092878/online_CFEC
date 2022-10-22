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
 * @since 2022-10-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_message_user")
public class MessageUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer messageId;

    private Integer receiveUserId;

    private String receiveUserName;

    private String receiveRealName;

    private Boolean readed;

    private Date createTime;

    private Date readTime;


}
