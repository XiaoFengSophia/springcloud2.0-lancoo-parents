package com.zxf.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.zxf.api.entity.UserEntity;
import com.zxf.api.service.IMemberService;
import com.zxf.base.BaseApiService;
import com.zxf.base.ResponseBase;
@RestController
public class MemberServiceImpl extends BaseApiService implements IMemberService {
	@Autowired
	private LoadBalancerClient loadBalancerClient;
	
//	@Autowired
//	RestTemplate restTemplate;
	@RequestMapping("/")
	public String index() {
		
		return "我是会员服务...";
	}

	@RequestMapping("/getMember")
	public UserEntity getMember(@RequestParam("name")String name) {
		// TODO Auto-generated method stub
		UserEntity userEntity = new UserEntity();
		userEntity.setName(name);
		userEntity.setAge(20);
		return userEntity;
	}

	@RequestMapping("/getUserInfo")
	public ResponseBase getUserInfo() {
		// TODO Auto-generated method stub
		try {
			//会员服务产生1500毫秒的等待
			Thread.sleep(1500);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return setResultSuccess("订单服务接口调用会员服务接口成功...");
	}
	 /**
     * 使用负载均衡轮训访问
     * 
     */
    @RequestMapping("/call")
    public Object call() {
    	ServiceInstance serviceInstance = loadBalancerClient.choose("zxf-eureka-server");
        System.out.println("服务地址：" + serviceInstance.getUri());
        System.out.println("服务名称：" + serviceInstance.getServiceId());
        String result = new RestTemplate().getForObject(serviceInstance.getUri() , String.class);
        System.out.println(result);
    	return  result;
    }
    @RequestMapping("/discover")
    public String discover() {
//    	ServiceInstance serviceInstance = loadBalancerClient.choose("zxf-eureka-server");
//        System.out.println("服务地址：" + serviceInstance.getUri());
//        System.out.println("服务名称：" + serviceInstance.getServiceId());
//        String result = restTemplate.getForObject(serviceInstance.getUri() , String.class);
//        System.out.println(result);
    	return  "this is discover-----1";
    }


}
