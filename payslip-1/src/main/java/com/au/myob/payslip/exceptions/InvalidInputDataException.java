package com.au.myob.payslip.exceptions;

/**
 * Custom exception to be used in the application while reading from csv files
 * @author Silas
 *
 */

public class InvalidInputDataException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidInputDataException(String errorMessage) {
		super(errorMessage);
	}
	
	
}
