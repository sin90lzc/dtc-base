package com.dtc.base.zk.exception;

import com.dtc.base.exception.DTCRuntimeException;

public class DTCZookeeperException extends DTCRuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4993348079642094364L;

	public DTCZookeeperException(ZookeeperErrorCode errorCode,Throwable t){
		super(errorCode.code(), errorCode.msg(), t);
	}
	
	
}
