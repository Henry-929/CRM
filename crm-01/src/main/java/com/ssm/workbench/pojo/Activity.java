package com.ssm.workbench.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Activity {
    private String id;//主键
    private String owner;//所有者 外键 关联tb_user
    private String name;//市场活动名称
    private String startDate;//开始日期 年月日
    private String endDate;//结束日期
    private String cost;//成本
    private String description;//描述
    private String createTime;//创建时间 年月日时分秒
    private String createBy;//创建人
    private String editTime;//修改时间
    private String editBy;//修改人
}
