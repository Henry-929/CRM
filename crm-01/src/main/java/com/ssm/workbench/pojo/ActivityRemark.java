package com.ssm.workbench.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityRemark {
    private String id;
    private String noteContent;//备注信息
    private String createTime;
    private String createBy;
    private String editTime;
    private String editBy;
    private String editFlag;//是否修改过的标记
    private String activityId;
}
