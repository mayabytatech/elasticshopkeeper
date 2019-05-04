package com.diviso.graeshoppe.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.diviso.graeshoppe.client.customer.domain.Customer;

import com.diviso.graeshoppe.client.product.model.*;

public interface QueryService {
public Page<Category> findAllCategories(Pageable pageable);
public Page<Product> findProductByCategoryId(Long categoryId,Pageable pageable);
//public List<Result> findAll(String searchTerm,Pageable pageable);
public Page<Customer> findAllCustomers(String searchTerm, Pageable pageable);
public List<String> findAllUom(Pageable pageable);
public Page<Customer> findAllCustomersWithoutSearch(Pageable pageable);
/**
 * @param pageable
 * @return
 */
Page<Product> findAllProduct(Pageable pageable);
Page<StockLine> findAllStockLines(Pageable pageable);
}
