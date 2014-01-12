package com.mt4agents.exceptions;

public class RestAgentControllerException extends Exception {

	private static final long serialVersionUID = -1956801675009036127L;

	public RestAgentControllerException(String message) {
		super(message);
	}

	public RestAgentControllerException(Throwable t) {
		super(t);
	}

	public RestAgentControllerException(String message, Throwable t) {
		super(message, t);
	}

}
