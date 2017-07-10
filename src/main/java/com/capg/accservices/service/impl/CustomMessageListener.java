package com.capg.accservices.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.capg.accservices.model.Customer;
import com.capg.accservices.service.AccountService;
import com.capg.accservices.service.impl.AccountServiceImpl;

 
@Service
public class CustomMessageListener {

    private static final Logger log = LoggerFactory.getLogger(CustomMessageListener.class);

    @Autowired
	private AccountService accountService;
    
    @RabbitListener(queues = "DigiappGenericQueue")
    public void receiveMessage(final Message message) {
        log.info("Received message as generic: {}", message.toString());
    }

  /*  @RabbitListener(queues = "DigiappSpecificQueue")
    public void receiveMessage(final CustomMessage customMessage) {
        log.info("Received message as specific class: {}", customMessage.toString());
    }*/
    
    @RabbitListener(queues = "DigiappSpecificQueue")
    public void receiveMessage(Customer customerDetails) {    	
        log.info("Received message as specific class: {}", customerDetails.toString());
        accountService.saveCustomerDetails(customerDetails);
    }
}
