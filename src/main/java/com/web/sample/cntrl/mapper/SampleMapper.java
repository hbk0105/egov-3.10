package com.web.sample.cntrl.mapper;

import com.stn.util.CommandMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper("sampleMapper")
public interface SampleMapper {

    HashMap findByOne(HashMap map);

    int totalCount(HashMap map);
    List<HashMap<String, Object>> findAll(HashMap map);
    int save(HashMap map);

    int update(HashMap map);

    int delete(HashMap map);
}
