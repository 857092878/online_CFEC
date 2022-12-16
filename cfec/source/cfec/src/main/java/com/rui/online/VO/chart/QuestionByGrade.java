package com.rui.online.VO.chart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author 蒲锐
 * @CreateTme 2022/11/7 20:53
 * Version1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class QuestionByGrade {

    private String grade;

    private Integer count;
}
