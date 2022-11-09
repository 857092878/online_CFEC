package com.rui.online.listener;

import com.rui.online.domain.ExamPaperAnswerInfo;
import com.rui.online.domain.enums.ExamPaperTypeEnum;
import com.rui.online.domain.enums.QuestionTypeEnum;
import com.rui.online.event.CalculateExamPaperAnswerCompleteEvent;
import com.rui.online.pojo.ExamPaper;
import com.rui.online.pojo.ExamPaperAnswer;
import com.rui.online.pojo.ExamPaperQuestionCustomerAnswer;
import com.rui.online.pojo.TextContent;
import com.rui.online.service.IExamPaperAnswerService;
import com.rui.online.service.IExamPaperQuestionCustomerAnswerService;
import com.rui.online.service.ITaskExamCustomerAnswerService;
import com.rui.online.service.ITextContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


/**
 * @version 3.5.0
 * @description: The type Calculate exam paper answer listener.
 * Copyright (C), 2020-2021, 武汉思维跳跃科技有限公司
 * @date 2021/12/25 9:45
 */
@Component
public class CalculateExamPaperAnswerListener implements ApplicationListener<CalculateExamPaperAnswerCompleteEvent> {

    private final IExamPaperAnswerService examPaperAnswerService;
    private final IExamPaperQuestionCustomerAnswerService examPaperQuestionCustomerAnswerService;
    private final ITextContentService textContentService;
    private final ITaskExamCustomerAnswerService examCustomerAnswerService;

    /**
     * Instantiates a new Calculate exam paper answer listener.
     *
     * @param examPaperAnswerService                 the exam paper answer service
     * @param examPaperQuestionCustomerAnswerService the exam paper question customer answer service
     * @param textContentService                     the text content service
     * @param examCustomerAnswerService              the exam customer answer service
     */
    @Autowired
    public CalculateExamPaperAnswerListener(IExamPaperAnswerService examPaperAnswerService, IExamPaperQuestionCustomerAnswerService examPaperQuestionCustomerAnswerService, ITextContentService textContentService, ITaskExamCustomerAnswerService examCustomerAnswerService) {
        this.examPaperAnswerService = examPaperAnswerService;
        this.examPaperQuestionCustomerAnswerService = examPaperQuestionCustomerAnswerService;
        this.textContentService = textContentService;
        this.examCustomerAnswerService = examCustomerAnswerService;
    }

    @Override
    @Transactional
    public void onApplicationEvent(CalculateExamPaperAnswerCompleteEvent calculateExamPaperAnswerCompleteEvent) {
        Date now = new Date();

        ExamPaperAnswerInfo examPaperAnswerInfo = (ExamPaperAnswerInfo) calculateExamPaperAnswerCompleteEvent.getSource();
        ExamPaper examPaper = examPaperAnswerInfo.getExamPaper();
        ExamPaperAnswer examPaperAnswer = examPaperAnswerInfo.getExamPaperAnswer();
        List<ExamPaperQuestionCustomerAnswer> examPaperQuestionCustomerAnswers = examPaperAnswerInfo.getExamPaperQuestionCustomerAnswers();

        examPaperAnswerService.insertByFilter(examPaperAnswer);
        examPaperQuestionCustomerAnswers.stream().filter(a -> QuestionTypeEnum.needSaveTextContent(a.getQuestionType())).forEach(d -> {
            TextContent textContent = new TextContent(d.getAnswer(), now);
            textContentService.insertByFilter(textContent);
            d.setTextContentId(textContent.getId());
            d.setAnswer(null);
        });
        examPaperQuestionCustomerAnswers.forEach(d -> {
            d.setExamPaperAnswerId(examPaperAnswer.getId());
        });
        examPaperQuestionCustomerAnswerService.insertList(examPaperQuestionCustomerAnswers);

        switch (ExamPaperTypeEnum.fromCode(examPaper.getPaperType())) {
            case Task: {
                examCustomerAnswerService.insertOrUpdate(examPaper, examPaperAnswer, now);
                break;
            }
            default:
                break;
        }
    }
}
