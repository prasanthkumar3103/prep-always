package com.au.myob.payslip.main;

import java.util.List;

import com.au.myob.payslip.exceptions.InvalidInputDataException;
import com.au.myob.payslip.exceptions.PayslipOutputWriterException;
import com.au.myob.payslip.vo.Employee;
import com.au.myob.payslip.vo.Payslip;

public interface IGeneratePaySlip {
	
    public List<Employee> readInputFile(String employeeFileName) throws InvalidInputDataException;
    
    public List<Payslip> computeTaxAndCreatePaySlip(List<Employee> employees, 
    		String _rulesFileName) throws InvalidInputDataException;
    
    public void printPaySlips(List<Payslip> payslips, String outputFileName) 
    		throws PayslipOutputWriterException;

}
