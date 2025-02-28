package com.project.myTest.service.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.myTest.command.UserVO;

@Service
public class LoginServiceImple implements LoginService{

	@Autowired
	private LoginMapper loginMapper;
	
	// 회원 가입
	@Override
	public int join(UserVO vo) {
		System.out.println("서비스들어옴");
		return loginMapper.join(vo);
	}

	@Override
	public UserVO login(String username) {
		
		return loginMapper.login(username);
	}


}
