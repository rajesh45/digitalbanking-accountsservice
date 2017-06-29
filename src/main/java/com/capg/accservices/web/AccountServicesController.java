package com.capg.accservices.web;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capg.accservices.model.Account;
import com.capg.accservices.model.Transaction;
import com.capg.accservices.service.AccountService;

/***
 * Account services related API controller
 * @author sujillel
 *
 */

@RestController
@ComponentScan("com.capg.accservices")
@CrossOrigin
@EnableAutoConfiguration
public class AccountServicesController {

	@Autowired
	private AccountService accountService;
    
	@RequestMapping(value="/accservices/{customerId}/accounts",method = RequestMethod.GET)
    public List<Account> getAccounts(@PathVariable  int customerId) {
		System.out.println("REQUEST MAPPED INSIDE CONTROLLER");
		return accountService.getCustomerAccounts(customerId);
    }
	
	@RequestMapping(value="/accservices/{accountNo}/account",method = RequestMethod.GET)
    public Account getAccountById(@PathVariable  int accountNo) {
		System.out.println("REQUEST MAPPED INSIDE CONTROLLER");
		return accountService.getAccountDetailsById(accountNo);
    }
	
	
	@RequestMapping(value="/accservices/{accountNo}/{amount}/accountdeposit",method = RequestMethod.POST)
    public String depositeAmount(@PathVariable  Integer accountNo, @PathVariable  Double amount) {
		System.out.println("REQUEST MAPPED INSIDE CONTROLLER");
		accountService.depositeAmount(accountNo, amount);
		return "Sucess";
    }
	
	@RequestMapping(value="/accservices/{accountNo}/{amount}/accountwithdraw",method = RequestMethod.POST)
    public String withdrawAmount(@PathVariable  Integer accountNo, @PathVariable  Double amount) {
		System.out.println("REQUEST MAPPED INSIDE CONTROLLER");
		accountService.withdrawAmount(accountNo, amount);
		return "Sucess";
    }
	
	//Dummy
	
	@RequestMapping(value="/accservices/testaccountwithdraw",method = RequestMethod.POST)
	@Transactional(rollbackFor=Exception.class)
    public String testaccountwithdraw(@RequestBody MultiValueMap<String, String> map){
		
		System.out.println("================");
		System.out.println("REQUEST MAPPED INSIDE CONTROLLER "+","+map.get("SavingsAccountNo").get(0));
		System.out.println("REQUEST MAPPED INSIDE CONTROLLER "+","+map.get("DebitAmount").get(0));		
		System.out.println("REQUEST MAPPED INSIDE CREDIT CARD PAYMENT CONTROLLER" +map.get("CreditCardNo")+","+map.get("CreditAmount"));
		
		accountService.withdrawBillAmountandDepositCC(Integer.parseInt(map.get("SavingsAccountNo").get(0)), Double.parseDouble(map.get("DebitAmount").get(0)),Long.parseLong(map.get("CreditCardNo").get(0)), Double.parseDouble(map.get("CreditAmount").get(0)));
		
		System.out.println("---------------");
		return "TXT Success!";
    }
	
	@Transactional(rollbackFor=Exception.class)
	@RequestMapping(value="/accservices/{accountNum}/{startDate}/{endDate}/getRecentTransactions"
			,method = RequestMethod.POST)
	public List<Transaction> getRecentTransactions(@PathVariable Integer accountNum
			, @PathVariable String startDate
			, @PathVariable String endDate) {
		System.out.println("REQUEST MAPPED INSIDE CONTROLLER");
		return accountService.getRecentTransactions(accountNum, startDate, endDate);
	}
}