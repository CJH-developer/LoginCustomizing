package com.project.myTest.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.myTest.command.UserVO;
import com.project.myTest.service.common.LoginMapper;

@Service
public class MyUserDetailsService implements UserDetailsService{

	@Autowired
	private LoginMapper loginMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		System.out.println("사용자의 로그인 시도 : " + username);
		
		UserVO vo = loginMapper.login(username);
		
		if(vo != null) {
			return new MyUserDetails(vo);
		}
		
		return null;
	}
	
	
	
}
