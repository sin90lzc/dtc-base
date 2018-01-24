package com.dtc.base.exception;

import java.util.Optional;

public class DTCRuntimeException extends RuntimeException{

	private int code;
	
	private String msg;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1452283940024222510L;

	public DTCRuntimeException() {}
	
	public DTCRuntimeException(int code,String msg,Throwable throwable){
		super(throwable);
		this.code=code;
		this.msg=msg;
	}
	
	public DTCRuntimeException(int code,String msg){
		this(code,msg,null);
	}
	
	@Override
	public String getMessage() {
		return this.toString();
	}
	
	@Override
	public String toString() {
		return String.format("[%s]%s%s", code,msg,Optional.ofNullable(getCause()).map(x->{
			return this.getCause().toString();
		}).orElse(""));
	}
	
	
	
}
