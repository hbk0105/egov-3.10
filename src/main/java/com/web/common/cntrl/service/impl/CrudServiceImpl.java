package com.web.common.cntrl.service.impl;

import com.stn.util.CommandMap;
import com.web.common.cntrl.service.CrudService;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;

@Service("crudService")
public class CrudServiceImpl implements CrudService {
    @Override
    public HashMap findByOne(HashMap map) {
        return null;
    }

    @Override
    public int totalCount(HashMap map) {
        return 0;
    }

    @Override
    public List<HashMap<String, Object>> findAll(HashMap map) {
        return null;
    }

    @Override
    public int save(HashMap map) {
        return 0;
    }

    @Override
    public int update(HashMap map) {
        return 0;
    }
    @Override
    public int delete(HashMap map) {
        return 0;
    }
}
