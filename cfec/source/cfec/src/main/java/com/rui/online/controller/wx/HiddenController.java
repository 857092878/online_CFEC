package com.rui.online.controller.wx;

import com.rui.online.base.RestResponse;
import com.rui.online.pojo.Hidden;
import com.rui.online.service.IHiddenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author 蒲锐
 * @CreateTme 2022/11/29 20:50
 * Version1.0.0
 */

@Controller("WXHiddenController")
@RequestMapping(value = "/api/wx/hidden")
@ResponseBody
public class HiddenController extends BaseWXApiController {
    @Autowired
    private IHiddenService hiddenService;
    @RequestMapping(value = "/status",method = RequestMethod.POST)
    public RestResponse<Hidden> finHidden(){
        Hidden hidden = hiddenService.findHidden();
        return RestResponse.ok(hidden);
    }
}
