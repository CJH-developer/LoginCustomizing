package com.project.myTest.service.common;

import com.project.myTest.command.UserVO;

public interface LoginService {

	// 회원가입
	public int join(UserVO vo);
	// 로그인
	UserVO login(String username); 

}
