package com.web.common.cntrl.service;

import com.stn.util.CommandMap;

import java.util.HashMap;
import java.util.List;

public interface CrudService<T> {
    HashMap findByOne(HashMap map);
    int totalCount(HashMap map);
    List<HashMap<String,Object>> findAll(HashMap map);
    int save(HashMap map);
    int update(HashMap map);
    int delete(HashMap map);
}