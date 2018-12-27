package com.au.myob.payslip.util;

public enum GeneratePaySlipEnum {

	COMMA_DELIMITER(","),
	NEW_LINE_SEPARATOR("\n"),
	PROPERTY_FILE("payslip.properties"),
	EMPLOYEES_CSV_FILE("EMPLOYEES_CSV_FILE"),
	TAX_RULES_CSV_FILE("TAX_RULES_CSV_FILE"),
	PAYSLIP_OUTPUT_CSV_FILE("PAYSLIP_OUTPUT_CSV_FILE"),
     
	PAYSLIP_FILE_HEADER("name,pay period,gross income,income tax,net income,super"),
	SPACE(" "),
	UNDEFINED(""),
	PAYSLIP_CSV_SUCCESS_MESSAGE("Payslip CSV File created successfully."),
	PAYSLIP_CSV_ERROR_MESSAGE("Error while creating payslip CSV file."),
	PAYSLIP_CSV_FINALIZE_ERROR_MESSAGE("Error while finalizing the resources after "
			+ "creating payslip CSV file."),
	READ_EMPLOYEE_INPUTFILE_ERROR_MESSAGE("Error while reading Employee details from CSV file. "
			+ "\nPlease ensure the following:\n"
			+ "\n1. Input files(employees.csv & taxrules.csv) and the jar file are in the path where this command is being executed.\n"
			+ "2. The values in the CSV file are valid"),
	COMPUTE_TAX_ERROR_MESSAGE("Error while computing the tax (or) while creating the "
			+ "payslip objects after computing the tax"),
	
	MONTHS(12),
	PERCENTILE(100);


	    private String textValue;
	    private int intValue;
	    
	    GeneratePaySlipEnum(int intValue) {
	    	this.intValue = intValue;
	    }
	    	
	    GeneratePaySlipEnum(String textValue) {
	        this.textValue = textValue;
	    }

	    public String getTextValue() {
	        return textValue;
	    }
	    
	    public int getIntValue() {
	    	return intValue;
	    }
}
