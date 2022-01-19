package com.kyoni.plog.service.security;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kyoni.plog.domain.UserEntity;
import com.kyoni.plog.domain.UserRoleEntity;
import com.kyoni.plog.vo.UserVO;

@Service
public interface MemberService extends UserLoginService {
	
	public default boolean register(UserVO userVO) {
		System.out.println(userVO.getEmail());
		return true;
	}
	
	public default boolean registerInputVerify(UserVO userVO) {
		return true;
	}

}