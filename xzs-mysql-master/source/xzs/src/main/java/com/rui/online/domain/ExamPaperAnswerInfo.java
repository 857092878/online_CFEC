package com.rui.online.domain;


import com.rui.online.pojo.ExamPaper;
import com.rui.online.pojo.ExamPaperAnswer;
import com.rui.online.pojo.ExamPaperQuestionCustomerAnswer;

import java.util.List;

public class ExamPaperAnswerInfo {
    public ExamPaper examPaper;
    public ExamPaperAnswer examPaperAnswer;
    public List<ExamPaperQuestionCustomerAnswer> examPaperQuestionCustomerAnswers;

    public ExamPaper getExamPaper() {
        return examPaper;
    }

    public void setExamPaper(ExamPaper examPaper) {
        this.examPaper = examPaper;
    }

    public ExamPaperAnswer getExamPaperAnswer() {
        return examPaperAnswer;
    }

    public void setExamPaperAnswer(ExamPaperAnswer examPaperAnswer) {
        this.examPaperAnswer = examPaperAnswer;
    }

    public List<ExamPaperQuestionCustomerAnswer> getExamPaperQuestionCustomerAnswers() {
        return examPaperQuestionCustomerAnswers;
    }

    public void setExamPaperQuestionCustomerAnswers(List<ExamPaperQuestionCustomerAnswer> examPaperQuestionCustomerAnswers) {
        this.examPaperQuestionCustomerAnswers = examPaperQuestionCustomerAnswers;
    }
}
