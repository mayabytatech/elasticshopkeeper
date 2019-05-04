package com.diviso.graeshoppe.web.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.diviso.graeshoppe.client.customer.api.ContactResourceApi;
import com.diviso.graeshoppe.client.customer.api.CustomerResourceApi;
import com.diviso.graeshoppe.client.customer.domain.Customer;
import com.diviso.graeshoppe.client.customer.model.ContactDTO;
import com.diviso.graeshoppe.client.customer.model.CustomerDTO;
import com.diviso.graeshoppe.client.product.model.*;
import com.diviso.graeshoppe.client.sale.api.SaleResourceApi;
import com.diviso.graeshoppe.client.sale.api.TicketLineResourceApi;
import com.diviso.graeshoppe.client.sale.model.SaleDTO;
import com.diviso.graeshoppe.client.sale.model.TicketLineDTO;
import com.diviso.graeshoppe.client.product.api.*;

import com.diviso.graeshoppe.service.QueryService;
import com.diviso.graeshoppe.web.rest.util.PaginationUtil;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/query")
public class QueryResource {
	
	@Autowired
	QueryService queryService;
	
	@Autowired
	SaleResourceApi saleResourceApi;

	@Autowired
	UomResourceApi uomResourceApi;
	
	@Autowired
	CategoryResourceApi categoryResourceApi;
	
	@Autowired
	CustomerResourceApi customerResourceApi;
	
	@Autowired
	private	ProductResourceApi productResourceApi ;
	
	@Autowired
	private TicketLineResourceApi ticketLineResourceApi;
	
	@Autowired
	private ContactResourceApi contactResourceApi;
	
/*	@GetMapping("/findAllCategories")
	public Page<Category> findAllCategories(Pageable pageable) {
		return queryService.findAllCategories(pageable);
	}*/
	
	@GetMapping("/findProductByCategoryId/{categoryId}")
	public Page<Product> findProductByCategoryId(@PathVariable Long categoryId,Pageable pageable) {
		return queryService.findProductByCategoryId(categoryId,pageable);
	}
	/*@GetMapping("/findAll/{searchTerm}")
	public List<Result> findAll(@PathVariable String searchTerm,Pageable pageable) {
		return queryService.findAll(searchTerm,pageable);
	}*/
	
	@GetMapping("/findAllCustomer/{searchTerm}")
	public Page<Customer> findAllCustomers(@PathVariable String searchTerm,Pageable pageable) {
			return queryService.findAllCustomers(searchTerm,pageable);
	}
	
	@GetMapping("/findAllCustomers")
	public Page<Customer> findAllCustomersWithoutSearch(Pageable pageable) {
			return queryService.findAllCustomersWithoutSearch(pageable);
	}
	
	@GetMapping("/customers/{id}")
	public ResponseEntity<CustomerDTO> findCustomerById(@PathVariable Long id) {
		return this.customerResourceApi.getCustomerUsingGET(id);
	}
	
	@GetMapping("contacts/{id}")
	public ResponseEntity<ContactDTO> findContactById(@PathVariable Long id) {
		return this.contactResourceApi.getContactUsingGET(id);
	}
	
	
	/*@GetMapping("/findAllUom")
	public List<String> findAllUom(Pageable pageable) {
		return queryService.findAllUom(pageable);
	}*/
	
	@GetMapping("/findAllUom")
	public ResponseEntity<List<UomDTO>> findAllUom(@RequestParam(required = false) Integer page,@RequestParam(required = false) Integer size, 
			@RequestParam(value = "sort", required = false) List<String> sort) {
		
		return uomResourceApi.getAllUomsUsingGET(page,size, sort);
	}
	
	@GetMapping("/findAllCateogories")
	public ResponseEntity<List<CategoryDTO>> findAllCategories(@RequestParam(required = false) Integer page,@RequestParam(required = false) Integer size, 
			@RequestParam(value = "sort", required = false) List<String> sort) {
		return categoryResourceApi.getAllCategoriesUsingGET(page, size, sort);
	
	}
	
	@GetMapping("/findAllCategoriesWithOutImage")
	public ResponseEntity<List<CategoryDTO>> findAllCategoriesWithOutImage(@RequestParam(required = false) Integer page,@RequestParam(required = false) Integer size, 
			@RequestParam(value = "sort", required = false) List<String> sort){
		return ResponseEntity.ok().body(categoryResourceApi.getAllCategoriesUsingGET(page, size, sort).getBody().stream().map(c -> {c.setImage(null); return c;}).collect(Collectors.toList()));
	}
	
	
	/*@GetMapping("/products")
	public ResponseEntity<List<ProductDTO>> findAllProduct( Boolean eagerload,Integer page,Integer size,List<String> sort){
	return productResourceApi.getAllProductsUsingGET(eagerload, page, size, sort);
	}*/
	
	@GetMapping("/products")
	public ResponseEntity<List<ProductDTO>> findAllProduct(Pageable page){
		return productResourceApi.listToDtoUsingPOST(queryService.findAllProduct(page).getContent());
	}

	@GetMapping("/ticket-lines")
	public ResponseEntity<List<TicketLineDTO>> findAllTicketlines(Integer page,Integer size,List<String> sort){
		return ticketLineResourceApi.getAllTicketLinesUsingGET(page, size, sort);
	}
	
	@GetMapping("/ticket-lines/{id}")
	public ResponseEntity<TicketLineDTO> findOneTicketLines(@PathVariable Long id){
		return ticketLineResourceApi.getTicketLineUsingGET(id);
	}
	
	@GetMapping("/products/{id}")
	public ResponseEntity<ProductDTO> findProduct(@PathVariable Long id){
		return this.productResourceApi.getProductUsingGET(id);
	}
	
	@GetMapping("/products/export")
	public ResponseEntity<byte[]> exportProducts() {
		return this.productResourceApi.getPdfAllProdutsWithPriceUsingGET();
	}
	
	@GetMapping("/customers/export")
	public ResponseEntity<byte[]> exportCustomers() {
		return this.customerResourceApi.getPdfAllCustomersUsingGET();
	}
	
	@GetMapping("/sales")
	public ResponseEntity<List<SaleDTO>> findAllSales(Integer page,Integer size,List<String> sort) {
		return this.saleResourceApi.getAllSalesUsingGET(page, size, sort);
	}
	
	@GetMapping("/sales/{id}")
	public ResponseEntity<SaleDTO> findSaleById(@PathVariable Long id) {
		return this.saleResourceApi.getSaleUsingGET(id);
	}
	
	@GetMapping("/stocklines")
	public ResponseEntity<List<StockLine>> findAllStockLines(Pageable pageable) {
		return ResponseEntity.ok().body(this.queryService.findAllStockLines(pageable).getContent());
	}
	
}
