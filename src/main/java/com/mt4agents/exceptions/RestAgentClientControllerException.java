package com.mt4agents.exceptions;

public class RestAgentClientControllerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5502083183632104623L;

	public RestAgentClientControllerException(String message) {
		super(message);
	}

	public RestAgentClientControllerException(Throwable t) {
		super(t);
	}

	public RestAgentClientControllerException(String message, Throwable t) {
		super(message, t);
	}

}
