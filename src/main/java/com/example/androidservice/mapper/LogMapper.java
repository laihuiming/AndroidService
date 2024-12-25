
package com.example.androidservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.androidservice.entity.SystemLog;
import org.apache.ibatis.annotations.Mapper;

/**
 @author: laihuiming
 @date: 2024/12/25
 @version: 1.0
 @description: 日志数据层
 **/
@Mapper
public interface LogMapper extends BaseMapper<SystemLog> {
}
