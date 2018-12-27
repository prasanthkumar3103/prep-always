package com.au.myob.payslip.vo;

/**
 * Employee Object
 * @author Silas
 *
 */

public class Employee {

	private String firstName;
	private String lastName;
	private int annaulSalary;
	private String superRate;
	private String stateDate;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getAnnaulSalary() {
		return annaulSalary;
	}

	public void setAnnaulSalary(int annaulSalary) {
		this.annaulSalary = annaulSalary;
	}

	public String getSuperRate() {
		return superRate;
	}

	public void setSuperRate(String superRate) {
		this.superRate = superRate;
	}

	public String getStateDate() {
		return stateDate;
	}

	public void setStateDate(String stateDate) {
		this.stateDate = stateDate;
	}

}