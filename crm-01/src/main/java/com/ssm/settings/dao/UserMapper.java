package com.ssm.settings.dao;

import com.ssm.settings.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {
   List<User> selectUser();

   User login(Map<String, String> map);
}