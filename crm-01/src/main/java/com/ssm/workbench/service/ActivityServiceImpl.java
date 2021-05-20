package com.ssm.workbench.service;

import com.ssm.exception.ActivityException;
import com.ssm.settings.dao.UserMapper;
import com.ssm.settings.pojo.User;
import com.ssm.vo.PaginationVo;
import com.ssm.workbench.dao.ActivityDao;
import com.ssm.workbench.dao.ActivityRemarkDao;
import com.ssm.workbench.pojo.Activity;
import com.ssm.workbench.pojo.ActivityRemark;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityServiceImpl implements ActivityService{

    private ActivityDao activityDao;
    private ActivityRemarkDao activityRemarkDao;
    private UserMapper userMapper;

    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public void setActivityRemarkDao(ActivityRemarkDao activityRemarkDao) {
        this.activityRemarkDao = activityRemarkDao;
    }

    public void setActivityDao(ActivityDao activityDao) {
        this.activityDao = activityDao;
    }

    @Override
    public boolean save(Activity activity) {
        boolean flag = true;
        Integer result = activityDao.save(activity);
        if(result != 1){
            flag = false;
        }
        return flag;
    }

    @Override
    public PaginationVo<Activity> pageList(Map<String, Object> map) {
        // 取得total
        Integer total = activityDao.getTotalByCondition(map);
        // 取得dataList
        List<Activity> dataList = activityDao.getActivityListByCondition(map);

        // 将total和dataList封装到vo中
        PaginationVo<Activity> vo = new PaginationVo<Activity>();
        vo.setTotal(total);
        vo.setDataList(dataList);

        // 将vo返回
        return vo;
    }

    @Override
    public void delete(String[] ids) throws ActivityException {
        // 查询出需要删除的备注的数量
        Integer count1 = activityRemarkDao.getCountByAids(ids);
        // 删除备注,返回受到影响的条数(实际删除的数量)
        Integer count2 = activityRemarkDao.deleteByAids(ids);
        if(count1 != count2){
            System.out.println("备注出错");
            throw new ActivityException("无法删除活动备注");
        }

        // 删除市场活动
        Integer count3 = activityDao.delete(ids);
        if(count3 != ids.length){
            System.out.println("市场活动出错");
            throw new ActivityException("无法删除市场活动");
        }

    }

    @Override
    public Map<String, Object> getUserListAndActivity(String id) {
        // 取uList
        List<User> uList = userMapper.selectUser();
        // 取a
        Activity a = activityDao.getById(id);
        // 打包到map集合中
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("uList",uList);
        map.put("a",a);
        return map;
    }

    @Override
    public boolean update(Activity activity) {
        boolean flag = true;
        Integer result = activityDao.update(activity);
        if(result != 1){
            flag = false;
        }
        return flag;
    }

    @Override
    public Activity detail(String id) {
        Activity activity = activityDao.detail(id);
        return activity;
    }

    @Override
    public List<ActivityRemark> getRemarkListByAid(String activityId) {
        List<ActivityRemark> arList = activityRemarkDao.getRemarkListByAid(activityId);
        return arList;
    }

    @Override
    public boolean deleteRemarkById(String id) {
        boolean flag = true;
        Integer result = activityRemarkDao.deleteRemarkById(id);
        if(result != 1){
            flag = false;
        }
        return flag;
    }

    @Override
    public boolean saveRemark(ActivityRemark ar) {
        boolean flag = true;
        Integer result = activityRemarkDao.saveRemark(ar);
        if(result != 1){
            flag = false;
        }
        return flag;
    }

    @Override
    public boolean updateRemark(ActivityRemark ar) {
        boolean flag = true;
        Integer result = activityRemarkDao.updateRemark(ar);
        if(result != 1){
            flag = false;
        }
        return flag;
    }

    @Override
    public List<Activity> getActivityListByClueId(String clueId) {
        List<Activity> aList = activityDao.getActivityListByClueId(clueId);
        return aList;
    }

    @Override
    public List<Activity> getActivityListByNameAndNotByClueId(Map<String,String> map) {
        List<Activity> aList = activityDao.getActivityListByNameAndNotByClueId(map);
        return aList;
    }

    @Override
    public List<Activity> getActivityListByName(String aname) {
        List<Activity> aList = activityDao.getActivityListByName(aname);
        return aList;
    }
}
