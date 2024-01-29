package com.web.sample.cntrl.service;

import com.web.common.cntrl.service.CrudService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SampleService extends CrudService {

    public List<HashMap> fileList(HashMap map);
    int deleteFile(HashMap map);
    HashMap fileMap(HashMap map);


}
