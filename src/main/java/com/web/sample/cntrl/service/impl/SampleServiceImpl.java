package com.web.sample.cntrl.service.impl;

import com.stn.util.CommandMap;
import com.web.common.cntrl.service.CrudService;
import com.web.sample.cntrl.mapper.SampleMapper;
import com.web.sample.cntrl.service.SampleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service("sampleService")
public class SampleServiceImpl implements SampleService {

    @Resource(name="sampleMapper")
    private SampleMapper sampleMapper;

    @Override
    public HashMap findByOne(HashMap map) {
        return sampleMapper.findByOne(map);
    }

    @Override
    public int totalCount(HashMap map) {
        return sampleMapper.totalCount(map);
    }

    @Override
    public List<HashMap<String, Object>> findAll(HashMap map) {
        return sampleMapper.findAll(map);
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
