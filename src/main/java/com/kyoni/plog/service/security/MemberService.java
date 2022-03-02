package com.kyoni.plog.service.security;


import org.springframework.stereotype.Service;

import com.kyoni.plog.domain.UserEntity;
import com.kyoni.plog.vo.UserVO;

@Service
public interface MemberService extends UserLoginService {
	
	void addUser(UserVO vo);
	UserVO getUserByEmail(String loginId);
	void updateUserEmail(UserEntity user);
	
}