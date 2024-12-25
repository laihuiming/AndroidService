
package com.example.androidservice.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.androidservice.service.ExcelImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

/**
 @author: laihuiming
 @date: 2024/1/25
 @version: 1.0
 @description: excel导入接口实现类
 **/
@Service
public class ExcelImportServiceImpl implements ExcelImportService {

    @Override
    public void checkValue(Object obj, String field, boolean delFlag) {
        try {
            Class<?> aClass = obj.getClass();
            Field field1 = aClass.getDeclaredField(field);
            field1.setAccessible(true);
            Object value = field1.get(obj);

            System.out.println(value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
