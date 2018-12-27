package com.au.myob.payslip.exceptions;

/**
 * Custom exception to be used in the application while writing the output
 * @author Silas
 *
 */

public class PayslipOutputWriterException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PayslipOutputWriterException(String errorMessage) {
		super(errorMessage);
	}
	
	
}
