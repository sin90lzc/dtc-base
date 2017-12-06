package com.dtc.base.zk.exception;

public enum ZookeeperErrorCode {
	ZOOKEEPER_CREATE_PATH_ERR(1001,"ZOOKEEPER_CREATE_PATH_ERR"),
	ZOOKEEPER_GETCHILDREN_ERR(1002,"ZOOKEEPER_GETCHILDREN_ERR"),
	ZOOKEEPER_GETDATA_ERR(1003,"ZOOKEEPER_GETDATA_ERR"),
	ZOOKEEPER_SETDATA_ERR(1004,"ZOOKEEPER_SETDATA_ERR"),
	ZOOKEEPER_CHECKEXISTS_ERR(1006,"ZOOKEEPER_CHECKEXISTS_ERR"),
	ZOOKEEPER_INIT_ERR(1007,"ZOOKEEPER_INIT_ERR")
	;
	
	private int code;
	
	private String msg;
	
	private ZookeeperErrorCode(int code,String msg){
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
