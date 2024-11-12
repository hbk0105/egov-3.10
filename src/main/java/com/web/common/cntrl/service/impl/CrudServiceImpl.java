package com.web.common.cntrl.service.impl;

import com.web.common.cntrl.service.CrudService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("crudService")
public class CrudServiceImpl implements CrudService {

    @Override
    public Object findByOne(Object o) {
        return null;
    }

    @Override
    public int totalCount(Object o) {
        return 0;
    }

    @Override
    public List findAll(Object o) {
        return null;
    }

    @Override
    public int save(Object o) {
        return 0;
    }

    @Override
    public int update(Object o) {
        return 0;
    }

    @Override
    public int delete(Object o) {
        return 0;
    }
}
