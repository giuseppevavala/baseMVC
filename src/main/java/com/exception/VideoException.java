package com.exception;

public class VideoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VideoException(String message) {
		super(message);
	}

	public VideoException(Throwable cause) {
		super(cause);
	}

	public VideoException(String message, Throwable cause) {
		super(message, cause);
	}

}
