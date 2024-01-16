/*
package com.web.user.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.web.user.vo.user.UserDetailsVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserAuthenticationService implements UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(UserAuthenticationService.class);

	@Autowired
	BCryptPasswordEncoder pwEncoding;

	@SuppressWarnings("unused")
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		System.out.println("### username :: " + username);
		
		
		Map<String, Object> user = new HashMap<String,Object>();
		user.put("username", "michael");
		String dbpw = pwEncoding.encode("1234");
		user.put("password", dbpw);
		user.put("authority", "ROLE_USER");
		
		UserDetailsVO vo = new UserDetailsVO();
		if(user == null ) throw new UsernameNotFoundException(username);
		logger.info(user.toString());
		List<GrantedAuthority> gas = new ArrayList<GrantedAuthority>();
		
		vo.setUsername("michael");
		vo.setPassword(dbpw);
		
		List<String> roles = new ArrayList<>();
		roles.add("ROLE_USER");

		// 사용자 권한 select해서 받아온 List<String> 객체 주입
		vo.setAuthorities(roles);
		
		return vo;
	}

}
*/
