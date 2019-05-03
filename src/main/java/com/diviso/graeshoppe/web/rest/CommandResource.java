package com.diviso.graeshoppe.web.rest;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.diviso.graeshoppe.client.product.api.*;
import com.diviso.graeshoppe.client.product.model.*;
import com.diviso.graeshoppe.client.sale.api.*;
import com.diviso.graeshoppe.client.sale.model.SaleDTO;
import com.diviso.graeshoppe.client.sale.model.TicketLineDTO;
import com.diviso.graeshoppe.client.aggregators.*;
import com.diviso.graeshoppe.client.customer.api.ContactResourceApi;
import com.diviso.graeshoppe.client.customer.api.CustomerResourceApi;
import com.diviso.graeshoppe.client.customer.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
	@Autowired
	SaleResourceApi saleResourceApi;
	
	@Autowired
	TicketLineResourceApi ticketLineResourceApi;
	private final Logger log = LoggerFactory.getLogger(CommandResource.class);

	@PostMapping("/customers/register-customer")
	public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerAggregator customerAggregator) {

		CustomerDTO customerDTO = new CustomerDTO();
		ContactDTO contactDTO = new ContactDTO();
		customerDTO.setName(customerAggregator.getName());
		contactDTO.setMobileNumber(customerAggregator.getMobileNumber());
		ContactDTO resultDTO = contactResourceApi.createContactUsingPOST(contactDTO).getBody();
		customerDTO.setContactId(resultDTO.getId());
	return	customerResourceApi.createCustomerUsingPOST(customerDTO);

	}
	
	@PutMapping("/customers")
	public ResponseEntity<CustomerDTO> updateCustomer(@RequestBody CustomerDTO customerDTO){
		return customerResourceApi.updateCustomerUsingPUT(customerDTO);
	}
	
	@DeleteMapping("/customers/{id}")
	public void deleteCustomer(@PathVariable Long id){
		customerResourceApi.deleteCustomerUsingDELETE(id);
	}
	
	@PutMapping("/categories")
	public ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO categoryDTO){
		return categoryResourceApi.updateCategoryUsingPUT(categoryDTO);
	}
	
	@DeleteMapping("/categories/{id}")
	public void deleteCategory(@PathVariable Long id){
		categoryResourceApi.deleteCategoryUsingDELETE(id);
	}
	@PostMapping("/unit-of-meassurement")
	public ResponseEntity<UomDTO> createUOM(@RequestBody UomDTO uomDTO) {
		return uomResourceApi.createUomUsingPOST(uomDTO);
	}
	
	@PostMapping("/productCategory")
	public ResponseEntity<CategoryDTO> createProductCategory(@RequestBody CategoryDTO categoryDTO) {
		return categoryResourceApi.createCategoryUsingPOST(categoryDTO);
	}
	
	
	@PostMapping("/products")
	public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
		return productResourceApi.createProductUsingPOST(productDTO);
	}
	
	@PutMapping("/products")
	public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO productDTO){
		return productResourceApi.updateProductUsingPUT(productDTO);
	}
	
	@DeleteMapping("/products/{id}")
	public void deleteProduct(@PathVariable Long id){
		productResourceApi.deleteProductUsingDELETE(id);
	}
	
	@PostMapping("/sales")
	public ResponseEntity<SaleDTO> createSale(@RequestBody SaleDTO saleDTO){
		return	saleResourceApi.createSaleUsingPOST(saleDTO);	
	}
	
	@PutMapping("/sales")
	public ResponseEntity<SaleDTO> updateSale(@RequestBody SaleDTO saleDTO){
		return this.saleResourceApi.updateSaleUsingPUT(saleDTO);
	}
	
	@DeleteMapping("/sales/{id}")
	public void deleteSale(@PathVariable Long id) {
		this.ticketLineResourceApi.findAllTicketLinesBySaleIdUsingGET(id).getBody().forEach(ticket -> {this.deleteTicketline(ticket.getId());});
		this.saleResourceApi.deleteSaleUsingDELETE(id);
	}
	
	@PostMapping("/ticket-lines")
	public ResponseEntity<TicketLineDTO> createTickerLine(@RequestBody TicketLineDTO ticketLineDTO) {
		return this.ticketLineResourceApi.createTicketLineUsingPOST(ticketLineDTO);
	}
	
	@PutMapping("/ticket-lines")
	public ResponseEntity<TicketLineDTO> updateTicketLine(@RequestBody TicketLineDTO ticketLineDTO){
		return ticketLineResourceApi.updateTicketLineUsingPUT(ticketLineDTO);
	}
	
	@DeleteMapping("/ticket-lines/{id}")
	public void deleteTicketline(@PathVariable Long id){
		ticketLineResourceApi.deleteTicketLineUsingDELETE(id);
	}
	
	@PutMapping("/uoms")
	public ResponseEntity<UomDTO> updateUOM(@RequestBody UomDTO uomDTO){
		return uomResourceApi.updateUomUsingPUT(uomDTO);
	}
	
	
	@DeleteMapping("/uoms/{id}")
	public void deleteUOM(@PathVariable  Long id){
		uomResourceApi.deleteUomUsingDELETE(id);
	}
	
	
}
