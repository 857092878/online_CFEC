package com.rui.online.VO.chart;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author 蒲锐
 * @CreateTme 2022/11/9 13:43
 * Version1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserCorrect {

    private String username;

    private Integer correct;

}
