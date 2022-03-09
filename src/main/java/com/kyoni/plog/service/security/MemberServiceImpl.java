package com.kyoni.plog.service.security;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kyoni.plog.domain.UserEntity;
import com.kyoni.plog.domain.UserRoleEntity;
import com.kyoni.plog.mapper.UserMapper;
import com.kyoni.plog.util.FileUtil;
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
		UserEntity entity = null;
		try {
			entity = userMapper.getUser(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
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
	
	@Override
	public void updateUserPicture(UserVO vo) {
		userMapper.updateUserPicture(vo);
	}

}
