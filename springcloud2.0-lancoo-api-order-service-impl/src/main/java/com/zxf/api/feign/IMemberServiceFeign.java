package com.zxf.api.feign;

import org.springframework.cloud.openfeign.FeignClient;

import com.zxf.api.feign.callback.IMemberServiceFeignCallback;
import com.zxf.api.service.IMemberService;
/**
 * 继承的作用解决代码重复
 * 订单服务继承会员服务接口，实现feign客户端的调用
 * @author lancoo
 *
 */
@FeignClient(value = "zxf-lancoo-member",fallback = IMemberServiceFeignCallback.class)
public interface IMemberServiceFeign extends IMemberService {
	
	

}
