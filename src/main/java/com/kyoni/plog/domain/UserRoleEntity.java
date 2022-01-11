package com.kyoni.plog.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRoleEntity {
	private String id;
	private String roleName;

	public UserRoleEntity(String id, String roleName) {
		this.id = id;
		this.roleName = roleName;
	}

}