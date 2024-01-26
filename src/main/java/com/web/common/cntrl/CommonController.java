package com.web.common.cntrl;

import com.stn.util.CommandMap;
import com.stn.util.ControllerParamManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

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

}