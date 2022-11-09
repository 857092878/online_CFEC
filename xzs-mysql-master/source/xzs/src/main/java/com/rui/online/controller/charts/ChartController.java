package com.rui.online.controller.charts;

import com.rui.online.VO.chart.*;
import com.rui.online.controller.BaseApiController;
import com.rui.online.service.*;
import com.rui.online.utils.DateTimeUtil;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author 蒲锐
 * @CreateTme 2022/11/3 14:03
 * Version1.0.0
 */

@RestController("ChartUserLogController")
@RequestMapping(value = "/api/chart/")
public class ChartController{

    @Autowired
    private IUserEventLogService userEventLogService;
    @Autowired
    private IExamPaperService examPaperService;
    @Autowired
    private IQuestionService questionService;
    @Autowired
    IExamPaperAnswerService examPaperAnswerService;
    @Autowired
    IExamPaperQuestionCustomerAnswerService examPaperQuestionCustomerAnswerService;
    @Autowired
    private ISubjectService subjectService;
    @Autowired
    private IUserService userService;


    /*
    用户日志信息，最近20条
     */
    @PostMapping("/userlog")
    public List<UserLogVoByStr> UserLog(){

        UserLogVoByStr userLogVoByStr = new UserLogVoByStr();

        List<UserLogVoByStr> userLogVoByStrs = new ArrayList<>();

        List<UserLogVo> logVos = userEventLogService.selectUserLog();

        for (UserLogVo logVo : logVos) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            userLogVoByStr.setCreateTime(simpleDateFormat.format(logVo.getCreateTime()));
            userLogVoByStr.setContent(logVo.getContent());
            userLogVoByStr.setUserName(logVo.getUserName());
            userLogVoByStrs.add(userLogVoByStr);
            }
        return userLogVoByStrs;

    }


    /*
    试题数
     */
    @PostMapping("/testNum")
    public Map<String,Object> TestNum(){

        Map<String,Object> map = new HashMap<>();
        //试卷总数
        Integer examPaperCount = examPaperService.selectAllCount();
        //题目总数
        Integer questionCount = questionService.selectAllCount();
        //答卷总数
        Integer doExamPaperCount = examPaperAnswerService.selectAllCount();
        //答题总数
        Integer doQuestionCount = examPaperQuestionCustomerAnswerService.selectAllCount();
        map.put("examPaperCount",examPaperCount);
        map.put("questionCount",questionCount);
        map.put("doExamPaperCount",doExamPaperCount);
        map.put("doQuestionCount",doQuestionCount);
        return map;
    }
    @PostMapping("/questionGrade")
    public  Map<String,Object> QuestionGrade(){
        List<QuestionByGrade> questionByGrades = questionService.selectallGradeQuestion();
        List<String> grade = new ArrayList<>();
        List<Integer> count = new ArrayList<>();
        for (QuestionByGrade questionByGrade : questionByGrades) {
            grade.add(questionByGrade.getGrade());
            count.add(questionByGrade.getCount());
        }

        Map<String,Object> map = new HashMap<>();
        map.put("grade",grade);
        map.put("count",count);
        return map;
    }
    @PostMapping("/userActivity")
    public Map<String,Object> UserActivity(){
        Map<String,Object> map = new HashMap<>();
        List<Integer> month = new ArrayList<>();
        //用户活跃度
        List<Integer> mothDayUserActionValue = userEventLogService.selectMothCount();
        for (int i = 1; i <= mothDayUserActionValue.size(); i++) {
            month.add(i);
        }
        map.put("data",mothDayUserActionValue);
        map.put("month",month);
        return map;
    }
    @PostMapping("/subjectNum")
    public List<SubjectQuestionNum> subjectNum(){
        List<SubjectQuestionNum> subjectQuestionNum = subjectService.selectSubjectQuestionNum();
        return subjectQuestionNum;
    }
    @PostMapping("/userListener")
    public Map<String,Object> UserListener(){

        Date startTime = DateTimeUtil.getMonthStartDay();//这个月第一天
        Date endTime = DateTimeUtil.getMonthEndDay();//这个月最后一天
        Date startDay = DateTimeUtil.getStartDay(new Date());
        Date endDay = DateTimeUtil.getEndDay(new Date());

        Map<String,Object> map = new HashMap<>();
//        今日登陆数
        Integer loginByToday = userEventLogService.loginByToday(startDay,endDay);
//        本月答卷数
        Integer answerByMonth = examPaperAnswerService.answerByMonth(startTime, endTime);
//        管理员数
        Integer adminCount = userService.selectAdmin();
//        共计登陆数
        Integer loginByMonth = userEventLogService.loginByMonth(startTime, endTime);
//        共计答卷数
        Integer allAnswer = examPaperAnswerService.allAnswer();
//        用户数
        Integer userCount = userService.selectUser();

        map.put("loginByToday",loginByToday);
        map.put("answerByMonth",answerByMonth);
        map.put("adminCount",adminCount);
        map.put("loginByMonth",loginByMonth);
        map.put("allAnswer",allAnswer);
        map.put("userCount",userCount);
        return map;
    }

    @PostMapping("userCorrect")
    public List<UserCorrect>  UserCorrect(){

        List<UserCorrect> userCorrect = examPaperQuestionCustomerAnswerService.selectUserCorrect();
        return userCorrect;
    }

}
