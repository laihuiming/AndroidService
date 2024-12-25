
package com.example.androidservice.service;

/**
 @author: laihuiming
 @date: 2024/1/25
 @version: 1.0
 @description: excel导入服务类
 **/
public interface ExcelImportService {
    //导入校验方法
    void checkValue(Object obj,String field, boolean delFlag);
}
