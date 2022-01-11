package com.kyoni.plog.service.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kyoni.plog.domain.UserEntity;
import com.kyoni.plog.domain.UserRoleEntity;
import com.kyoni.plog.mapper.UserMapper;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public UserEntity getUser(String id) {
		return userMapper.getUser(id);
	}

	@Override
	public List<UserRoleEntity> getUserRoles(String id) {
		return userMapper.getUserRoles(id);
	}
}