package com.example.restaurant.exception;

/**
 * Service exception used for internal errors.
 *
 */
public class ServiceException extends Exception {

	/** */
	private static final long serialVersionUID = 1L;

	public ServiceException(String errorMessage) {
        super(errorMessage);
    }
	
	public ServiceException(Throwable cause) {
		super(cause);
	}
	
	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public ServiceException(Exception exception) {
		super(exception);
	}

}
