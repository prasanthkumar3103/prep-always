package com.au.myob.payslip.vo;

/**
 * Rules Object
 * @author Silas
 *
 */

public class Rule {
	
	private int minimum_salary;
	private int maximum_salary;
	private int fixed_tax;
	private float variable_tax;
	public int getMinimum_salary() {
		return minimum_salary;
	}
	public void setMinimum_salary(int minimum_salary) {
		this.minimum_salary = minimum_salary;
	}
	public int getMaximum_salary() {
		return maximum_salary;
	}
	public void setMaximum_salary(int maximum_salary) {
		this.maximum_salary = maximum_salary;
	}
	public int getFixed_tax() {
		return fixed_tax;
	}
	public void setFixed_tax(int fixed_tax) {
		this.fixed_tax = fixed_tax;
	}
	public float getVariable_tax() {
		return variable_tax;
	}
	public void setVariable_tax(float variable_tax) {
		this.variable_tax = variable_tax;
	}

	

}
