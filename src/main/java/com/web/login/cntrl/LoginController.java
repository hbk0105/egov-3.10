package com.web.login.cntrl;

import com.stn.util.CommandMap;
import com.web.user.vo.user.UserDetailsVO;
import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

//import com.nhncorp.lucy.security.xss.XssPreventer;

@Controller
public class LoginController {
	
	Logger log = Logger.getLogger(this.getClass());
	// https://debugdaldal.tistory.com/89?category=925275
	
    // 사용자가 입력한 정보로부터 POST 요청은 Spring Security를 거친 후 해당 메서드로 들어온다.
	@RequestMapping("/customLogin.do")
    public String login(@RequestParam(value="error", required=false) String error, 
                        @RequestParam(value="logout", required=false) String logout, 
                        Model model , CommandMap commandMap) {
    	
		// https://to-dy.tistory.com/60
		if(commandMap.isEmpty() == false){
            Iterator<Entry<String,Object>> iterator = commandMap.getMap().entrySet().iterator();
            Entry<String,Object> entry = null;
            while(iterator.hasNext()){
                entry = iterator.next();
                System.out.println("key : "+entry.getKey()+", value : "+entry.getValue());
            }
        }

        if(error != null) {
            model.addAttribute("errorMsg", "Invalid username and password");
        }
        if(logout != null) {
            model.addAttribute("logoutMsg", "You have been logged out successfully");
        }
        return "/egovframework/example/login/login"; // login.jsp(Custom Login Page)
    }    
	
	

	@RequestMapping(value = "/logout.do", method = RequestMethod.GET)
	public String loout(HttpServletRequest request, HttpServletResponse response) throws Exception { 
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){ 
			//HttpSession session = request.getSession();
			//session.invalidate();
			new SecurityContextLogoutHandler().logout(request, response, auth); 
		} 
		return "redirect:/customLogin.do";
	} 

	@GetMapping("/member/member.do")
	public void doMember(Authentication authentication, Model model , CommandMap commandMap) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsVO vo = (UserDetailsVO) auth.getPrincipal();
		log.debug("auth -- > " + vo.toString());
		log.info("logined member");

		try {
			if (authentication != null) {
				System.out.println("타입정보 : " + authentication.getClass());

				// 세션 정보 객체 반환
				WebAuthenticationDetails web = (WebAuthenticationDetails)authentication.getDetails();
				System.out.println("세션ID : " + web.getSessionId());
				System.out.println("접속IP : " + web.getRemoteAddress());

				// UsernamePasswordAuthenticationToken에 넣었던 UserDetails 객체 반환
				UserDetails userVO = (UserDetails) authentication.getPrincipal();
				System.out.println("ID정보 : " + userVO.getUsername());
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		model.addAttribute("vo",vo);

	}
	
	@GetMapping("/admin/test.do")
	public void doAdmin() {
		log.info("admin only");
	}
	/*
	@RequestMapping(value = "/test.do", method = RequestMethod.GET)
	public void errorTest(HttpServletRequest request, Model model) throws Exception {
        throw new Exception("test.do에서 에러발생");    
    }
*/

	
	@RequestMapping("/accessError.do")
	public String accessDenied(Authentication auth, HttpServletRequest request, Model model) {
		log.info("access Denied : "+ auth);
		log.info("access Denied : "+ request.getAttribute("errMsg"));
		model.addAttribute("msg", "Access Denied");
		
		return "/egovframework/example/login/accessError";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/updateUser.do", method = {RequestMethod.GET, RequestMethod.POST})
	//@RequestBody를 적어야 Map으로 값을 받아올 수 있다.
	public @ResponseBody String  updateUser( @RequestParam Map<String, Object> map){
		log.info( map );
		System.out.println("zzzz " + map.get("data"));
		// 구글의 json paser 라이브러리
		/*JSONObject obj = JsonUtil.getJsonStringFromMap(map);
		System.out.println(obj);
		System.out.println(obj.get("data"));*/
		
		/*ObjectMapper mapper = new ObjectMapper();
		Map<String, String> m;
		try {
			m = mapper.readValue(map.get("data").toString(), Map.class);
			System.out.println("####  m :: " + m);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return map.get("data").toString();
	}
	
	// https://hailey0.tistory.com/39
	public static void main(String[] args) {
		
		/*String dirty = "\"><script>alert('xss');</script>"; 
		String clean = XssPreventer.escape(dirty); // => "&quot;&gt;&lt;script&gt;alert(&#39xss&#39);&lt;/script&gt;"
		System.out.println("#### clean :: " + clean);
		dirty = XssPreventer.unescape(clean); // => "\"><script>alert('xss');</script>" }
		System.out.println("### dirty :: " + dirty);
		*/
	}


}
