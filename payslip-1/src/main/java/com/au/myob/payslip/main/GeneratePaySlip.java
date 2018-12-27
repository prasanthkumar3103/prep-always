package com.au.myob.payslip.main;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.log4j.Logger;

import com.au.myob.payslip.exceptions.InvalidInputDataException;
import com.au.myob.payslip.exceptions.PayslipOutputWriterException;
import com.au.myob.payslip.util.GeneratePaySlipEnum;
import com.au.myob.payslip.vo.Employee;
import com.au.myob.payslip.vo.Payslip;
import com.au.myob.payslip.vo.Rule;

/**
 * The main stub with implementation for the following.
 * 
 * 1. Reading the list of employees from the csv file
 * 2. Creating the rules from the taxcomputation rules csv file
 * 3. Creating the list of payslips for the employees based on the taxation rules
 * 4. Creating the output csv file with the list of payslips
 * 
 * @author Silas
 *
 */

public class GeneratePaySlip implements IGeneratePaySlip
{
	
	final static Logger logger = Logger.getLogger(GeneratePaySlip.class);
	
	private List<Employee> employees;
	private List<Rule> rules;
	private List<Payslip> payslips;
	
	
	/**
	 * Method to read the csv file and create a list of Employee objects 
	 * 
	 * @param employeeFileName - name of the csv file with the list of employee details
	 * @return list of Employees.
	 */
    public List<Employee> readInputFile(String employeeFileName) throws InvalidInputDataException {
		try (Stream<String> lines = Files.lines(Paths.get(employeeFileName))) {
			
			Employee employee;
			employees = new ArrayList<Employee>();
			List<List<String>> values = lines.map(line -> Arrays.asList(line.split(","))).collect(Collectors.toList());
			values.remove(0);
			
			for(List<String> list : values) {
				employee = new Employee();
				employee.setFirstName(list.get(0));
				employee.setLastName(list.get(1));
				employee.setAnnaulSalary(Integer.parseInt(list.get(2)));
				employee.setSuperRate(list.get(3));
				employee.setStateDate(list.get(4));
				employees.add(employee);
			}
			
		} catch (NumberFormatException nfe) {
			throw new InvalidInputDataException(GeneratePaySlipEnum.READ_EMPLOYEE_INPUTFILE_ERROR_MESSAGE
					.getTextValue());
		} catch (IOException e) {
			throw new InvalidInputDataException(GeneratePaySlipEnum.READ_EMPLOYEE_INPUTFILE_ERROR_MESSAGE
					.getTextValue());
		}
		
		return employees;
    }
    
