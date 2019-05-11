package com.diviso.graeshoppe.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.diviso.graeshoppe.client.customer.domain.Customer;

import com.diviso.graeshoppe.client.product.model.*;
import com.diviso.graeshoppe.client.sale.domain.Sale;
import com.diviso.graeshoppe.client.sale.domain.TicketLine;
import com.diviso.graeshoppe.client.sale.model.SaleDTO;

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
public Page<Product> findAllProduct(Pageable pageable);
public Page<StockLine> findAllStockLines(Pageable pageable);
/**
 * @return
 */
public Page<Sale> findSales(Pageable pageable);
public List<TicketLine> findTicketLinesBySaleId(Long saleId);
public Page<StockCurrent> findAllStockCurrents(Pageable pageable);
public Page<StockDiary> findAllStockDiaries(Pageable pageable);
public Page<Product> findAllProductBySearchTerm(String searchTerm, Pageable pageable);
public Page<StockCurrent> findStockCurrentByProductId(Long productId, Pageable pageable);
public Page<StockDiary> findStockDiaryByProductId(Long productId, Pageable pageable);
public Page<StockCurrent> findStockCurrentByProductName(String name, Pageable pageable);
public Page<Product> findAllProducts(Pageable pageable);
}
