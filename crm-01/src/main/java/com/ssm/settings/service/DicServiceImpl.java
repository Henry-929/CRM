package com.ssm.settings.service;

import com.ssm.settings.dao.DicTypeDao;
import com.ssm.settings.dao.DicValueDao;
import com.ssm.settings.pojo.DicType;
import com.ssm.settings.pojo.DicValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DicServiceImpl implements DicService{

    @Autowired
    private DicTypeDao dicTypeDao;
    @Autowired
    private DicValueDao dicValueDao;

    @Override
    public Map<String, List<DicValue>> getAll(ServletContext application) {
        Map<String,List<DicValue>> map = new HashMap<String, List<DicValue>>();

        // 将字典类型列表取出
        List<DicType> dtList = dicTypeDao.getTypeList();

        // 将字典类型列表遍历
        for(DicType dicType : dtList){

            // 取得每一种类型的字典类型编码
            String code = dicType.getCode();

            // 根据每一个字典类型来取得字典值列表
            List<DicValue> dvList = dicValueDao.getListByCode(code);

            map.put(code+"List",dvList);
        }

        return map;
    }
}
