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
	private String nicname;
	private String pwd;
	private String authorityCode;
	private Role role;

	public UserEntity(String email, String pwd) {
		this.email = email;
		this.pwd = pwd;
	}
	
	@Builder
	public UserEntity(String nicname, String email, Role role) {
		this.nicname = nicname;
		this.email = email;
		this.role = role;
	}

}