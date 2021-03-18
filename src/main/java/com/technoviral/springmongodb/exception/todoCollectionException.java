package com.technoviral.springmongodb.exception;

public class todoCollectionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public todoCollectionException(String message) {
		super(message);
	}
	
	public static String NotFoundException(String id) {
		return "Todo with id :"+id+ "not found!";
	}

	public static String AlreadyExists() {
		return "To do with given name already exists";
	}

}
