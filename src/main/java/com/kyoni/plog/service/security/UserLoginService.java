package com.kyoni.plog.service.security;

import java.util.List;
import java.util.Map;

import com.kyoni.plog.domain.UserEntity;
import com.kyoni.plog.domain.UserRoleEntity;

public interface UserLoginService {
	
	public UserEntity getUser(Map<String, String> map);

	public List<UserRoleEntity> getUserRoles(int memberId);
	
}