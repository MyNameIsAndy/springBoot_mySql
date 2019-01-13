package com.andy.dao.base;


import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * Created by admin on 2019-01-11.
 */
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T>,PayUpdateMapper<T>{
}
