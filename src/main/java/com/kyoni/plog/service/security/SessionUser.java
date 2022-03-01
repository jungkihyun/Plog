package com.kyoni.plog.service.security;

import lombok.Getter;

import java.io.Serializable;

import com.kyoni.plog.domain.UserEntity;

/*
 * 세션에 저장하려면 직렬화를 해야 하는데
 * User 엔티티는 추후 변경사항이 있을 수 있기 때문에
 * 직렬화를 하기 위한 별도의 SessionUser 클래스 생성
 */
@Getter
public class SessionUser implements Serializable {
	private String nicname, email, picture;

	public SessionUser(UserEntity user) {
		this.nicname = user.getNicname();
		this.email = user.getEmail();
	}
}