package com.capg.accservices.model;

import java.sql.Date;

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
@Table(name = "Customer", catalog="postgres", schema="public")
public class Customer {
	
@Id 
@Column(name="customer_id")	
private int customerId;

public int getCustomerId() {
	return customerId;
}
public void setCustomerId(int customerId) {
	this.customerId = customerId;
}
@Override
public String toString() {
	return "Customer [customer_id=" + customerId + ", customer_name=" + customer_name + ", password=" + password
			+ ", last_login=" + last_login + ", mobile_no=" + mobile_no + ", email_id=" + email_id + "]";
}
@Column(name="customer_name") 
@NotNull
private String customer_name;

@Column(name="password")
private String password;

@Column(name="last_login")
private Date last_login;

@Column(name="mobile_no")
private int mobile_no;

@Column(name="email_id")
private String email_id;


public String getCustomer_name() {
	return customer_name;
}
public void setCustomer_name(String customer_name) {
	this.customer_name = customer_name;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public Date getLast_login() {
	return last_login;
}
public void setLast_login(Date last_login) {
	this.last_login = last_login;
}
public int getMobile_no() {
	return mobile_no;
}
public void setMobile_no(int mobile_no) {
	this.mobile_no = mobile_no;
}
public String getEmail_id() {
	return email_id;
}
public void setEmail_id(String email_id) {
	this.email_id = email_id;
}


}
