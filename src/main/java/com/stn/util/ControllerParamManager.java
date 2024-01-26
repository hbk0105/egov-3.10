package com.stn.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 컨트롤러 검색 유지 파라메터 메니져
 * 
 * @author Administrator
 *
 */
public class ControllerParamManager {

	/**
	 * $구분자 대상 params 변수 input(hidden) tag로 변환
	 * 
	 * @param params
	 * @return
	 */
	public static String getParamHtmlFormConvert(HashMap<String, Object> params) {

		String form = "";

		Set<String> keys = params.keySet();
		for (String key : keys) {
			if (key.contains("$")) {
				if(params.get(key) != null){
					form += "<input type='hidden' name='" + key.replace("$", "") + "' value='" + params.get(key) + "' />\n";
				}
			}
		}
		return form;
	}

}
