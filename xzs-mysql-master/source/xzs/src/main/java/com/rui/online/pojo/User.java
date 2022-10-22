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
 * @since 2022-10-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String userUuid;

    private String userName;

    private String password;

    private String realName;

    private Integer age;

    private Integer sex;

    private Date birthDay;

    private Integer userLevel;

    private String phone;

    private Integer role;

    private Integer status;

    private String imagePath;

    private Date createTime;

    private Date modifyTime;

    private Date lastActiveTime;

    private Boolean deleted;

    private String wxOpenId;


}
