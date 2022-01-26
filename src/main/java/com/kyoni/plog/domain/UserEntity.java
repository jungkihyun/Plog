package com.kyoni.plog.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserEntity {
	private int seq;
	private String email;
	private String nicname;
	private String pwd;

	public UserEntity(String email, String pwd) {
		this.email = email;
		this.pwd = pwd;
	}

}