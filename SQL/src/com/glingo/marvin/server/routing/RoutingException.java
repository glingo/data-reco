package com.glingo.marvin.server.routing;

public class RoutingException extends Exception {

	/** */
	private static final long serialVersionUID = 1L;

	public RoutingException(String message, Object... args) {
		super(String.format(message, args));
	}
}
