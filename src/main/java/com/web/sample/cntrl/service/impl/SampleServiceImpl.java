package com.web.sample.cntrl.service.impl;

import com.stn.util.FileUtil;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import com.web.sample.cntrl.mapper.SampleMapper;
import com.web.sample.cntrl.service.SampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("sampleService")
@Primary
public class SampleServiceImpl implements SampleService {

    private static final Logger logger = LoggerFactory.getLogger(SampleServiceImpl.class);
    @Autowired
    private SampleMapper sampleMapper;

    @Autowired
    private FileUtil fileUtil;


    private void fileSave(HashMap map) throws Exception{
        MultipartHttpServletRequest multipartReq = (MultipartHttpServletRequest) map.get("multipartReq");
        List<Map<String,Object>>  fileList = fileUtil.fileUpload(multipartReq,"");
        for(Map<String, Object> m : fileList){
            m.put("boardId",map.get("boardId"));
            sampleMapper.fileSave((HashMap) m);
        }
    }


    @Override
    public HashMap findByOne(HashMap o) {
        return sampleMapper.findByOne(o);
    }

    @Override
    public int totalCount(HashMap o) {
        return sampleMapper.totalCount((HashMap) o);
    }

    @Override
    public List findAll(HashMap o) { return sampleMapper.findAll((HashMap) o);
    }

    @Override
    @Transactional
    public int save(HashMap map) {
        int succ = 0;
        map.put("boardId",sampleMapper.selectMaxBoardId());
        if(sampleMapper.save(map) > 0){
            succ++;
            try {
                fileSave(map);
            }catch (Exception e){
                logger.error("게시글 등록 중 오류 발생: {}", e.getMessage(), e);
                throw new RuntimeException("게시글 등록 중 오류가 발생했습니다.");
            }
        }
        return succ;

    }

    @Override
    public int update(HashMap map) {
        int succ = 0;
        if(sampleMapper.update(map) > 0){
            map.put("boardId",map.get("id"));
            succ++;
            try {
                fileSave(map);
            }catch (Exception e){
                logger.error("게시글 수정 중 오류 발생: {}", e.getMessage(), e);
                throw new RuntimeException("게시글 수정 중 오류가 발생했습니다.");
            }
        }
        return succ;
    }


    @Override
    @Transactional
    public int delete(HashMap map) {
        int succ = 0;
        if(sampleMapper.delete(map) > 0){
            succ++;
            map.put("boardId",map.get("id"));
            map.remove("id");
            HashMap fileMap = sampleMapper.fileMap(map);
            if(fileMap != null){
                if(fileUtil.fileDelete(fileMap.get("FILE_PATH").toString()) == 1) {
                    sampleMapper.deleteFile(map);
                }
            }
        }

        return succ;
    }


    @Override
    public List<HashMap> fileList(HashMap map) {
        return sampleMapper.fileList(map);
    }


    @Override
    public int deleteFile(HashMap map) {
        return sampleMapper.deleteFile(map);
    }

    @Override
    public HashMap fileMap(HashMap map) {
        return sampleMapper.fileMap(map);
    }

}
