package com.web.common.contoller.service;

import com.stn.util.CommandMap;

import java.util.HashMap;
import java.util.List;

public interface CrudService<T> {
    T findByOne(CommandMap map);
    List<HashMap<String,Object>> findAll();
    T save(CommandMap map);
    void delete(CommandMap map);
}