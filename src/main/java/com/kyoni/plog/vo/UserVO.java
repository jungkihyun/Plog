package com.kyoni.plog.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserVO {
	
	private int seq;
	private String email;
	private String nicname;
	private String pwd;
	
	public UserVO() {
		
	}
	
	public UserVO(String email, String nicname, String pwd) {
		this.email = email;
		this.nicname = nicname;
		this.pwd = pwd;
	}

}
