package com.web.common.cntrl.service;

import java.util.List;

public interface CrudService<T> {
    T findByOne(T t);
    int totalCount(T t);
    List<T> findAll(T t);
    int save(T t);
    int update(T t);
    int delete(T t );
}