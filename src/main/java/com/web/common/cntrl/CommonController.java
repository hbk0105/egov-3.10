package com.web.common.cntrl;

import com.stn.util.CommandMap;
import com.stn.util.ControllerParamManager;
import com.stn.util.FileUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.System.out;

/**
 *
 * Description : 관리 공통 관련 컨트롤러
 *
 * Modification Information
 * 수정일			 수정자						수정내용
 * -----------	-----------------------------  -------
 * 2021. 04. 13.	 Sangs					 	최초작성
 *
 */
@Controller
public class CommonController {

    private Logger log = LogManager.getLogger(this.getClass());


	/**
	 * 메시지를 alert 뿌리고 url에 해당하는 페이지로 이동한다.
	 * @param request
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/commonMsgForward.do")
	public String msgForward(HttpServletRequest request, CommandMap commandMap) throws Exception {
		String paramInputs = "";
		try {
				paramInputs = ControllerParamManager.getParamHtmlFormConvert((HashMap<String, Object>) commandMap.getMap());
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getStackTrace()[0].getClassName() + ":" +  e.getStackTrace()[0].getMethodName() + " ERROR : " + e.getMessage());
		}

		request.setAttribute("paramInputs", paramInputs);
		request.setAttribute("resultCode", request.getAttribute("resultCode"));
		request.setAttribute("returnUrl", request.getAttribute("returnUrl"));
		request.setAttribute("msg", request.getAttribute("msg"));

		return "cmmn/msgForward";
	}

	@Value("${Globals.file.uploadDir}")
	private String uploadDir;

	@Value("${Globals.file.smartEditor}")
	private String smartEditor;

	@Autowired
	private FileUtil fileUtil;

	/**
	 * 스마트에디터 이미지 드랍
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/commomnMultiImageUpload.do")
	public String  commomnMultiImageUpload(MultipartHttpServletRequest multipartReq,  Model model ,
			HttpServletRequest request , HttpServletResponse response){

		String msg = "SUCCESS";
		try {

			List<Map<String,Object>> list = fileUtil.fileUpload(multipartReq,"smartEditor");
			out.println("### list --> " + list);


			for(Map<String,Object> f : list){

				String fname = (String) f.get("fileName");

				System.out.println("### fname --> " + fname);
				String fileNm = fname.substring(0,fname.lastIndexOf("."));
				System.out.println("### fileNm --> " + fileNm);

				String ext = fname.substring(fname.lastIndexOf(".")+1,fname.length());
				System.out.println("#### ext " + ext);

				String reqImgUrl = "/commonImg/thumNail/"+fileNm+"/"+ext+".do";
				f.put("bNewLine","true");
				f.put("sFileName",f.get("origFilename"));
				f.put("sFileURL",reqImgUrl);
			}

			model.addAttribute("fileList",list);

		} catch (Exception e) {
			e.printStackTrace();
			msg = e.getMessage();
		}
		model.addAttribute("result",msg);
		return "jsonView";
	}


	@GetMapping("/commonImg/thumNail/{fnm}/{ext}.do")
	public String thumNail(@PathVariable(required = false) String fnm
			,@PathVariable(required = false) String ext, HttpServletRequest req , HttpServletResponse res ) {
		String rootPath = "";
		String fileSeCode = (req.getParameter("fileSeCode") == null || "".equals(req.getParameter("fileSeCode"))) ? "E" : req.getParameter("fileSeCode");
		FileInputStream in = null;
		try {

			if (fileSeCode != null && "E".equals(fileSeCode)) {
				rootPath = uploadDir + File.separator + smartEditor ;
			} else {
				rootPath = uploadDir ;
			}
			out.println("## rootPath --> " + rootPath);

			String srcFileName = URLDecoder.decode(fnm, "UTF-8");
			log.info("filename : " + srcFileName);
			File file = new File(rootPath + File.separator + srcFileName+"."+ext);
			log.info("file : " + file);
			if(file.isFile()) {
				in = new FileInputStream(file);
				int length = (int)file.length();

				int bufferSize = 1024;
				byte[] buffer = new byte[bufferSize];

				MimetypesFileTypeMap fileTypeMap = new MimetypesFileTypeMap();
				String mimeType = fileTypeMap.getContentType(file.getName());
				System.out.println("### mimeType :: " + mimeType);

				ServletOutputStream stream = res.getOutputStream();
				res.setContentType(mimeType);
				res.setHeader("Content-Disposition","");

				while ((length = in.read(buffer)) != -1) {
					stream.write(buffer, 0, length);
				}
				stream.flush();
				stream.close();
			} else {
				log.error("IMG FILE NOT FOUND");
				log.error(rootPath+" 파일이 없습니다.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}finally {
			try {
				if(in != null)
					in.close();
			} catch (Exception e){ log.error("Exception", e); }
		}
		return "cmmn/file";
	}

}