package com.diviso.graeshoppe.web.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


import com.diviso.graeshoppe.client.customer.domain.Customer;
import com.diviso.graeshoppe.client.product.model.*;
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
	UomResourceApi uomResourceApi;
	
	@Autowired
	CategoryResourceApi categoryResourceApi;
	
	
	@Autowired
	private	ProductResourceApi productResourceApi ;
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
		List<CategoryDTO> categoryDTOList=new ArrayList<CategoryDTO>();
	
		for(CategoryDTO categoryDTO:categoryResourceApi.getAllCategoriesUsingGET(page, size, sort).getBody()){
			categoryDTO.setImage(null);
			categoryDTOList.add(categoryDTO);
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>  "+categoryDTO+"   >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		}
		 Page<CategoryDTO> page1=new PageImpl<CategoryDTO>(categoryDTOList);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page1, "/api/query/findAllCategoriesWithOutImage");
        return ResponseEntity.ok().headers(headers).body(categoryDTOList);
		
		
	}
	
	
	@GetMapping("/products")
	public ResponseEntity<List<ProductDTO>> findAllProduct( Boolean eagerload,Integer page,Integer size,List<String> sort){
	return productResourceApi.getAllProductsUsingGET(eagerload, page, size, sort);
	}
	

	
}
