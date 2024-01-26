package com.web.sample.cntrl;

import com.stn.util.CommandMap;
import com.stn.util.ExcelDownUtil;
import com.stn.util.MailUtil;
import com.stn.util.PageUtil;
import com.web.sample.cntrl.service.SampleService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SampleController {

    private Logger log = LogManager.getLogger(this.getClass());

    @Autowired
    ExcelDownUtil excelDownUtil;


    @Autowired
    MailUtil mailUtil;

    @Autowired
    JavaMailSender javaMailSender;

    @Resource(name = "sampleService")
    private SampleService sampleService;

    /**
     * 샘플 메인 페이지
     * @return String
     * @throws Exception
     * */
    @RequestMapping("/sample/main.do")
    public String main( ModelMap model , HttpServletRequest req)  {

        HashMap map = new HashMap();
        map.put("id",1);

        try{
            model.addAttribute("board",sampleService.findByOne(map));

        }catch (Exception e){
            e.printStackTrace();
        }

        return "/sample/main";
    }

    /**
     * 샘플 로그인 페이지
     * @return String
     * @throws Exception
     * */
    @RequestMapping("/sample/login.do")
    public String login( ModelMap model , HttpServletRequest req) throws Exception {
        // mailUtil.signCertificationMail("aceCarWash","에이스카","michael@sangs.co.kr","마이클" ,(long)1 , javaMailSender);
        return "/sample/login";
    }

    /**
     * 게시판 리스트
     * @return String
     * @throws Exception
     * */
    @RequestMapping("/sample/board.do")
    public String board(CommandMap commandMap , ModelMap model , HttpServletRequest req ) {
        HashMap param = (HashMap) commandMap.getMap();
        long pageIndex = commandMap.getMap().get("pageIndex") == null ? 1 : Long.parseLong(String.valueOf(commandMap.getMap().get("pageIndex")));

        int totCnt = 0;
        try {

            totCnt = sampleService.totalCount(param) ;

            /* MICHAEL CUSTOM PAGING INFO */
            PageUtil pageUtil = new PageUtil(totCnt, 10, pageIndex, req);
            model.addAttribute("paper", pageUtil.pager());
            model.addAttribute("pageUtil", pageUtil);
            /* MICHAEL CUSTOM PAGING INFO */

            param.put("startPage",pageUtil.getMysqlStartNumber());
            param.put("pageSize",pageUtil.getPageSize());

            model.addAttribute("resultList", sampleService.findAll(param));
            model.addAttribute("paramMap",param);
        }catch (Exception e){
            e.printStackTrace();
        }

        return "/sample/board";
    }

    @GetMapping("/sample/hello")
    public String hello() throws Exception{
        // 예외 발생 시뮬레이션
        try{
            throw new RuntimeException("서버에서 예외가 발생했습니다.");
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";

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
