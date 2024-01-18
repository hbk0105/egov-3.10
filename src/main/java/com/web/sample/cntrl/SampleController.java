package com.web.sample.cntrl;

import com.stn.util.ExcelDownUtil;
import egovframework.example.sample.service.SampleDefaultVO;
import egovframework.example.sample.service.SampleVO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class SampleController {

    private Logger log = LogManager.getLogger(this.getClass());

    @Autowired
    ExcelDownUtil excelDownUtil;

    /**
     * 샘플 메인 페이지
     * @return String
     * @throws Exception
     * */
    @RequestMapping("/sample/main.do")
    public String main() throws Exception {
        return "/sample/main";
    }

    /**
     * 샘플 로그인 페이지
     * @return String
     * @throws Exception
     * */
    @RequestMapping("/sample/login.do")
    public String login() throws Exception {
        return "/sample/login";
    }


    @GetMapping("/sample/hello")
    public String hello() {
        // 예외 발생 시뮬레이션
        throw new RuntimeException("서버에서 예외가 발생했습니다.");
    }

    @RequestMapping("/sample/excelDwon.do")
    public void down(HttpServletRequest request , HttpServletResponse response) throws Exception {

        // header 순서대로 hashMap서 조회가 되려면 컬럼 별칭(Alias)을 알맞게 줘야함
        // ex) A , B , C , D .. 순서대로 출력 됨
        List<HashMap> dataList = new ArrayList<HashMap>();

        HashMap data = new HashMap();
        data.put("A","홍길동");
        data.put("B","22");
        dataList.add(data);

        HashMap data2 = new HashMap();
        data2.put("A","옥동자");
        data2.put("B","48");
        dataList.add(data2);

        String[] headers = {"이름","나이"};

        String fileNm = "샘플엑셀";

        excelDownUtil.excelDown(headers,dataList,fileNm,response,request);
    }





}
