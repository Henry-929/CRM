package com.ssm.workbench.service;

import com.ssm.utils.DateTimeUtil;
import com.ssm.utils.UUIDUtil;
import com.ssm.workbench.dao.CustomerDao;
import com.ssm.workbench.dao.TranDao;
import com.ssm.workbench.dao.TranHistoryDao;
import com.ssm.workbench.pojo.Customer;
import com.ssm.workbench.pojo.Tran;
import com.ssm.workbench.pojo.TranHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TranServiceImpl implements TranService{

    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private TranDao tranDao;
    @Autowired
    private TranHistoryDao tranHistoryDao;

    @Override
    public boolean save(Tran tran, String customerName) {
        /**
         * 交易添加业务
         *      在做添加之前,参数t里面就少了一项信息,就是客户的主键,customerId
         *
         *      先处理客户相关的需求
         *      (1) 判断customerName,根据客户名称在客户表进行精确查询
         *          如果有这个客户,则取出这个客户的id,封装到t对象中
         *          如果没有这个客户,则在客户表新建一条客户信息,然后将新建的客户的id取出,封装到t对象中
         *
         *      (2) 经过以上操作后,t对象中的信息就全了,需要执行添加交易的操作
         *
         *      (3) 添加交易完毕后,需要创建一条交易历史
         */
        boolean flag = true;
        Customer cus = customerDao.getCustomerByName(customerName);
        if(cus == null){

            cus = new Customer();
            cus.setId(UUIDUtil.getUUID());
            cus.setName(customerName);
            cus.setCreateTime(DateTimeUtil.getSysTime());
            cus.setCreateBy(tran.getCreateBy());
            cus.setContactSummary(tran.getContactSummary());
            cus.setNextContactTime(tran.getNextContactTime());
            cus.setOwner(tran.getOwner());
            // 添加客户
            int count1 = customerDao.save(cus);
            if(count1 != 1){
                flag = false;
            }
        }

        // 通过以上对于客户的处理,不论是查询出来已有的客户,还是以前没有我们新增的客户,总之客户已经有了,客户的id就有了
        // 将客户的id封装到t对象中
        tran.setCustomerId(cus.getId());

        int count2 = tranDao.save(tran);
        if(count2 != 1){

            flag = false;

        }

        // 添加交易历史
        TranHistory th = new TranHistory();
        th.setId(UUIDUtil.getUUID());
        th.setTranId(tran.getId());
        th.setStage(tran.getStage());
        th.setMoney(tran.getMoney());
        th.setExpectedDate(tran.getExpectedDate());
        th.setCreateTime(DateTimeUtil.getSysTime());
        th.setCreateBy(tran.getCreateBy());
        int count3 = tranHistoryDao.save(th);

        if(count3 != 1){

            flag = false;

        }

        return flag;
    }

    @Override
    public Tran detail(String id) {
        Tran t = tranDao.detail(id);
        return t;
    }

    @Override
    public List<TranHistory> getHistoryListByTranId(String tranId) {
        List<TranHistory> ths = tranHistoryDao.getHistoryListByTranId(tranId);
        return ths;
    }

    @Override
    public boolean changeStage(Tran tran) {
        boolean flag = true;

        // 改变交易阶段
        int count1 = tranDao.changeStage(tran);
        if(count1 != 1){

            flag = false;

        }

        // 交易阶段改变后,生成一条交易历史
        TranHistory th = new TranHistory();
        th.setId(UUIDUtil.getUUID());
        th.setCreateBy(tran.getEditBy());
        th.setCreateTime(DateTimeUtil.getSysTime());
        th.setExpectedDate(tran.getExpectedDate());
        th.setMoney(tran.getMoney());
        th.setTranId(tran.getId());
        th.setStage(tran.getStage());
        // 添加交易历史
        int count2 = tranHistoryDao.save(th);
        if(count2 != 1){

            flag = false;

        }

        return flag;
    }

    @Override
    public Map<String, Object> getChars() {
        int total = tranDao.getToal();

        List<Map<String,Object>> dataList = tranDao.getChars();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total",total);
        map.put("dataList",dataList);
        return map;
    }

}
