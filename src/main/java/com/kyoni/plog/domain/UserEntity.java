package com.kyoni.plog.domain;

import com.kyoni.plog.enums.Role;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserEntity {
	private int seq;
	private String email;
	private String username;
	private String pwd;
	private String authorityCode;
	private Role role;
	private String googleSub;

	public UserEntity(String email, String pwd) {
		this.email = email;
		this.pwd = pwd;
	}
	
	@Builder
	public UserEntity(String username, String email, Role role, String googleSub) {
		this.username = username;
		this.email = email;
		this.role = role;
		this.googleSub = googleSub;
	}

}