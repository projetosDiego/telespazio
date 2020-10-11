package com.wyspace.satelite.exception;

public class BandwithLimitExceededException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BandwithLimitExceededException(String message) {
		super(message);
	}

}
