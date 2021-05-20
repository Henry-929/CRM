package com.ssm.workbench.controller;

import com.ssm.exception.ActivityException;
import com.ssm.settings.pojo.User;
import com.ssm.settings.service.UserService;
import com.ssm.utils.DateTimeUtil;
import com.ssm.utils.JsonUtils;
import com.ssm.utils.UUIDUtil;
import com.ssm.vo.PaginationVo;
import com.ssm.workbench.pojo.Activity;
import com.ssm.workbench.pojo.ActivityRemark;
import com.ssm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/workbench/activity")
public class ActivityController {

    @Autowired
    @Qualifier("ActivityServiceImpl")
    private ActivityService activityService;

    @Autowired
    @Qualifier("UserServiceImpl")
    private UserService userService;

    @RequestMapping("/updateRemark.do")
    @ResponseBody
    public String updateRemark(HttpServletRequest request,ActivityRemark ar){
        String editTime = DateTimeUtil.getSysTime();
        String editBy = ((User) request.getSession().getAttribute("user")).getName();
        String editFlag = "1";

        ar.setEditTime(editTime);
        ar.setEditBy(editBy);
        ar.setEditFlag(editFlag);

        boolean flag = activityService.updateRemark(ar);

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("success", flag);
        map.put("ar",ar);

        return JsonUtils.getObjJson(map);
    }

    @RequestMapping("/saveRemark.do")
    @ResponseBody
    public String saveRemark(HttpServletRequest request,ActivityRemark ar){
        String id = UUIDUtil.getUUID();
        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User) request.getSession().getAttribute("user")).getName();
        String editFlag = "0";

        ar.setId(id);
        ar.setCreateTime(createTime);
        ar.setCreateBy(createBy);
        ar.setEditFlag(editFlag);

        boolean flag = activityService.saveRemark(ar);

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("success", flag);
        map.put("ar",ar);

        return JsonUtils.getObjJson(map);
    }

    @RequestMapping("/deleteRemarkById.do")
    @ResponseBody
    public String deleteRemarkById(String id){
        boolean flag = activityService.deleteRemarkById(id);

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("success", flag);

        return JsonUtils.getObjJson(map);
    }

    @RequestMapping("/detail.do")
    public String detail(String id, Model model){
        Activity activity = activityService.detail(id);
        model.addAttribute("activity",activity);
        return "forward:/workbench/activity/detail.jsp";
    }

    @RequestMapping("/getRemarkListByAid.do")
    @ResponseBody
    public String getRemarkListByAid(String activityId){
        List<ActivityRemark> arList = activityService.getRemarkListByAid(activityId);
        return JsonUtils.getObjJson(arList);
    }

    @RequestMapping("/update.do")
    @ResponseBody
    public String update(HttpServletRequest request,Activity activity){
        // 修改时间: 当前系统时间
        String editTime = DateTimeUtil.getSysTime();
        // 修改人: 当前登录用户
        String editBy = ((User) request.getSession().getAttribute("user")).getName();

        activity.setEditTime(editTime);
        activity.setEditBy(editBy);

        boolean flag = activityService.update(activity);

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("success", flag);

        return JsonUtils.getObjJson(map);
    }

    @RequestMapping("/getUserListAndActivity.do")
    @ResponseBody
    public String getUserListAndActivity(String id){
        Map<String,Object> map = activityService.getUserListAndActivity(id);
        return JsonUtils.getObjJson(map);
    }

    @RequestMapping("/delete.do")
    @ResponseBody
    public String delete(@RequestParam("id") String[] ids) throws ActivityException {
        activityService.delete(ids);

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("success", true);

        return JsonUtils.getObjJson(map);
    }

    @RequestMapping("/pageList.do")
    @ResponseBody
    public String pageList(Activity activity, Integer pageSize, Integer pageNo){
        int skipCount = (pageNo - 1) * pageSize;
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("name",activity.getName());
        map.put("owner", activity.getOwner());
        map.put("startDate", activity.getStartDate());
        map.put("endDate", activity.getEndDate());
        map.put("pageSize",pageSize);
        map.put("skipCount",skipCount);

        PaginationVo<Activity> vo = activityService.pageList(map);
        return JsonUtils.getObjJson(vo);
    }

    @RequestMapping("/save.do")
    @ResponseBody
    public String save(HttpServletRequest request, Activity activity){
        String id = UUIDUtil.getUUID();
        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User) request.getSession().getAttribute("user")).getName();

        activity.setId(id);
        activity.setCreateTime(createTime);
        activity.setCreateBy(createBy);

        boolean flag = activityService.save(activity);
        System.out.println(flag);

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("success", flag);

        return JsonUtils.getObjJson(map);
    }

    @RequestMapping("/getUserList.do")
    @ResponseBody
    public String getUserList(){
        List<User> users = userService.selectUser();
        return JsonUtils.getObjJson(users);
    }
}