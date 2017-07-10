package com.capg.accservices.model;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
@Entity
@Table(name = "Account")

public class Account {

		@Id
	
	@Column(name="account_no")

	private int accountNo;
	
	@Column(name="customer_id")
	private int customerId;
  
	public int getAccountNo() {
		System.out.println("GETTING ACCOUNT NUMBER");
		return accountNo;
	}
	public void setAccountNo(int accountNo) {
		System.out.println("SETTING ACCOUNT NUMBER");
		this.accountNo = accountNo;
	}
	public int getCustomerId() {
		System.out.println("GETTING CUSTOMER ID");
		return customerId;
	}
	public void setCustomerId(int customerId) {
		System.out.println("SETTING CUSTOMER ID");
		this.customerId = customerId;
	}
	public String getIfscCode() {
		System.out.println("GETTING IFSC CODE");
		return ifscCode;
	}
	public void setIfscCode(String ifscCode) {
		System.out.println("SETTING IFSC CODE");
		this.ifscCode = ifscCode;
	}
	public String getAccountType() {
		System.out.println("GETTING ACCOUNT TYPE");
		return accountType;
	}
	public void setAccountType(String accountType) {
		System.out.println("SETTING ACCOUNT TYPE");
		this.accountType = accountType;
	}
	public double getAccountBalance() {
		System.out.println("GETTING ACCOUNT BALANCE");
		return accountBalance;
	}
	public void setAccountBalance(double accountBalance) {
		System.out.println("SETTING ACCOUNT BALANCE");
		this.accountBalance = accountBalance;
	}
	public Date getAccountCreationDate() {
		System.out.println("GETTING ACCOUNT CREATION DATE");
		return accountCreationDate;
	}
	public void setAccountCreationDate(Date accountCreationDate) {
		System.out.println("SETTING ACCOUNT CREATION DATE");
		this.accountCreationDate = accountCreationDate;
	}
	public boolean isAccountStatus() {
		System.out.println("GETTING ACCOUNT STATUS");
		return accountStatus;
	}
	public void setAccountStatus(boolean accountStatus) {
		System.out.println("SETTING ACCOUNT STATUS");
		this.accountStatus = accountStatus;
	}
	@NotNull
	@Column(name="ifsc_code")
	private String ifscCode;
	  @NotNull
		@Column(name="account_type")

	private  String accountType;
	  @NotNull
		@Column(name="account_balance")

	  private double accountBalance;
		@Column(name="account_creation_date")

	  private Date accountCreationDate;
	  @NotNull

		@Column(name="account_status  	")
	private boolean accountStatus;
	
}
