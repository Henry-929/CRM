package com.ssm.workbench.dao;

import com.ssm.workbench.pojo.ActivityRemark;

import java.util.List;

public interface ActivityRemarkDao {
    Integer getCountByAids(String[] ids);

    Integer deleteByAids(String[] ids);

    List<ActivityRemark> getRemarkListByAid(String activityId);

    Integer deleteRemarkById(String id);

    Integer saveRemark(ActivityRemark ar);

    Integer updateRemark(ActivityRemark ar);
}
