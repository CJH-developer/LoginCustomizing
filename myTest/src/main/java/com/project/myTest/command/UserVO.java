package com.project.myTest.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserVO {

	private int id;
	private String username;
	private String password;
	private String name;
	private String role;
}
