package com.ssm.settings.dao;

import com.ssm.settings.pojo.DicValue;

import java.util.List;

public interface DicValueDao {
    List<DicValue> getListByCode(String code);
}
