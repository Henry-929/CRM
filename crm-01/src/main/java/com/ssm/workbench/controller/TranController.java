package com.ssm.workbench.controller;

import com.ssm.settings.pojo.User;
import com.ssm.settings.service.UserService;
import com.ssm.utils.DateTimeUtil;
import com.ssm.utils.JsonUtils;
import com.ssm.utils.UUIDUtil;
import com.ssm.vo.PaginationVo;
import com.ssm.workbench.pojo.Tran;
import com.ssm.workbench.pojo.TranHistory;
import com.ssm.workbench.service.CustomerService;
import com.ssm.workbench.service.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/workbench/transaction")
public class TranController {

    @Autowired
    private UserService userService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private TranService tranService;

    @RequestMapping("/getChars.do")
    @ResponseBody
    public String getChars(){
        Map<String,Object> map = tranService.getChars();
        return JsonUtils.getObjJson(map);
    }

    @RequestMapping("/changeStage.do")
    @ResponseBody
    public String changeStage(Tran tran,HttpServletRequest request){
        String editTime = DateTimeUtil.getSysTime();
        String editBy = ((User)request.getSession().getAttribute("user")).getName();

        tran.setEditTime(editTime);
        tran.setEditBy(editBy);

        String mJson = (String) request.getSession().getServletContext().getAttribute("mJson");
        Map<String,String> pMap = (Map<String, String>) JsonUtils.getMapObj(mJson, Map.class);
        tran.setPossibility(pMap.get(tran.getStage()));

        boolean flag = tranService.changeStage(tran);

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("success", flag);
        map.put("t",tran);

        return JsonUtils.getObjJson(map);
    }

    @RequestMapping("/getHistoryListByTranId.do")
    @ResponseBody
    public String getHistoryListByTranId(String tranId,HttpServletRequest request){
        List<TranHistory> ths = tranService.getHistoryListByTranId(tranId);

        String mJson = (String) request.getSession().getServletContext().getAttribute("mJson");
        Map<String,String> pMap = (Map<String, String>) JsonUtils.getMapObj(mJson, Map.class);

        // 将交易历史列表遍历
        for(TranHistory th : ths){
            // 根据每条交易历史,取出每一个阶段
            String stage = th.getStage();
            String possibility = pMap.get(stage);
            th.setPossibility(possibility);
        }

        return JsonUtils.getObjJson(ths);
    }

    @RequestMapping("/detail.do")
    public String detail(String id, Model model,HttpServletRequest request){
        Tran t = tranService.detail(id);

        // 处理可能性
        /**
         * 阶段t
         * 阶段和可能性之间的对应关系 pMap
         */
        String stage = t.getStage();
        String mJson = (String) request.getSession().getServletContext().getAttribute("mJson");

        Map<String,String> pMap = (Map<String, String>) JsonUtils.getMapObj(mJson, Map.class);
        String possibility = pMap.get(stage);
        t.setPossibility(possibility);

        model.addAttribute("t",t);
        return "forward:/workbench/transaction/detail.jsp";
    }

    @RequestMapping("/save.do")
    public String save(Tran tran, String customerName, HttpServletRequest request){
        String id = UUIDUtil.getUUID();
        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User)request.getSession().getAttribute("user")).getName();

        tran.setId(id);
        tran.setCreateTime(createTime);
        tran.setCreateBy(createBy);

        boolean flag = tranService.save(tran,customerName);
        String strPath = "";
        if (flag){
            // 如果添加交易成功,跳转到列表页
            strPath = "redirect:/workbench/transaction/index.jsp";
        }

        return strPath;
    }

    @RequestMapping("/getCustomerName.do")
    @ResponseBody
    public String getCustomerName(String name){
        List<String> sList = customerService.getCustomerName(name);
        return JsonUtils.getObjJson(sList);
    }

    @RequestMapping("/add.do")
    public String add(Model model){
        List<User> uList = userService.selectUser();
        model.addAttribute("uList",uList);
        return "forward:/workbench/transaction/save.jsp";
    }
}
