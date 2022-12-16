package com.rui.online.VO.chart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @Author 蒲锐
 * @CreateTme 2022/11/3 14:10
 * Version1.0.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserLogVo {

    private String userName;

    private String content;

    private Date createTime;
}
