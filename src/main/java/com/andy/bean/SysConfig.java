package com.andy.bean;

import lombok.Data;

import javax.persistence.Table;

/**
 * @Classname SysConfig
 * @Description 系统配置表
 * @Date 2019-06-28 10:19
 * @Created by Andy
 */
@Table(name = "system_config")
@Data
public class SysConfig {
    private String id;//主键
    private String paramCode;//参数代码
    private String paramName;//参数名称
    private String paramValue;//参数值
    private String remark;//备注
    private String createUser;//创建人
    private String createTime;//创建时间
}
