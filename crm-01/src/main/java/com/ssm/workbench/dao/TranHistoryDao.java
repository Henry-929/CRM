package com.ssm.workbench.dao;

import com.ssm.workbench.pojo.TranHistory;

import java.util.List;

public interface TranHistoryDao {

    int save(TranHistory th);

    List<TranHistory> getHistoryListByTranId(String tranId);
}
