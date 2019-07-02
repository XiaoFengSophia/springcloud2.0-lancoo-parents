package com.zxf.api.servcice.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.zxf.api.entity.UserEntity;
import com.zxf.api.feign.IMemberServiceFeign;
import com.zxf.api.service.IOrderService;
import com.zxf.base.BaseApiService;
import com.zxf.base.ResponseBase;
@RestController
public class OrderServiceImpl extends BaseApiService implements IOrderService {
	@Autowired
	private LoadBalancerClient loadBalancerClient;

	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate() {
		
		return new RestTemplate();
	}
	
	@Autowired
    private DiscoveryClient discoveryClient;
	
	//订单服务继承会员服务接口，用来实现feign客户端的调用，减少重复代码接口
	@Autowired
	private IMemberServiceFeign iMemberServiceFeign;
	
	@RequestMapping("/")
	public String Index() {
		
		return "我是订单服务...";
	}
	
	@RequestMapping("/orderToMember")
	public String orderToMember(String name) {
		// TODO Auto-generated method stub
		UserEntity userEntity = iMemberServiceFeign.getMember(name);
		return userEntity==null?"未找到任何信息":userEntity.toString();
	}
	
	 /**
     * 使用负载均衡轮训访问
     * 
     */
    @RequestMapping("/call")
    public Object call() {
    	ServiceInstance serviceInstance = loadBalancerClient.choose("zxf-lancoo-member");
    	RestTemplate restTemplate = getRestTemplate();
        String result = restTemplate.getForObject(serviceInstance.getUri()+"/discover" , String.class);
    	return  result;
    }
	
	//没有解决服务雪崩效应
	@RequestMapping("/orderToMemberUserInfo")
	public ResponseBase orderToMemberUserInfo() {
		// TODO Auto-generated method stub
		return iMemberServiceFeign.getUserInfo();
	}
	
	/**
	 * 解决服务雪崩效应  第一种方式：直接写
	 * fallbackMethod   服务降级执行
	 * hystrix:  
	 * @HystrixCommand 默认开启线程池隔离方式、服务降级、服务熔断
	 * 设置禁止hystrix超时时间  command.default.execution.timeout.enabled: false  业务逻辑能执行，浏览器响应，所以走服务降级
	 * @return
	 */
	@HystrixCommand(fallbackMethod = "orderToMemberUserInfoHystrixFallBack")
	@RequestMapping("/orderToMemberUserInfoHystrix")
	public ResponseBase orderToMemberUserInfoHystrix() {
		// TODO Auto-generated method stub
		System.out.println("orderToMemberUserInfoHystrix："+"线程池名字："+Thread.currentThread().getName());
		return iMemberServiceFeign.getUserInfo();
	}
	//orderToMemberUserInfoHystrixFallBack 服务降级返回方法
	public ResponseBase orderToMemberUserInfoHystrixFallBack() {
		
		return setResultSuccess("返回一个友好的提示：服务降级，服务器忙，请稍后重试！");
	}
	//解决服务雪崩效应  第一种方式：以类的形式
	@RequestMapping("/orderToMemberUserInfoHystrix2")
	public ResponseBase orderToMemberUserInfoHystrix2() {
		// TODO Auto-generated method stub
		System.out.println("orderToMemberUserInfoHystrix："+"线程池名字："+Thread.currentThread().getName());
		return iMemberServiceFeign.getUserInfo();
	}

	@RequestMapping("/orderInfo")
	public ResponseBase orderInfo() {
		// TODO Auto-generated method stub
		System.out.println("orderInfo："+"线程池名字："+Thread.currentThread().getName());
		return setResultSuccess();
	}
	

}
