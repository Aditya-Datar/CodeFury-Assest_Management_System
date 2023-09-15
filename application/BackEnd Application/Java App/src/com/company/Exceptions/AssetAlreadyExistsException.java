package com.company.Exceptions;

public class AssetAlreadyExistsException extends Exception {

	public AssetAlreadyExistsException() {
		super();
	}

	public AssetAlreadyExistsException(String message) {
		super(message);
	}

	public AssetAlreadyExistsException(Throwable cause) {
		super(cause);
	}
	
	public AssetAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public AssetAlreadyExistsException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
}
