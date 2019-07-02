package com.zxf.api.service;

import org.springframework.web.bind.annotation.RequestMapping;

import com.zxf.base.ResponseBase;

public interface IOrderService {
	
	//订单服务调用会员服务接口信息 feign
	@RequestMapping("/orderToMember")
	public String orderToMember(String name);
	
	//订单服务调用会员服务接口
	@RequestMapping("/orderToMemberUserInfo")
	public ResponseBase orderToMemberUserInfo();
	
	//订单服务调用会员服务接口
	@RequestMapping("/orderInfo")
	public ResponseBase orderInfo();
	

}
