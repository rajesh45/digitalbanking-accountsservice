package com.capg.accservices.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "account_tansaction", catalog="postgres", schema="public")
public class Transaction implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="transaction_id")
	private int transactionId; 
	
	@Column(name="tx_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date transactionDate;
	
	@Column(name="tx_details")
	private String transactionDetails; 
	
	@Column(name="account_no")
	private Integer accountNo; 

	@Column(name="tx_type")
	private String transactionType;
	
    @Column(name="tx_value")
	private Double transactionValue;
    
    @Column(name="tx_description")
    private String transactionDescription;

	public int getTransactionId() {
		System.out.println("GETTING TRANSACTION Id");
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		System.out.println("SETTING TRANSACTION Id");
		this.transactionId = transactionId;
	}

	public Date getTransactionDate() {
		System.out.println("GETTING TRANSACTION DATE");
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		System.out.println("SETTING TRANSACTION DATE");
		this.transactionDate = transactionDate;
	}

	public String getTransactionDetails() {
		System.out.println("GETTING TRANSACTION DETAILS");
		return transactionDetails;
	}

	public void setTransactionDetails(String transactionDetails) {
		System.out.println("SETTING TRANSACTION DETAILS");
		this.transactionDetails = transactionDetails;
	}

	public int getAccountNo() {
		System.out.println("GETTING ACCOUNT NO");
		return accountNo;
	}

	public void setAccountNo(int accountNo) {
		System.out.println("SETTING ACCOUNT NO");
		this.accountNo = accountNo;
	}

	public String getTransactionType() {
		System.out.println("GETTING TRANSACTION TYPE");
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		System.out.println("SETTING TRANSACTION TYPE");
		this.transactionType = transactionType;
	}

	public Double getTransactionValue() {
		System.out.println("GETTING TRANSACTION VALUE");
		return transactionValue;
	}

	public void setTransactionValue(Double transactionValue) {
		System.out.println("SETTING TRANSACTION VALUE");
		this.transactionValue = transactionValue;
	}

	public String getTransactionDescription() {
		System.out.println("GETTING TRANSACTION DESCRIPTION");
		return transactionDescription;
	}

	public void setTransactionDescription(String transactionDescription) {
		System.out.println("SETTING TRANSACTION DESCRIPTION");
		this.transactionDescription = transactionDescription;
	}

	@Override
	public String toString() {
		return "Transaction [transactionId=" + transactionId + ", transactionDate=" + transactionDate
				+ ", transactionDetails=" + transactionDetails + ", accountNo=" + accountNo + ", transaction_type="
				+ transactionType + ", transactionValue=" + transactionValue + ", transactionDescription="
				+ transactionDescription + "]";
	} 
}