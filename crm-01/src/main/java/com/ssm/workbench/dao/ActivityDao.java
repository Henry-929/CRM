package com.ssm.workbench.dao;

import com.ssm.workbench.pojo.Activity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ActivityDao {
    Integer save(Activity activity);

    List<Activity> getActivityListByCondition(Map<String, Object> map);

    Integer getTotalByCondition(Map<String, Object> map);

    Integer delete(String[] ids);

    Activity getById(String id);

    Integer update(Activity activity);

    Activity detail(String id);

    List<Activity> getActivityListByClueId(String clueId);

    List<Activity> getActivityListByNameAndNotByClueId(Map<String,String> map);

    List<Activity> getActivityListByName(String aname);
}
