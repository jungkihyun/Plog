package com.kyoni.plog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kyoni.plog.domain.UserEntity;
import com.kyoni.plog.domain.UserRoleEntity;

@Mapper
public interface UserMapper {
	UserEntity getUser(@Param("id") String id);

	List<UserRoleEntity> getUserRoles(@Param("id") String id);
}