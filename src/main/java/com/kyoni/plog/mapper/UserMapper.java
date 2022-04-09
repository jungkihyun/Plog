package com.kyoni.plog.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kyoni.plog.domain.UserEntity;
import com.kyoni.plog.domain.UserRoleEntity;
import com.kyoni.plog.vo.UserVO;

@Mapper
public interface UserMapper {
	UserEntity getUser(Map<String, String> map);
	List<UserRoleEntity> getUserRoles(@Param("memberId") int memberId);
	
	void updateUserEmail(UserEntity user);
	
	void addUser(UserVO vo);
	void addAuthority(int seq);
	int getLastIndex();
	void updateUserPicture(UserVO vo);
	void updateUsername(UserVO vo);
	Integer pwCheck(UserVO vo);
	void updatePassword(UserVO vo);
}