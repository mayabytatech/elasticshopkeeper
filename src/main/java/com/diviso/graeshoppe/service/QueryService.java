package com.diviso.graeshoppe.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.diviso.graeshoppe.client.customer.domain.Customer;
import com.diviso.graeshoppe.client.product.model.Category;
import com.diviso.graeshoppe.client.product.model.Product;
import com.diviso.graeshoppe.client.product.model.StockCurrent;
import com.diviso.graeshoppe.client.product.model.StockDiary;
import com.diviso.graeshoppe.client.product.model.StockLine;
import com.diviso.graeshoppe.client.sale.domain.Sale;
import com.diviso.graeshoppe.client.sale.domain.TicketLine;
import com.diviso.graeshoppe.client.store.domain.Review;
import com.diviso.graeshoppe.client.store.domain.Store;
import com.diviso.graeshoppe.client.store.domain.UserRating;

public interface QueryService {
public Page<Category> findAllCategories(Pageable pageable);
public Page<Product> findProductByCategoryId(Long categoryId,Pageable pageable);
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
public Page<StockCurrent> findAllStockCurrentByCategoryId(Long categoryId, Pageable pageable);
public StockCurrent findStockCurrentByProductId(Long productId);
public StockDiary findStockDiaryByProductId(Long productId);
public Page<StockCurrent> findStockCurrentByProductName(String name, Pageable pageable);
public Page<Product> findAllProducts(Pageable pageable);
public Page<Review> findAllReviews(Pageable pageable);
public Page<UserRating> findAllUserRatings(Pageable pageable);
public Page<Store> findStoreByRegNo(String regNo, Pageable pageable);

}
