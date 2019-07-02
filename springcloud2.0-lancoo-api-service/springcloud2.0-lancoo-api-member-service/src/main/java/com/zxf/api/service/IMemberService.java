package com.zxf.api.service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zxf.api.entity.UserEntity;
import com.zxf.base.ResponseBase;

public interface IMemberService {
	
	@RequestMapping("/getMember")
	public UserEntity getMember(@RequestParam("name") String name);
	
	@RequestMapping("/getUserInfo")
	public ResponseBase getUserInfo();

}
