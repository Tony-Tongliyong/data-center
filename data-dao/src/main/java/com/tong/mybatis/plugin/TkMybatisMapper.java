package com.tong.mybatis.plugin;


import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface TkMybatisMapper<T> extends Mapper<T>,MySqlMapper<T> {
}
