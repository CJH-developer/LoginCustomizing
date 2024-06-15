package com.project.myTest.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import com.project.myTest.service.common.LoginMapper;

public class CustomAuthenticationFailure extends SimpleUrlAuthenticationFailureHandler{

	private String redirectURL;
	private static final int lockyn = 5;
	private Map<String, Integer> loginAttemps = new HashMap<>();
	
	@Autowired
	private LoginMapper loginMapper;
	
	// 인증 실패시 실행
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		// 로그인 실패시에 다양한 작업 처리
		
		String username = request.getParameter("username");
		System.out.println("fails : " + username);
		String msg;
		
		if(exception instanceof BadCredentialsException) {
			int attempts = loginAttemps.getOrDefault(username, 0);
			attempts++;
			loginAttemps.put(username, attempts);
			
			if(attempts >= lockyn) {
				lockCnt(username);
				msg = "잠김 계정이오니, 관리자에게 문의바랍니다.";
			}else {
				System.out.println(attempts + " / " + lockyn);
				msg = "아이디와 비밀번호를 확인해주세요. 로그인 시도 : " + attempts + "/ 총" + lockyn;
			}
			
		}else if(exception instanceof InsufficientAuthenticationException) {
			msg = "비밀번호가 틀렸습니다.";
		}
		
		
		response.sendRedirect(redirectURL);
	}
	
	private void lockCnt(String username) {
		loginMapper.lock(username);
		System.out.println("잠긴 계정 : " + username);
	}

	public String getRedirectURL() {
		return redirectURL;
	}

	public void setRedirectURL(String redirectURL) {
		this.redirectURL = redirectURL;
	}
	
}