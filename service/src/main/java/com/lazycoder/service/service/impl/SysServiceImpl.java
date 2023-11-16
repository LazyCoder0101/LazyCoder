package com.lazycoder.service.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component(value = "SysServiceImpl")
public class SysServiceImpl {

    private Logger log = LoggerFactory.getLogger("系统日志");

    /**
     * 打印系统日志警告
     * @param msg
     */
    public void log_warn(String msg){
        log.warn(msg);
    }

    /**
     * 打印系统错误日志
     * @param msg
     */
    public void log_error(String msg){
        log.error(msg);
    }

}
