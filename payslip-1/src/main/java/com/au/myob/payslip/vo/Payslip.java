package com.au.myob.payslip.vo;

/**
 * Payslip Object
 * @author Silas
 *
 */

public class Payslip {
	
	String fullName;
	String payPeriod;
	int grossIncome;
	int incomeTax;
	int netIncome;
	int sprAnnuation;
	
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getPayPeriod() {
		return payPeriod;
	}
	public void setPayPeriod(String payPeriod) {
		this.payPeriod = payPeriod;
	}
	public int getGrossIncome() {
		return grossIncome;
	}
	public void setGrossIncome(int grossIncome) {
		this.grossIncome = grossIncome;
	}
	public int getIncomeTax() {
		return incomeTax;
	}
	public void setIncomeTax(int incomeTax) {
		this.incomeTax = incomeTax;
	}
	public double getNetIncome() {
		return netIncome;
	}
	public void setNetIncome(int netIncome) {
		this.netIncome = netIncome;
	}
	public int getSprAnnuation() {
		return sprAnnuation;
	}
	public void setSprAnnuation(int sprAnnuation) {
		this.sprAnnuation = sprAnnuation;
	}
	
}
