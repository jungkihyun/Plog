package com.kyoni.plog.service.security;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kyoni.plog.domain.UserEntity;
import com.kyoni.plog.domain.UserRoleEntity;
import com.kyoni.plog.mapper.UserMapper;
import com.kyoni.plog.vo.UserVO;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private UserMapper userMapper;
	
	@Override
	@Transactional(readOnly = false)
	public void addUser(UserVO vo) {
		userMapper.addUser(vo);
		userMapper.addAuthority(userMapper.getLastIndex());
	}

	@Override
	public UserEntity getUser(Map<String, String> map) {
		return userMapper.getUser(map);
	}

	@Override
	public List<UserRoleEntity> getUserRoles(int memberId) {
		return userMapper.getUserRoles(memberId);
	}

	@Override
	public UserVO getUserByEmail(String loginId) {
		return null;
	}
	
	@Override
	public void updateUserEmail(UserEntity user) {
		userMapper.updateUserEmail(user);
	}

}
