package com.web.sample.cntrl;

import com.stn.util.*;
import com.web.sample.cntrl.service.SampleService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import java.io.File;
import java.util.*;

@Controller
public class SampleController {

    private Logger log = LogManager.getLogger(this.getClass());

    @Autowired
    ExcelDownUtil excelDownUtil;


    @Autowired
    MailUtil mailUtil;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    private FileUtil fileUtil;

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
    @RequestMapping("/sample/boardList.do")
    public String board(CommandMap commandMap , ModelMap model , HttpServletRequest req ) {
        HashMap param = (HashMap) commandMap.getMap();

        /*
        name이 a1로 여러개 들어올 때 출력 방법
        System.out.println("## param --> " + Arrays.deepToString((Object[]) param.get("a1")));
         */
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

        return "/sample/boardList";
    }

    /**
     * 게시글 상세
     * @return String
     * @throws Exception
     * */
    @RequestMapping("/sample/boardRead.do")
    public String read(CommandMap commandMap , ModelMap model , HttpServletRequest req ) {
        HashMap param = (HashMap) commandMap.getMap();
        model.addAttribute("result", sampleService.findByOne(param));
        param.put("boardId",param.get("id").toString());
        model.addAttribute("fileList", sampleService.fileList(param));
        model.addAttribute("paramMap",param);
        return "/sample/boardRead";
    }


    /**
     * 게시글 작성
     * @return String
     * @throws Exception
     * */
    @RequestMapping("/sample/boardWrite.do")
    public String boardWrite(CommandMap commandMap , ModelMap model , HttpServletRequest req ) {
        HashMap param = (HashMap) commandMap.getMap();

        String id = param.get("id") == null ? "" : param.get("id").toString();
        String mode = id == null ? "C" : "U";

        // 수정 상태일 경우만 DB 실행
        if("U".equalsIgnoreCase(mode)){
            model.addAttribute("result", sampleService.findByOne(param));
            param.put("boardId",param.get("id").toString());
            model.addAttribute("fileList", sampleService.fileList(param));
        }

        model.addAttribute("paramMap",param);
        return "/sample/boardWrite";
    }

    /**
     * 게시글 등록/수정
     * @return String
     * @throws Exception
     * */
    @RequestMapping("/sample/boardExec.do")
    public String boardExec(CommandMap commandMap , MultipartHttpServletRequest multipartReq ,  ModelMap model , HttpServletRequest req ) {
        HashMap param = (HashMap) commandMap.getMap();
        String msg = "등록 되었습니다.";

        String id = param.get("id") == null ? "" : param.get("id").toString();
        String mode = id == null || "".equalsIgnoreCase(id) ? "C" : "U";
        try {

            param.put("content",StringUtil.unscript2(StringUtil.unscript(param.get("content").toString())));

            param.put("multipartReq",multipartReq);
            if("C".equalsIgnoreCase(mode)){ // 등록
                sampleService.save(param);
            }else if("U".equalsIgnoreCase(mode)){ // 수정
                msg = "수정 되었습니다.";
                sampleService.update(param);
            }
        }catch (Exception o_O){
            o_O.printStackTrace();
            msg = "게시글 저장 중 오류가 발생했습니다.";
        }

        req.setAttribute("msg",msg);
        req.setAttribute("returnUrl","/sample/boardList.do");
        return "forward:/commonMsgForward.do";
       // return "/sample/boardList";
    }


    /**
     * 파일 삭제
     * @param req
     * @return String
     * @throws Exception
     */
    @RequestMapping(value = "/samepl/ajaxFileDelete.do")
    public String ajaxFileDelete(CommandMap commandMap , HttpServletRequest req , Model model){
        HashMap param = (HashMap) commandMap.getMap();

        String resultCode = "SUCCESS";
        HashMap fileMap = sampleService.fileMap(param);
        if(fileMap == null){
            resultCode = "FILE NOT FOUND";
        }else{
            if(fileUtil.fileDelete(fileMap.get("FILE_PATH").toString()) == 1) {
                sampleService.deleteFile(param);
            }else{
                resultCode = "FILE DELETE ERROR";
            }
        }
        model.addAttribute("msg",resultCode);
        return "jsonView";
    }


    /**
     * 게시글 삭제
     * @param req
     * @return String
     * @throws Exception
     */
    @RequestMapping(value = "/samepl/ajaxBoardDelete.do")
    public String ajaxBoardDelete(CommandMap commandMap , HttpServletRequest req , Model model){
        HashMap param = (HashMap) commandMap.getMap();
        String msg = "FAIL";
        if(sampleService.delete(param) > 0){
            msg = "SUCCESS";
        }
        model.addAttribute("msg",msg);
        return "jsonView";
    }



    /**
     * 파일 조회
     * @param req
     * @return ResponseEntity<byte[]>
     * @throws Exception
     */
    @RequestMapping("/samepl/fileDownLoad.do")
    @Consumes(MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<byte[]> downloadDocument(CommandMap commandMap , HttpServletRequest req , HttpServletResponse response) throws Exception {
        HashMap param = (HashMap) commandMap.getMap();
        HashMap fileMap = sampleService.fileMap(param);
        if(fileMap == null){
            new PrintWiterUtil().cmmnMsg2(response,"첨부파일이 없습니다.");
        }else{
            return  fileUtil.fileDownload( fileMap.get("ORIGINAL_FILE_NAME").toString() , new File(fileMap.get("FILE_PATH").toString()) , req);
        }
        return null;
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
