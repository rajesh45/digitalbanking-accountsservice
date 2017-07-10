package com.capg.accservices.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.capg.accservices.model.Customer;

@Repository
@Transactional
public interface CustomerDao extends JpaRepository<Customer,Integer> {
	
  List<Customer> findByCustomerId(int customerId);

}
