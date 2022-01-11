package com.kyoni.plog.service.security;

import java.util.List;

import com.kyoni.plog.domain.UserEntity;
import com.kyoni.plog.domain.UserRoleEntity;

public interface UserLoginService {
	public UserEntity getUser(String id);

	public List<UserRoleEntity> getUserRoles(String id);
}