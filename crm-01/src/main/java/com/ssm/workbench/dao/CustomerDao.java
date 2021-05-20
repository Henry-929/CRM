package com.ssm.workbench.dao;

import com.ssm.workbench.pojo.Customer;

import java.util.List;

public interface CustomerDao {

    Customer getCustomerByName(String company);

    int save(Customer cus);

    List<String> getCustomerName(String name);
}
