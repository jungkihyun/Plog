package com.kyoni.plog.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserEntity {
	private String id;
	private String password;
	private String username;

	public UserEntity(String id, String password) {
		this.id = id;
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserEntity [id=" + id + ", password=" + password + "]";
	}
}