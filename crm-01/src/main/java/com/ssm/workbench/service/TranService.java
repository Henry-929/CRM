package com.ssm.workbench.service;

import com.ssm.vo.PaginationVo;
import com.ssm.workbench.pojo.Tran;
import com.ssm.workbench.pojo.TranHistory;

import java.util.List;
import java.util.Map;

public interface TranService {
    boolean save(Tran tran, String customerName);

    Tran detail(String id);

    List<TranHistory> getHistoryListByTranId(String tranId);

    boolean changeStage(Tran tran);

    Map<String, Object> getChars();
}
