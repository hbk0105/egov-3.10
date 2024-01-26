package com.web.sample.cntrl.service.impl;

import com.stn.util.CommandMap;
import com.web.common.cntrl.service.CrudService;
import com.web.sample.cntrl.mapper.SampleMapper;
import com.web.sample.cntrl.service.SampleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("sampleService")
public class SampleServiceImpl implements SampleService {

    @Resource(name="sampleMapper")
    private SampleMapper sampleMapper;

    @Override
    public HashMap findByOne(HashMap map) {
        return sampleMapper.findByOne(map);
    }
    @Override
    public List<HashMap> fileList(HashMap map) {
        return sampleMapper.fileList(map);
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
        // 롤백 테스트 필수
        //map.put("boardId2",sampleMapper.selectMaxBoardId());
         map.put("boardId",sampleMapper.selectMaxBoardId());
        return sampleMapper.save(map);
    }


    @Override
    public int fileSave(List<Map<String,Object>> fileList,HashMap map) {

        int su = 0;
        for(Map<String, Object> m : fileList){
            m.put("boardId",map.get("boardId"));
            sampleMapper.fileSave((HashMap) m);
            su++;
        }
        if(su == 0){
            throw new RuntimeException("게시글 등록 중 오류가 발생했습니다.");
        }
        return su;
        // boardId
    }

    @Override
    public int update(HashMap map) {
        return 0;
    }

    @Override
    public int delete(HashMap map) {
        return 0;
    }


    @Override
    public int deleteFile(List<Map<String,Object>> fileList,HashMap map) {
        return 0;
    }
}
