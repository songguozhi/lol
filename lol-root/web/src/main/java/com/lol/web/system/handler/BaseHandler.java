package com.lol.web.system.handler;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.lol.web.common.bean.HonourParams;
import com.lol.web.common.enums.StatusCode;

public class BaseHandler {

	@ExceptionHandler({Exception.class})
	@ResponseBody
	public String recordError(Exception e){
		e.printStackTrace();
		return JSON.toJSONString(new HonourParams(StatusCode.ERROR.getCode(), "操作失败"));
	}
	
	public HonourParams getSuccess(String message){
		return new HonourParams(StatusCode.SUCCESS.getCode(), message);
	}
	
}
