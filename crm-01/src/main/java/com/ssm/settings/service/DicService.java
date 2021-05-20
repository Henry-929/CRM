package com.ssm.settings.service;

import com.ssm.settings.pojo.DicValue;

import javax.servlet.ServletContext;
import java.util.List;
import java.util.Map;

public interface DicService {
    Map<String, List<DicValue>> getAll(ServletContext application);
}
