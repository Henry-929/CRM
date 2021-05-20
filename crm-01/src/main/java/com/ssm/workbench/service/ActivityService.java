package com.ssm.workbench.service;

import com.ssm.exception.ActivityException;
import com.ssm.vo.PaginationVo;
import com.ssm.workbench.pojo.Activity;
import com.ssm.workbench.pojo.ActivityRemark;

import java.util.List;
import java.util.Map;

public interface ActivityService {
    boolean save(Activity activity);

    PaginationVo<Activity> pageList(Map<String, Object> map);

    void delete(String[] ids) throws ActivityException;

    Map<String, Object> getUserListAndActivity(String id);

    boolean update(Activity activity);

    Activity detail(String id);

    List<ActivityRemark> getRemarkListByAid(String activityId);

    boolean deleteRemarkById(String id);

    boolean saveRemark(ActivityRemark ar);

    boolean updateRemark(ActivityRemark ar);

    List<Activity> getActivityListByClueId(String clueId);

    List<Activity> getActivityListByNameAndNotByClueId(Map<String,String> map);

    List<Activity> getActivityListByName(String aname);
}
