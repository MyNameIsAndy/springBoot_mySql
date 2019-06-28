package com.andy.service.impl;

import com.andy.bean.SysConfig;
import com.andy.common.Constant;
import com.andy.dao.mapper.SysConfigMapper;
import com.andy.service.SysConfigService;
import com.andy.util.StringUtil;
import com.andy.util.redisUtil.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Classname SysConfigServiceImpl
 * @Description TODO
 * @Date 2019-06-28 10:35
 * @Created by Andy
 */
@Service
@Slf4j
public class SysConfigServiceImpl implements SysConfigService {
    @Autowired
    private SysConfigMapper sysConfigMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Override
    public String getSysConfig(String paramCode) {
        boolean b = redisUtil.existKey(Constant.PUB_SYSTEM_CONFIG);
        if(!b){
            List<SysConfig> sysConfigs = sysConfigMapper.selectAll();
            for(SysConfig sysConfig : sysConfigs){
                redisUtil.addMap(Constant.PUB_SYSTEM_CONFIG,sysConfig.getParamCode(),sysConfig.getParamValue());
            }
        }
        return redisUtil.getMapField(Constant.PUB_SYSTEM_CONFIG,paramCode);
    }
}
