package com.project.myTest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.myTest.command.UserVO;
import com.project.myTest.service.common.LoginService;


@Controller
public class LoginController {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private LoginService loginService;
	
	@GetMapping("/main")
	public String main(@RequestParam(value="err", required=false) String err, Model model) {
		
		if(err != null ) {
			model.addAttribute("msg", "아이디 또는 비밀번호를 확인하세요.");
		}
		
		return "main";
	}	
	
	
	@GetMapping("/join")
	public String join() {
		return "join/join";
	}
	
	@PostMapping("/join/joinForm")
	public String joinForm(UserVO vo) {
		System.out.println("joinForm 들어옴");
		String pw = bCryptPasswordEncoder.encode(vo.getPassword());
		vo.setPassword(pw);
		
		loginService.join(vo);
		System.out.println("joinForm 실행");
		return "redirect:/main";
	}
	
	@GetMapping("/user/mypage")
	public @ResponseBody String userMyPage() {
		return "유저 페이지";
	}
	
	@GetMapping("/com/mypage")
	public @ResponseBody String comMyPage() {
		return "com 페이지";
	}
	
	@GetMapping("/test")
	public String test() {
		return "test";
	}
}
