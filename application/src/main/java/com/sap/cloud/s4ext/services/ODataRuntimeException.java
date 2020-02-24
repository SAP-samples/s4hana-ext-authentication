package com.sap.cloud.s4ext.services;

public class ODataRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1535637397711003878L;

	public ODataRuntimeException() {
		super();
	}

	public ODataRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public ODataRuntimeException(String message) {
		super(message);
	}

	public ODataRuntimeException(Throwable cause) {
		super(cause);
	}

}
