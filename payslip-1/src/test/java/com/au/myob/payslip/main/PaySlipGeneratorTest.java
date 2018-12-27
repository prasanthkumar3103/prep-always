package com.au.myob.payslip.main;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import static org.mockito.Mockito.*;

import com.au.myob.payslip.client.PaySlipGenerator;
import com.au.myob.payslip.exceptions.InvalidInputDataException;
import com.au.myob.payslip.exceptions.PayslipOutputWriterException;
import com.au.myob.payslip.vo.Employee;
import com.au.myob.payslip.vo.Payslip;

/**
 * Unit test for simple GeneratePaySlip.
 */
public class PaySlipGeneratorTest 
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     * @throws InvalidInputDataException 
     */
	@Test
    public void testCreateEmployeeObject_validInputCSVFile() throws InvalidInputDataException
    {
		String fileName = "input_employees.csv";
    	GeneratePaySlip generatePaySlip = new GeneratePaySlip();
    	List<Employee> employees = generatePaySlip.readInputFile(fileName);
        
    	Assert.assertNotNull(employees);
    }

    /**
     * Test method with invalid employee input file
     *
     * @param testName name of the test case
     * @throws InvalidInputDataException 
     */
	@Test(expected = InvalidInputDataException.class)
    public void testCreateEmployeeObject_inValidInputCSVFile() throws InvalidInputDataException
    {
		String fileName = "invalid_input_employees.csv";
    	GeneratePaySlip generatePaySlip = new GeneratePaySlip();
    	List<Employee> employees = generatePaySlip.readInputFile(fileName);
        
    	Assert.assertNotNull(employees);
    }
	
    /**
     * Test method with non-existing employee input file
     *
     * @param testName name of the test case
     * @throws InvalidInputDataException 
     */
	@Test(expected = InvalidInputDataException.class)
    public void testCreateEmployeeObject_nonExistingInputCSVFile() throws InvalidInputDataException
    {
		String fileName = "non_existing_employees.csv";
    	GeneratePaySlip generatePaySlip = new GeneratePaySlip();
    	List<Employee> employees = generatePaySlip.readInputFile(fileName);
        
    	Assert.assertNotNull(employees);
    }

	@Test
	public void testToHandleNumberFormatException() throws Exception {
		GeneratePaySlip mockGeneratePaySlip = mock(GeneratePaySlip.class);
		String fileName = "input_employees.csv";
		when(mockGeneratePaySlip.readInputFile(fileName)).thenThrow(new NumberFormatException());
	
	}
	
	@Test
    public void testComputeTax_validInputEmployeeAndValidTaxRules() throws InvalidInputDataException {
		String fileName = "input_employees.csv";
    	GeneratePaySlip generatePaySlip = new GeneratePaySlip();
    	List<Employee> employees = generatePaySlip.readInputFile(fileName);

    	String rulesFile = "taxcalculation.csv";
    	List<Payslip> payslips = generatePaySlip.computeTaxAndCreatePaySlip(employees, rulesFile);
    	
    	Assert.assertNotNull(payslips);
    }

	@Test(expected = InvalidInputDataException.class)
    public void testComputeTax_validInputEmployeeAndInValidTaxRules() throws InvalidInputDataException {
		String fileName = "input_employees.csv";
    	GeneratePaySlip generatePaySlip = new GeneratePaySlip();
    	List<Employee> employees = generatePaySlip.readInputFile(fileName);

    	String rulesFile = "invalid_taxcalculation.csv";
    	List<Payslip> payslips = generatePaySlip.computeTaxAndCreatePaySlip(employees, rulesFile);
    	
    	Assert.assertNotNull(payslips);
    }

	@Test(expected = InvalidInputDataException.class)
    public void testComputeTax_nonExistingInputEmployeeAndValidTaxRules() throws InvalidInputDataException {
		String fileName = "input_employees.csv";
    	GeneratePaySlip generatePaySlip = new GeneratePaySlip();
    	List<Employee> employees = generatePaySlip.readInputFile(fileName);

    	String rulesFile = "non_existing.csv";
    	List<Payslip> payslips = generatePaySlip.computeTaxAndCreatePaySlip(employees, rulesFile);
    	
    	Assert.assertNotNull(payslips);
    }
	
	@Test
    public void testPrintPaySlips() throws PayslipOutputWriterException {
    	GeneratePaySlip generatePaySlip = new GeneratePaySlip();
    	List<Payslip> payslips = new ArrayList<Payslip>();
    	String outputFileName = "payslip_output.csv";

    	generatePaySlip.printPaySlips(payslips, outputFileName);
    	
    	Assert.assertNotNull(new File(outputFileName));
    }
    
	@Test(expected = PayslipOutputWriterException.class)
    public void testPrintPaySlips_with_paySlipObject_asNull() throws PayslipOutputWriterException {
    	GeneratePaySlip generatePaySlip = new GeneratePaySlip();
    	String outputFileName = "payslip_output.csv";
    	
    	generatePaySlip.printPaySlips(null, outputFileName);
    	
    	Assert.assertNotNull(new File(outputFileName));
    }

	@Test
    public void testPaySlipGenerator_client() throws IOException, URISyntaxException {
    	PaySlipGenerator paySlipGenerator = new PaySlipGenerator();
    	
    	paySlipGenerator.main(null);
    }

	@Test
    public void testPaySlipGenerator_clientWithParams() throws IOException, URISyntaxException {
    	PaySlipGenerator paySlipGenerator = new PaySlipGenerator();
    	String[] args = {"input.csv","rules.csv","output.csv"};
    	paySlipGenerator.main(args);
    	
    	String[] _args = {"input.csv","output.csv"};
    	paySlipGenerator.main(_args);
    	
    }
	
	@Test
	public void testPaySlipGenerator_throwPayslipOutputWriterException() throws Exception {
		PaySlipGenerator mockPaySlipGenerator = mock(PaySlipGenerator.class);
		String fileName = "input_employees.csv";
		doThrow(PayslipOutputWriterException.class).when(mockPaySlipGenerator).main(null);
	}

 }