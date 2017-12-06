package com.dtc.base.exception;

public class DTCRuntimeException extends RuntimeException{

	private int code;
	
	private String msg;
	
	private Throwable throwable;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1452283940024222510L;

	public DTCRuntimeException(int code,String msg,Throwable throwable){
		super();
		this.code=code;
		this.msg=msg;
		this.throwable=throwable;
	}
	
	public DTCRuntimeException(int code,String msg){
		this(code,msg,null);
	}
	
	@Override
	public String getMessage() {
		return this.toString();
	}
	
	@Override
	public synchronized Throwable getCause() {
		return this.throwable;
	}
	
	@Override
	public String toString() {
		return String.format("[%s]%s", code,msg);
	}
	
	
	
}
