package com.andy.dao.mapper;

import com.andy.bean.User;
import com.andy.dao.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by admin on 2019-01-11.
 */
@Repository
public interface UserMapper extends BaseMapper<User>{


}