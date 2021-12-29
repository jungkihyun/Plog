package com.kyoni.plog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kyoni.plog.mapper.TestMapper;
import com.kyoni.plog.vo.TestVO;

@Service
public class TestService {
	
	@Autowired
	private TestMapper testMapper;
	
	public void insert(TestVO testVO) {
		testMapper.addUser(testVO);
	}

}
