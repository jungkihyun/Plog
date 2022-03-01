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
	private String oauthKey;
	private String picture;
	
	public UserVO() {
		
	}
	
	public UserVO(String email, String username, String pwd, String oauthKey, String picture) {
		this.email = email;
		this.username = username;
		this.pwd = pwd;
		this.oauthKey = oauthKey;
		this.picture = picture;
	}

}
