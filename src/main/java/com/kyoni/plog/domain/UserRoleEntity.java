package com.kyoni.plog.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRoleEntity {
	private int seq;
	private String email;
	private String roleName;

	public UserRoleEntity(String email, String roleName) {
		this.email = email;
		this.roleName = roleName;
	}

}