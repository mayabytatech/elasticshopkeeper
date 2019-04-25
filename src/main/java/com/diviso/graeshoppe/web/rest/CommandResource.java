package com.diviso.graeshoppe.web.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.diviso.graeshoppe.client.product.api.*;
import com.diviso.graeshoppe.client.product.model.*;
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
	private UomResourceApi uomResourceApi;
	
	@Autowired
	private CategoryResourceApi categoryResourceApi;
	@Autowired
	private	ProductResourceApi productResourceApi ;
	@Autowired
	private ContactResourceApi contactResourceApi;
	@Autowired
	private CustomerResourceApi customerResourceApi;
	private final Logger log = LoggerFactory.getLogger(CommandResource.class);

	@PostMapping("/customers/register-customer")
	public void createCustomer(@RequestBody CustomerAggregator customerAggregator) {

		CustomerDTO customerDTO = new CustomerDTO();
		ContactDTO contactDTO = new ContactDTO();
		customerDTO.setName(customerAggregator.getName());
		contactDTO.setMobileNumber(customerAggregator.getMobileNumber());
		ContactDTO resultDTO = contactResourceApi.createContactUsingPOST(contactDTO).getBody();
		customerDTO.setContactId(resultDTO.getId());
		customerResourceApi.createCustomerUsingPOST(customerDTO);

	}
	
	
	
	@PostMapping("/unit-of-meassurement")
	public void createUOM(@RequestBody UomDTO uomDTO) {
		uomResourceApi.createUomUsingPOST(uomDTO);
	}
	
	@PostMapping("/productCategory")
	public void createProductCategory(@RequestBody CategoryDTO categoryDTO) {
		categoryResourceApi.createCategoryUsingPOST(categoryDTO);
	}
	
	
	@PostMapping("/product")
	public void createProduct(@RequestBody ProductDTO productDTO) {
		productResourceApi.createProductUsingPOST(productDTO);
	}
	
	
	
	
	
	
	}
	
	
	
	
	
	
	
	
	
	
	

