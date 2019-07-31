package com.diviso.graeshoppe.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.diviso.graeshoppe.client.customer.domain.Customer;
import com.diviso.graeshoppe.client.order.domain.Order;
import com.diviso.graeshoppe.client.order.domain.OrderLine;
import com.diviso.graeshoppe.client.product.model.Category;
import com.diviso.graeshoppe.client.product.model.EntryLineItem;
import com.diviso.graeshoppe.client.product.model.Product;
import com.diviso.graeshoppe.client.product.model.StockCurrent;
import com.diviso.graeshoppe.client.product.model.StockEntry;
import com.diviso.graeshoppe.client.sale.domain.Sale;
import com.diviso.graeshoppe.client.sale.domain.TicketLine;
import com.diviso.graeshoppe.client.store.domain.DeliveryInfo;
import com.diviso.graeshoppe.client.store.domain.Review;
import com.diviso.graeshoppe.client.store.domain.Store;
import com.diviso.graeshoppe.client.store.domain.UserRating;
import com.diviso.graeshoppe.client.store.model.DeliveryInfoDTO;

public interface QueryService {
	public Page<Category> findAllCategories(Pageable pageable);

	public Page<Product> findProductByCategoryId(Long categoryId, String storeId, Pageable pageable);

	public Page<Customer> findAllCustomers(String searchTerm, Pageable pageable);

	public List<String> findAllUom(Pageable pageable);

	public Page<Customer> findAllCustomersWithoutSearch(Pageable pageable);

	/**
	 * @param pageable
	 * @return
	 */
	public Page<Product> findAllProduct(String storeId, Pageable pageable);

	public Page<EntryLineItem> findAllEntryLineItems(String storeId, Pageable pageable);

	/**
	 * @return
	 */
	public Page<Sale> findSales(String storeId, Pageable pageable);

	public List<TicketLine> findTicketLinesBySaleId(Long saleId);

	public Page<StockCurrent> findAllStockCurrents(String storeId, Pageable pageable);

	public Page<StockEntry> findAllStockEntries(String storeId, Pageable pageable);

	public Page<Product> findAllProductBySearchTerm(String searchTerm, String storeId, Pageable pageable);

	public Page<StockCurrent> findAllStockCurrentByCategoryId(Long categoryId, String storeId, Pageable pageable);

	public StockCurrent findStockCurrentByProductId(Long productId, String storeId);

	public StockEntry findStockEntryByProductId(Long productId, String storeId);

	public Page<StockCurrent> findStockCurrentByProductName(String name, String storeId, Pageable pageable);

	public Page<Product> findAllProducts(String storeId, Pageable pageable);

	public Page<Review> findAllReviews(String storeId, Pageable pageable);

	public Page<UserRating> findAllUserRatings(String storeId, Pageable pageable);

	public Store findStoreByRegNo(String regNo);

	/**
	 * @param id
	 * @return
	 */
	public Page<DeliveryInfo> findDeliveryInfoByStoreId(Long id);

	/**
	 * @param storeId
	 */
	public Page<Order> findOrderByStoreId(String storeId, Pageable pageable);

	/**
	 * @param orderId
	 * @return
	 */
	List<OrderLine> findOrderLinesByOrderId(Long orderId);

	/**
	 * @param storeId
	 */
	public Page<Category> findAllCategories(String storeId);

}
