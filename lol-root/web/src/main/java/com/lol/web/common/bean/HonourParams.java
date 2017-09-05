package com.lol.web.common.bean;

/**
 * 
 * @author YangLi
 *
 */
public class HonourParams {

	public int statusCode=200;
	public String message;
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public HonourParams(int statusCode, String message) {
		super();
		this.statusCode = statusCode;
		this.message = message;
	}
	public HonourParams() {
		super();
	}
	
	
}
