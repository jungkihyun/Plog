package com.kyoni.plog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kyoni.plog.vo.TestVO;

@Mapper
public interface TestMapper {

	List<TestVO> findAll();
	void addUser(TestVO testVO);
	
}
