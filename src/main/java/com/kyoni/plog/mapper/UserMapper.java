package com.kyoni.plog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kyoni.plog.domain.UserEntity;
import com.kyoni.plog.domain.UserRoleEntity;
import com.kyoni.plog.vo.UserVO;

@Mapper
public interface UserMapper {
	UserEntity getUser(@Param("email") String email);

	List<UserRoleEntity> getUserRoles(@Param("memberId") int memberId);
	
	void addUser(UserVO vo);
	void addAuthority(int seq);
	int getLastIndex();
}