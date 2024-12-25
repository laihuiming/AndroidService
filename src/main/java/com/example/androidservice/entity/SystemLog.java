
package com.example.androidservice.entity;

import lombok.Data;

/**
 @author: laihuiming
 @date: 2024/12/24
 @version: 1.0
 @description: 系统操作日志
 **/
@Data
public class SystemLog {
    private String id;
    private Long userId;
    private String operationType;
}
