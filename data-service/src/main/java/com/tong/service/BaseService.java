package com.tong.service;

import tk.mybatis.mapper.entity.Condition;

import java.util.List;

public interface BaseService<T> {

    T selectById(Object key);

    int save(T entity);

    int insertNotNull(T entity);

    int delete(Object key);

    int update(T entity);

    int updateNotNull(T entity);

    List<T> selectByExample(Object example);

    Condition createCondition();
}
