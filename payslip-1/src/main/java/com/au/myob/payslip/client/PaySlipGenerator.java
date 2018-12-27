package com.au.myob.payslip.client;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.au.myob.payslip.exceptions.InvalidInputDataException;
import com.au.myob.payslip.exceptions.PayslipOutputWriterException;
import com.au.myob.payslip.main.GeneratePaySlip;
import com.au.myob.payslip.util.GeneratePaySlipEnum;
import com.au.myob.payslip.vo.Employee;
import com.au.myob.payslip.vo.Payslip;

/**
 * Client class to test the GeneratePaySlip application.
 * 
 * @author Silas
 *
 */

public class PaySlipGenerator {

	final static Logger logger = Logger.getLogger(PaySlipGenerator.class);
	
	public static void main(String[] args) throws IOException {

		String employeesFileName = null;
		String rulesFileName = null;
		String outputFileName = null;
		
		GeneratePaySlip paySlipGen = new GeneratePaySlip();
		
		if(args != null && args.length == 3) {
			employeesFileName = args[0];
			rulesFileName = args[1];
			outputFileName = args[2];
			
		} else {

			Properties props = new Properties();
			try(InputStream propsStream 
					= Thread.currentThread().getContextClassLoader()
						.getResourceAsStream(GeneratePaySlipEnum.PROPERTY_FILE.getTextValue())) {
			    props.load(propsStream);
			}
			
			employeesFileName = 
					props.getProperty(GeneratePaySlipEnum.EMPLOYEES_CSV_FILE.getTextValue());
			rulesFileName = 
					props.getProperty(GeneratePaySlipEnum.TAX_RULES_CSV_FILE.getTextValue());
			outputFileName = 
					props.getProperty(GeneratePaySlipEnum.PAYSLIP_OUTPUT_CSV_FILE.getTextValue());

		}
			List<Employee> employees = null;
			List<Payslip> payslips = null;
			try {
				employees = paySlipGen.readInputFile(employeesFileName);
				
				payslips = paySlipGen.computeTaxAndCreatePaySlip(employees, rulesFileName);
				
				paySlipGen.printPaySlips(payslips, outputFileName);
	
			} catch (InvalidInputDataException e) {
				logger.error(e.getMessage());
			} catch (PayslipOutputWriterException powe) {
				logger.error(powe.getMessage());
			}
	}

}
