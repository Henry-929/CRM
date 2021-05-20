package com.ssm.settings.controller;

import com.ssm.exception.LoginException;
import com.ssm.settings.pojo.User;
import com.ssm.settings.service.UserService;
import com.ssm.utils.JsonUtils;
import com.ssm.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/settings/user")
public class UserController {

    @Autowired
    @Qualifier("UserServiceImpl")
    private UserService userService;

    @RequestMapping("/login.do")
    @ResponseBody
    public String login(String loginAct, String loginPwd, HttpServletRequest request) throws LoginException {
        loginPwd= MD5Util.getMD5(loginPwd);
        String ip = request.getRemoteAddr();

        User user = userService.login(loginAct, loginPwd, ip);
        request.getSession().setAttribute("user", user);

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("success", true);

        return JsonUtils.getObjJson(map);

    }
}
