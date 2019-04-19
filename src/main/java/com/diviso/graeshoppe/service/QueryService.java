package com.diviso.graeshoppe.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.diviso.graeshoppe.client.customer.domain.Customer;

/*import com.diviso.graeshoppe.client.product.domain.Category;
import com.diviso.graeshoppe.client.product.domain.Product;
import com.diviso.graeshoppe.domain.Result;
*/
public interface QueryService {
/*public Page<Category> findAllCategories(Pageable pageable);
public Page<Product> findProductByCategoryId(Long categoryId,Pageable pageable);
public List<Result> findAll(String searchTerm,Pageable pageable);*/
public Page<Customer> findAllCustomers(String searchTerm, Pageable pageable);
}
