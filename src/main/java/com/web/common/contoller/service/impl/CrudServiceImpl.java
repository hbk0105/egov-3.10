package com.web.common.contoller.service.impl;

import com.stn.util.CommandMap;
import com.web.common.contoller.service.CrudService;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;

@Service("crudService")
public class CrudServiceImpl implements CrudService {
    @Override
    public Object findByOne(CommandMap map) {
        return null;
    }

    @Override
    public List<HashMap<String, Object>> findAll() {
        return null;
    }

    @Override
    public Object save(CommandMap map) {
        return null;
    }

    @Override
    public void delete(CommandMap map) {

    }
}
