package com.rui.online.controller.student;

import com.rui.online.VO.student.education.SubjectVM;
import com.rui.online.base.RestResponse;
import com.rui.online.controller.BaseApiController;
import com.rui.online.pojo.Subject;
import com.rui.online.pojo.User;
import com.rui.online.service.ISubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author 蒲锐
 * @CreateTme 2022/10/18 14:43
 * Version1.0.0
 */

@RestController("StudentEducationController")
@RequestMapping(value = "/api/student/education")
public class EducationController extends BaseApiController {
    private final ISubjectService subjectService;

    @Autowired
    public EducationController(ISubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @RequestMapping(value = "/subject/list", method = RequestMethod.POST)
    public RestResponse<List<SubjectVM>> list() {
        User user = getCurrentUser();
        List<Subject> subjects = subjectService.getSubjectByLevel(user.getUserLevel());
        List<SubjectVM> subjectVMS = subjects.stream().map(d -> {
            SubjectVM subjectVM = modelMapper.map(d, SubjectVM.class);
            subjectVM.setId(String.valueOf(d.getId()));
            return subjectVM;
        }).collect(Collectors.toList());
        return RestResponse.ok(subjectVMS);
    }
}
