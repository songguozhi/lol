package com.lol.web.common.enums;

/**
 * 返回状态
 * @author Yangli
 *
 */
public enum StatusCode {

	SUCCESS(200),
	ERROR(300),
	TIME_OUT(301);
	private final int code;

	private StatusCode(int code) {
		this.code = code;
	}
	
	public int getCode(){
		return code;
	}
}
