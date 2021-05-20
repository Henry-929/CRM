package com.ssm.settings.service;

import com.ssm.exception.LoginException;
import com.ssm.settings.pojo.User;

import java.util.List;

public interface UserService {
    User login(String loginAct, String loginPwd, String ip) throws LoginException;

    List<User> selectUser();

}
