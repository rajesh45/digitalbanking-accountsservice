package com.capg.accservices.service.impl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.capg.accservices.dao.AccountDao;
import com.capg.accservices.dao.CustomerDao;
import com.capg.accservices.dao.TransactionDao;
import com.capg.accservices.model.Account;
import com.capg.accservices.model.Customer;
import com.capg.accservices.model.Transaction;
import com.capg.accservices.service.AccountService;


@Service
public class AccountServiceImpl implements AccountService{
	static Logger logger = Logger.getLogger(AccountServiceImpl.class);
	@Autowired
	private AccountDao accountDao;
	
	@Autowired
	private CustomerDao customerDao;

	@Autowired
	private TransactionDao transactionDao;

	@Value("${max.recent.transactions}")
	private Integer maxRecentTransactions;

	public List<Account> getCustomerAccounts(Integer customerId){
		System.out.println("REQUEST IN SERVICE");
		List<Account> accountList= accountDao.findByCustomerId(customerId);
		return accountList;
	}

	@Override
	public Account getAccountDetailsById(Integer accountNo) {
		System.out.println("REQUEST IN SERVICE");
		Account account= accountDao.findByAccountNo(accountNo);
		return account;
	}

	@Override
	//@Transactional(rollbackFor=Exception.class)
	public void saveCustomerDetails(Customer customerDetails) {
		System.out.println("REQUEST IN SERVICE");
		Customer customer = new Customer();
		long millis=System.currentTimeMillis();  
        java.sql.Date date=new java.sql.Date(millis);  
		customer.setCustomerId(323125);
		customer.setCustomer_name(customerDetails.getCustomer_name());
	//	customer.setEmail_id(customerDetails.getEmail_id());		
		customer.setMobile_no(546454);
		customer.setPassword(customerDetails.getPassword());
	//	System.out.println("OUTPUT: "+customerDao.findByCustomerId(556633));
	/*	customer.setCustomerId(134125);
		customer.setCustomer_name("Dummy");
		customer.setEmail_id("sathish@gmail.com");		
		customer.setMobile_no(4235445); 
		customer.setPassword("dgsggh");*/
		
		customerDao.save(customer);		
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void depositeAmount(Integer accountNo, Double amount) {
		System.out.println("REQUEST IN SERVICE");
		Account account = accountDao.findByAccountNo(accountNo);
		Transaction transaction =new Transaction();
		double newBalance = account.getAccountBalance() + amount;
		account.setAccountBalance(newBalance);
		accountDao.save(account);
		transaction.setTransactionValue(amount);
		transaction.setAccountNo(accountNo);
		transaction.setTransactionDescription("credit transaction");
		Calendar calendar = Calendar.getInstance();
		transaction.setTransactionDate(new Timestamp (calendar.getTime().getTime()));
		transaction.setTransactionType("Credit");
		transactionDao.save(transaction);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public double withdrawBillAmountandDepositCC(Integer accountNo, Double amount,Long cardNo,Double payableAmount) {
		System.out.println("REQUEST IN withdrawBillAmountandDepositCC SERVICE");
		Account account = accountDao.findByAccountNo(accountNo);
		Transaction transaction =new Transaction();
		if (amount > account.getAccountBalance()) {
			return 0.0D;
		}else {
			Double newBalance = account.getAccountBalance() - amount;
			account.setAccountBalance(newBalance);
			accountDao.save(account);
			transaction.setTransactionValue(amount);
			transaction.setAccountNo(accountNo);
			transaction.setTransactionDescription("debit transaction");
			Calendar calendar = Calendar.getInstance();
			transaction.setTransactionDate(new Timestamp (calendar.getTime().getTime()));
			transaction.setTransactionType("Debit");
			transactionDao.save(transaction);			
			CreditCardAccountforCCPayment(cardNo,payableAmount);
			return account.getAccountBalance();
		}
	}
	
	private boolean CreditCardAccountforCCPayment(Long cardNo,Double payableAmount)
	{
	
		
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		String creditCardNo= Long.toString(cardNo);
		String creditAmount = Double.toString(payableAmount);		
		map.add("CreditCardNo", creditCardNo);
		map.add("CreditAmount", creditAmount);						
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(map , headers);
		System.out.println("Going to call...CC");
		ResponseEntity<String> loginResponse = restTemplate
				  .exchange("http://mydigitalbanking.com:8100/cardservices/payCreditCardBill", HttpMethod.POST, entity, String.class);
		
				if (loginResponse.getStatusCode() == HttpStatus.OK) {
				  System.out.println("Success...Success...Success!!!");
				  System.out.println(loginResponse.getBody());
				  return true;
				}else
				{
					return false;
				}
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class, propagation = Propagation.NESTED)
	public double withdrawAmount(Integer accountNo, Double amount) {
		System.out.println("REQUEST IN SERVICE");
		Account account = accountDao.findByAccountNo(accountNo);
		Transaction transaction =new Transaction();
		if (amount > account.getAccountBalance()) {
			return 0;
		}else {
			Double newBalance = account.getAccountBalance() - amount;
			account.setAccountBalance(newBalance);
			accountDao.save(account);
			transaction.setTransactionValue(amount);
			transaction.setAccountNo(accountNo);
			transaction.setTransactionDescription("debit transaction");
			Calendar calendar = Calendar.getInstance();
			transaction.setTransactionDate(new Timestamp (calendar.getTime().getTime()));
			transaction.setTransactionType("Debit");
			transactionDao.save(transaction);
			return account.getAccountBalance();

		}
	}

	@Override
	public List<Transaction> getRecentTransactions(Integer accountNum, String startDate, String endDate) {
		System.out.println("REQUEST IN SERVICE");
		Date startDt;
		List<Transaction> txList =null;
		try {
			startDt = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
			Date endDt =new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
			txList = transactionDao.getTransactions(accountNum, startDt, endDt);

			// Get max 10 recent transactions
			if(txList!= null && txList.size()>maxRecentTransactions){
				txList = txList.subList(0, maxRecentTransactions-1);
			}
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}
		return txList;
	}
}