package com.kyoni.plog.service.security;

import java.util.List;

import com.kyoni.plog.domain.UserEntity;
import com.kyoni.plog.domain.UserRoleEntity;

public interface UserLoginService {
	
	public UserEntity getUser(String email);

	public List<UserRoleEntity> getUserRoles(int memberId);
	
}