	/**
	 * Method to calculate tax and create payslip by applying the tax rules (from taxcalculation.csv)
	 * on the employee data 
	 * 
	 * @param listOfEmployees
	 * @param - name of the csv file with the tax rules
	 * @return list of Payslips.
	 */
    public List<Payslip> computeTaxAndCreatePaySlip(List<Employee> employees, String _rulesFileName) throws InvalidInputDataException {
    	
    	Rule rule;
    	Payslip payslip;
    	payslips = new ArrayList<Payslip>();
		rules = new ArrayList<Rule>();
		try (Stream<String> rulesLines = Files.lines(Paths.get(_rulesFileName))) {
			
			List<List<String>> rulesList = rulesLines.map(ruleLine 
					-> Arrays.asList(ruleLine.split(
							GeneratePaySlipEnum.COMMA_DELIMITER.getTextValue())))
					.collect(Collectors.toList());
			rulesList.remove(0);
			
			for(List<String> list : rulesList) {
				rule = new Rule();
				rule.setMinimum_salary(Integer.parseInt(list.get(0)));
				rule.setMaximum_salary(Integer.parseInt((list.get(1).equals(GeneratePaySlipEnum.UNDEFINED.getTextValue()))?new Integer(Integer.MAX_VALUE).toString():list.get(1)));
				rule.setFixed_tax(Integer.parseInt(list.get(2)));
				rule.setVariable_tax(Float.parseFloat(list.get(3)));
				rules.add(rule);
			}

			for(Employee employee : employees) {
				
				   Predicate<Rule> rulePredicate = 
						   predicateCandidate -> (employee.getAnnaulSalary() >= predicateCandidate.getMinimum_salary() 
						   	&& employee.getAnnaulSalary() < predicateCandidate.getMaximum_salary());
				   Rule matchingRule = rules.stream().filter(rulePredicate).findFirst().get();
				   
				   payslip = new Payslip();
				   int incomeTax = 0;
				   payslip.setFullName(employee.getFirstName() + 
						   GeneratePaySlipEnum.SPACE.getTextValue() + employee.getLastName());
				   payslip.setPayPeriod(employee.getStateDate());
				   payslip.setGrossIncome(employee.getAnnaulSalary() / 
						   GeneratePaySlipEnum.MONTHS.getIntValue());
				   
				   incomeTax = 
						   (int) ((matchingRule.getFixed_tax() + (employee.getAnnaulSalary() - 
								   matchingRule.getMinimum_salary()) * 
								   (matchingRule.getVariable_tax() / 
										   GeneratePaySlipEnum.PERCENTILE.getIntValue())) / 
								   GeneratePaySlipEnum.MONTHS.getIntValue());
				   payslip.setIncomeTax(incomeTax);
				   payslip.setNetIncome(payslip.getGrossIncome() - incomeTax);
				   payslip.setSprAnnuation((int) (payslip.getGrossIncome() * 
						   (Float.parseFloat(employee.getSuperRate())/
								   GeneratePaySlipEnum.PERCENTILE.getIntValue())));
				   payslips.add(payslip);
				   
			}
		} catch (NumberFormatException nfe) {
			throw new InvalidInputDataException(GeneratePaySlipEnum.COMPUTE_TAX_ERROR_MESSAGE
					.getTextValue());
			
		} catch (IOException e) {
			throw new InvalidInputDataException(GeneratePaySlipEnum.COMPUTE_TAX_ERROR_MESSAGE.getTextValue());
		}
		
		return payslips;
		
    }

	/**
	 * Method to calculate tax and create payslip by applying the tax rules (from taxcalculation.csv)
	 * on the employee data 
	 * 
	 * @param listOfPayslips
	 * @param - name of the csv file to which the payslips are to be written
	 */
    public void printPaySlips(List<Payslip> payslips, String outputFileName) 
    		throws PayslipOutputWriterException {
    	
    	FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(outputFileName);
            fileWriter.append(GeneratePaySlipEnum.PAYSLIP_FILE_HEADER.getTextValue().toString());
            fileWriter.append(GeneratePaySlipEnum.NEW_LINE_SEPARATOR.getTextValue());
            
        	for(Payslip paySlip : payslips) {
        		fileWriter.append(paySlip.getFullName());
                fileWriter.append(GeneratePaySlipEnum.COMMA_DELIMITER.getTextValue());
        		fileWriter.append(paySlip.getPayPeriod());
                fileWriter.append(GeneratePaySlipEnum.COMMA_DELIMITER.getTextValue());
        		fileWriter.append(new Integer(paySlip.getGrossIncome()).toString());
                fileWriter.append(GeneratePaySlipEnum.COMMA_DELIMITER.getTextValue());
        		fileWriter.append(new Integer(paySlip.getIncomeTax()).toString());
                fileWriter.append(GeneratePaySlipEnum.COMMA_DELIMITER.getTextValue());
        		fileWriter.append(new Double(paySlip.getNetIncome()).toString());
                fileWriter.append(GeneratePaySlipEnum.COMMA_DELIMITER.getTextValue());
        		fileWriter.append(new Integer(paySlip.getSprAnnuation()).toString());
                fileWriter.append(GeneratePaySlipEnum.NEW_LINE_SEPARATOR.getTextValue());
        	}

            logger.info(GeneratePaySlipEnum.PAYSLIP_CSV_SUCCESS_MESSAGE.getTextValue());
        } catch (Exception e) {
            throw new PayslipOutputWriterException(GeneratePaySlipEnum.PAYSLIP_CSV_ERROR_MESSAGE.getTextValue());
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (Exception e) {
                throw new PayslipOutputWriterException(GeneratePaySlipEnum.PAYSLIP_CSV_FINALIZE_ERROR_MESSAGE.getTextValue());
            }
        }
    }
    
}