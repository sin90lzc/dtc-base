package com.dtc.base.exception;

public enum BaseErrorCode {

	ZOOKEEPER_CREATE_PATH_ERR(1000,"ZOOKEEPER_CREATE_PATH_ERR");
	
	private int code;
	
	private String msg;
	
	private BaseErrorCode(int code,String msg){
		this.code=code;
		this.msg=msg;
	}

	public int code(){
		return this.code;
	}
	
	public String msg(){
		return this.msg;
	}
}
