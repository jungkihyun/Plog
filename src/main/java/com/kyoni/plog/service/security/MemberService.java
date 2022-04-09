package com.kyoni.plog.service.security;


import java.util.Map;

import org.springframework.stereotype.Service;

import com.kyoni.plog.domain.UserEntity;
import com.kyoni.plog.vo.UserVO;

@Service
public interface MemberService extends UserLoginService {
	
	void addUser(UserVO vo);
	UserVO getUserByEmail(String loginId);
	void updateUserEmail(UserEntity user);
	void updateUserPicture(UserVO vo);
	void updateUsername(UserVO vo);
	Integer pwCheck(UserVO vo);
	void updatePassword(UserVO vo);
	
}