package com.ssm.workbench.service;

import com.ssm.vo.PaginationVo;
import com.ssm.workbench.pojo.Clue;
import com.ssm.workbench.pojo.Tran;

import java.util.Map;

public interface ClueService {
    boolean save(Clue clue);

    Clue detail(String id);

    boolean unbund(String id);

    boolean bund(String cid, String[] aids);

    boolean convert(String clueId, Tran tran, String createBy);

    PaginationVo<Clue> pageList(Map<String, Object> map);
}
