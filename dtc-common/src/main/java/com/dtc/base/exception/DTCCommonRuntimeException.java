package com.dtc.base.exception;

public class DTCCommonRuntimeException extends DTCRuntimeException{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4881462592485956998L;
	public DTCCommonRuntimeException(BaseErrorCode errorCode) {
		super(errorCode.code(), errorCode.msg());
	}
	public DTCCommonRuntimeException(BaseErrorCode errorCode,Throwable t) {
		super(errorCode.code(),errorCode.msg(),t);
	}
}
