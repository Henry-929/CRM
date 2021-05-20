package com.ssm.settings.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DicValue {
    private String id;
    private String value;
    private String text;
    private String orderNo;
    private String typeCode;
}
