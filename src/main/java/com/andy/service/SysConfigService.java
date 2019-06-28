package com.andy.service;

import com.andy.bean.SysConfig;

import java.util.List;

/**
 * @Classname SysConfigService
 * @Description 获取系统参数
 * @Date 2019-06-28 10:26
 * @Created by Andy
 */
public interface SysConfigService {
    /**
     * 根据代码获取参数
     * @param paramCode
     * @return
     */
    String getSysConfig(String paramCode);
}
