package com.rui.online.event;

import com.rui.online.domain.ExamPaperAnswerInfo;
import org.springframework.context.ApplicationEvent;

/**
 * @version 3.3.0
 * @description: The type Calculate exam paper answer complete event.
 * Copyright (C), 2022-2022, 白色巨塔
 * @date 2022/5/25 10:45
 */
public class CalculateExamPaperAnswerCompleteEvent extends ApplicationEvent {


    private final ExamPaperAnswerInfo examPaperAnswerInfo;


    /**
     * Instantiates a new Calculate exam paper answer complete event.
     *
     * @param examPaperAnswerInfo the exam paper answer info
     */
    public CalculateExamPaperAnswerCompleteEvent(final ExamPaperAnswerInfo examPaperAnswerInfo) {
        super(examPaperAnswerInfo);
        this.examPaperAnswerInfo = examPaperAnswerInfo;
    }

    /**
     * Gets exam paper answer info.
     *
     * @return the exam paper answer info
     */
    public ExamPaperAnswerInfo getExamPaperAnswerInfo() {
        return examPaperAnswerInfo;
    }

}
