package com.project.myTest.service.common;

import org.apache.ibatis.annotations.Mapper;

import com.project.myTest.command.UserVO;

@Mapper
public interface LoginMapper {

	public int join(UserVO vo);
	UserVO login(String username);
	
	public int lock(String username);
}
