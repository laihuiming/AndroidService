
package com.example.androidservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.androidservice.entity.SystemLog;
import com.example.androidservice.mapper.LogMapper;
import com.example.androidservice.service.LogService;
import org.springframework.stereotype.Service;

/**
 @author: laihuiming
 @date: 2024/12/25
 @version: 1.0
 @description: 日志接口实现
 **/
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, SystemLog> implements LogService {
}
