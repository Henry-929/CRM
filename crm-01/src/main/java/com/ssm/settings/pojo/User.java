package com.ssm.settings.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
   private String id;//用户编号 主键
   private String loginAct;//登陆账号
   private String name;//用户真实姓名
   private String loginPwd;//登陆密码
   private String email;//邮箱
   private String expireTime;//失效时间
   private String lockState;//锁定状态 0：锁定 1：启用
   private String deptno;//部门编号
   private String allowIps;//允许访问的IP地址
   private String createTime;//创建时间
   private String createBy;//创建人
   private String editTime;//修改时间
   private String editBy;//修改人
}