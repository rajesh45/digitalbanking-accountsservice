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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capg.accservices.dao.AccountDao;
import com.capg.accservices.dao.TransactionDao;
import com.capg.accservices.model.Account;
import com.capg.accservices.model.Transaction;
import com.capg.accservices.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService{
	static Logger logger = Logger.getLogger(AccountServiceImpl.class);
	@Autowired
	private AccountDao accountDao;

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