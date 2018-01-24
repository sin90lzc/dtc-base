package com.dtc.base.exception;

public enum BaseErrorCode {

	ZOOKEEPER_CREATE_PATH_ERR(1000,"ZOOKEEPER_CREATE_PATH_ERR"),
	CLASS_FIELD_RESOLVE_ERR(1003,"CLASS_FIELD_RESOLVE_ERR"),
	UNSAFE_ERR(1004,"UNSAFE_ERR"),
	REFLECT_CLASS_NEW_INSTANCE_ERR(1005,"REFLECT_CLASS_NEW_INSTANCE_ERR"),
	REFLECT_METHOD_INVOKE_ERR(1006,"REFLECT_METHOD_INVOKE_ERR")
	;
	
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
