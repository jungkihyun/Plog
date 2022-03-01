package com.kyoni.plog.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserVO {
	
	private int seq;
	private String email;
	private String username;
	private String pwd;
	private String googleSub;
	
	public UserVO() {
		
	}
	
	public UserVO(String email, String username, String pwd, String googleSub) {
		this.email = email;
		this.username = username;
		this.pwd = pwd;
		this.googleSub = googleSub;
	}

}
