package com.web.sample.cntrl.service.impl;

import com.stn.util.FileUtil;
import com.web.sample.cntrl.mapper.SampleMapper;
import com.web.sample.cntrl.service.SampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("sampleService")
public class SampleServiceImpl implements SampleService {

    @Resource(name="sampleMapper")
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
    public Object findByOne(Object o) {
        return sampleMapper.findByOne((HashMap) o);
    }

    @Override
    public int totalCount(Object o) {
        return sampleMapper.totalCount((HashMap) o);
    }

    @Override
    public List findAll(Object o) { return sampleMapper.findAll((HashMap) o);
    }

    @Override
    @Transactional
    public int save(Object o) {
        HashMap map = (HashMap) o;
        int succ = 0;
        map.put("boardId",sampleMapper.selectMaxBoardId());
        if(sampleMapper.save(map) > 0){
            succ++;
            try {
                fileSave(map);
            }catch (Exception e){
                e.printStackTrace();
                throw new RuntimeException("게시글 등록 중 오류가 발생했습니다.");
            }
        }
        return succ;

    }

    @Override
    public int update(Object o) {
        HashMap map = (HashMap) o;
        int succ = 0;
        if(sampleMapper.update(map) > 0){
            succ++;
            try {
                fileSave(map);
            }catch (Exception e){
                e.printStackTrace();
                throw new RuntimeException("게시글 수정 중 오류가 발생했습니다.");
            }
        }
        return succ;
    }


    @Override
    @Transactional
    public int delete(Object o) {
        HashMap map = (HashMap) o;
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
