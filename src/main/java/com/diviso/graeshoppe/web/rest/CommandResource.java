package com.diviso.graeshoppe.web.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diviso.graeshoppe.client.customer.api.ContactResourceApi;
import com.diviso.graeshoppe.client.customer.api.CustomerResourceApi;
import com.diviso.graeshoppe.client.customer.domain.CustomerAggregator;
import com.diviso.graeshoppe.client.customer.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
@RequestMapping("/api/command")
public class CommandResource {
	@Autowired
	private ContactResourceApi contactResourceApi;
	@Autowired
	private CustomerResourceApi customerResourceApi;
	private final Logger log = LoggerFactory.getLogger(CommandResource.class);
	
	
	@PostMapping("/customers/register-customer")
	public void createCustomer(@RequestBody CustomerAggregator customerAggregator) {
	       log.debug("REST request to save customer : {}",  customerAggregator );
		CustomerDTO customerDTO= new CustomerDTO();
		ContactDTO contactDTO= new ContactDTO();
		contactDTO.setMobileNumber(customerAggregator.getMobileNumber());
		ContactDTO resultDTO=contactResourceApi.createContactUsingPOST(contactDTO).getBody(); 
		customerDTO.setName(customerAggregator.getName());
		customerDTO.setContactId(resultDTO.getId());
		customerResourceApi.createCustomerUsingPOST(customerDTO);
		

	}
}
