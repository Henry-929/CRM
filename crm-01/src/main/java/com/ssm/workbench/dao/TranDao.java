package com.ssm.workbench.dao;

import com.ssm.workbench.pojo.Tran;

import java.util.List;
import java.util.Map;

public interface TranDao {

    int save(Tran tran);

    Tran detail(String id);

    int changeStage(Tran tran);

    int getToal();

    List<Map<String, Object>> getChars();
}
