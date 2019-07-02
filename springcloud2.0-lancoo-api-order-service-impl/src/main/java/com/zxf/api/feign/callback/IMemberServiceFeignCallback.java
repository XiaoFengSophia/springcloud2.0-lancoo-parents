package com.zxf.api.feign.callback;

import org.springframework.stereotype.Component;

import com.zxf.api.entity.UserEntity;
import com.zxf.api.feign.IMemberServiceFeign;
import com.zxf.base.BaseApiService;
import com.zxf.base.ResponseBase;
@Component
public class IMemberServiceFeignCallback extends BaseApiService implements IMemberServiceFeign {

	@Override
	public UserEntity getMember(String name) {
		// TODO Auto-generated method stub
		return null;
	}
	//服务友好降级提示
	@Override
	public ResponseBase getUserInfo() {
		// TODO Auto-generated method stub
		return setResultError("服务器忙，请稍后重试，来自以类的形式写服务降级！！！");
	}

}
