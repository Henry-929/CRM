package com.ssm.workbench.controller;

import com.ssm.settings.pojo.User;
import com.ssm.settings.service.UserService;
import com.ssm.utils.DateTimeUtil;
import com.ssm.utils.JsonUtils;
import com.ssm.utils.UUIDUtil;
import com.ssm.vo.PaginationVo;
import com.ssm.workbench.pojo.Activity;
import com.ssm.workbench.pojo.Clue;
import com.ssm.workbench.pojo.Tran;
import com.ssm.workbench.service.ActivityService;
import com.ssm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/workbench/clue")
public class ClueController {

    @Autowired
    private ClueService clueService;
    @Autowired
    private UserService userService;
    @Autowired
    private ActivityService activityService;

    @RequestMapping("/pageList.do")
    @ResponseBody
    public String pageList(Integer pageNo,Integer pageSize){
        Integer skipCount = (pageNo-1)*pageSize;
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("pageSize",pageSize);
        map.put("skipCount",skipCount);

        PaginationVo<Clue> vo = clueService.pageList(map);
        return JsonUtils.getObjJson(vo);
    }

    @RequestMapping("/convert.do")
    public String convert(String flag,String clueId,HttpServletRequest request){
        Tran tran = null;
        String createBy = ((User) request.getSession().getAttribute("user")).getName();
        //接收是否需要创建的标记
        if ("a".equals(flag)){
            tran = new Tran();
            //接收交易表单中的参数
            tran.setId(UUIDUtil.getUUID());
            tran.setCreateTime(DateTimeUtil.getSysTime());
            tran.setMoney(request.getParameter("money"));
            tran.setName(request.getParameter("name"));
            tran.setExpectedDate(request.getParameter("expectedDate"));
            tran.setStage(request.getParameter("stage"));
            tran.setActivityId(request.getParameter("activityId"));
            tran.setCreateBy(createBy);
        }
        /**
         * 为业务层传递的参数
         *  1. 必须传递的参数clueId,有了这个clueId之后,我们才知道要转换哪条记录
         *  2. 必须传递的参数t,因为在线索转换的过程中,有可能会临时创建一笔交易(业务层接收的t也有可能是个null)
         */
        boolean returnFlag = clueService.convert(clueId,tran,createBy);
        String strPath = "";
        if (returnFlag){
            strPath = "redirect:/workbench/clue/index.jsp";
        }
        return strPath;
    }

    @RequestMapping("/getActivityListByName.do")
    @ResponseBody
    public String getActivityListByName(String aname){
        List<Activity> aList = activityService.getActivityListByName(aname);
        return JsonUtils.getObjJson(aList);
    }

    @RequestMapping("/bund.do")
    @ResponseBody
    public String bund(@RequestParam("cid") String cid,@RequestParam("aid") String[] aids){
        boolean flag = clueService.bund(cid,aids);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("success", flag);
        return JsonUtils.getObjJson(map);
    }

    @RequestMapping("/getActivityListByNameAndNotByClueId.do")
    @ResponseBody
    public List<Activity> getActivityListByNameAndNotByClueId(String aname,String clueId){
        Map<String,String> map = new HashMap<String, String>();
        map.put("aname",aname);
        map.put("clueId",clueId);
        List<Activity> aList = activityService.getActivityListByNameAndNotByClueId(map);
        return aList;
    }

    @RequestMapping("/unbund.do")
    @ResponseBody
    public String unbund(String id){
        boolean flag = clueService.unbund(id);

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("success", flag);

        return JsonUtils.getObjJson(map);
    }

    @RequestMapping("/getActivityListByClueId.do")
    @ResponseBody
    public String getActivityListByClueId(String clueId){
        List<Activity> aList = activityService.getActivityListByClueId(clueId);
        return JsonUtils.getObjJson(aList);
    }

    @RequestMapping("/detail.do")
    public String detail(String id, Model model){
        Clue clue = clueService.detail(id);
        model.addAttribute("c", clue);
        return "forward:/workbench/clue/detail.jsp";
    }

    @RequestMapping("/save.do")
    @ResponseBody
    public String save(HttpServletRequest request,Clue clue){
        String id = UUIDUtil.getUUID();
        String createBy = ((User)request.getSession().getAttribute("user")).getName();
        String createTime = DateTimeUtil.getSysTime();
        clue.setId(id);
        clue.setCreateBy(createBy);
        clue.setCreateTime(createTime);

        boolean flag = clueService.save(clue);

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("success", flag);

        return JsonUtils.getObjJson(map);
    }

    @RequestMapping("/getUserList.do")
    @ResponseBody
    public String getUserList(){
        List<User> uList = userService.selectUser();
        return JsonUtils.getObjJson(uList);
    }
}
