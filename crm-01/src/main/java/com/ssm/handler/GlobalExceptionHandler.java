package com.ssm.handler;

import com.ssm.exception.ActivityException;
import com.ssm.exception.LoginException;
import com.ssm.utils.JsonUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LoginException.class)
    @ResponseBody
    public String doLoginException(Exception exception){
        Map<String,Object> objectMap = new HashMap<String,Object>();
        objectMap.put("success", false);
        objectMap.put("msg", exception.getMessage());
        return JsonUtils.getObjJson(objectMap);
    }

    @ExceptionHandler(ActivityException.class)
    @ResponseBody
    public String doActivityException(Exception exception){
        Map<String,Object> objectMap = new HashMap<String,Object>();
        objectMap.put("success", false);
        objectMap.put("msg", exception.getMessage());
        return JsonUtils.getObjJson(objectMap);
    }
}
