package com.diviso.graeshoppe.web.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.diviso.graeshoppe.client.customer.api.ContactResourceApi;
import com.diviso.graeshoppe.client.customer.api.CustomerResourceApi;
import com.diviso.graeshoppe.client.customer.domain.Customer;
import com.diviso.graeshoppe.client.customer.model.ContactDTO;
import com.diviso.graeshoppe.client.customer.model.CustomerDTO;
import com.diviso.graeshoppe.client.order.domain.Order;
import com.diviso.graeshoppe.client.order.domain.OrderLine;
import com.diviso.graeshoppe.client.product.api.CategoryResourceApi;
import com.diviso.graeshoppe.client.product.api.LoadControllerApi;
import com.diviso.graeshoppe.client.product.api.ProductResourceApi;
import com.diviso.graeshoppe.client.product.api.StockCurrentResourceApi;
import com.diviso.graeshoppe.client.product.api.StockDiaryResourceApi;
import com.diviso.graeshoppe.client.product.api.UomResourceApi;
import com.diviso.graeshoppe.client.product.model.CategoryDTO;
import com.diviso.graeshoppe.client.product.model.Product;
import com.diviso.graeshoppe.client.product.model.ProductDTO;
import com.diviso.graeshoppe.client.product.model.StockCurrent;
import com.diviso.graeshoppe.client.product.model.StockCurrentDTO;
import com.diviso.graeshoppe.client.product.model.StockDiary;
import com.diviso.graeshoppe.client.product.model.StockDiaryDTO;
import com.diviso.graeshoppe.client.product.model.StockLine;
import com.diviso.graeshoppe.client.product.model.UomDTO;
import com.diviso.graeshoppe.client.sale.api.SaleResourceApi;
import com.diviso.graeshoppe.client.sale.api.TicketLineResourceApi;
import com.diviso.graeshoppe.client.sale.domain.Sale;
import com.diviso.graeshoppe.client.sale.domain.TicketLine;
import com.diviso.graeshoppe.client.sale.model.SaleDTO;
import com.diviso.graeshoppe.client.sale.model.TicketLineDTO;
import com.diviso.graeshoppe.client.store.api.DeliveryInfoResourceApi;
import com.diviso.graeshoppe.client.store.api.StoreResourceApi;
import com.diviso.graeshoppe.client.store.api.TypeResourceApi;
import com.diviso.graeshoppe.client.store.domain.Review;
import com.diviso.graeshoppe.client.store.domain.Store;
import com.diviso.graeshoppe.client.store.domain.UserRating;
import com.diviso.graeshoppe.client.store.model.DeliveryInfoDTO;
import com.diviso.graeshoppe.client.store.model.StoreBundleDTO;
import com.diviso.graeshoppe.client.store.model.StoreDTO;
import com.diviso.graeshoppe.client.store.model.TypeDTO;
import com.diviso.graeshoppe.service.QueryService;
import com.diviso.graeshoppe.service.dto.PdfDTO;
import com.diviso.graeshoppe.service.dto.SaleAggregate;

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
	private ProductResourceApi productResourceApi;

	@Autowired
	private TicketLineResourceApi ticketLineResourceApi;

	@Autowired
	private ContactResourceApi contactResourceApi;

	@Autowired
	private StockCurrentResourceApi stockCurrentResourceApi;

	@Autowired
	private StockDiaryResourceApi stockDiaryResourceApi;

	@Autowired
	private StoreResourceApi storeResourceApi;

	@Autowired
	DeliveryInfoResourceApi deliveryInfoResourceApi;

	@Autowired
	TypeResourceApi typeResourceApi;

	@Autowired
	LoadControllerApi loadControllerApi;
	

    private final Logger log = LoggerFactory.getLogger(QueryResource.class);

	@GetMapping("/findAllProductByCategoryId/{categoryId}/{storeId}")
	public Page<Product> findAllProductsByCategoryId(@PathVariable Long categoryId, @PathVariable String storeId,
			Pageable pageable) {
		return queryService.findProductByCategoryId(categoryId, storeId, pageable);
	}

	@GetMapping("/findAllStockCurrentByProductName/{name}/{storeId}")
	public Page<StockCurrent> findAllStockCurrentByProductName(@PathVariable String name, @PathVariable String storeId,
			Pageable pageable) {
		return queryService.findStockCurrentByProductName(name, storeId, pageable);
	}

	@GetMapping("/findStockDiaryByProductId/{productId}/{storeId}")
	public ResponseEntity<StockDiary> findStockDiaryByProductId(@PathVariable Long productId,
			@PathVariable String storeId) {
		return ResponseEntity.ok().body(queryService.findStockDiaryByProductId(productId, storeId));
	}

	@GetMapping("/findAllStockCurrentsByCategoryId/{categoryId}/{storeId}")
	public Page<StockCurrent> findAllStockCurrentByCategory(@PathVariable Long categoryId, @PathVariable String storeId,
			Pageable pageable) {
		return queryService.findAllStockCurrentByCategoryId(categoryId, storeId, pageable);
	}

	@GetMapping("/findStockCurrentByProductId/{productId}/{storeId}")
	public ResponseEntity<StockCurrent> findStockCurrentByProductId(@PathVariable Long productId,
			@PathVariable String storeId) {
		return ResponseEntity.ok().body(queryService.findStockCurrentByProductId(productId, storeId));
	}

	/**/
	@GetMapping("/findStockCurrentDTOByProductId/{productId}")
	public ResponseEntity<StockCurrentDTO> findStockCurrentDTOByProductId(@PathVariable Long productId) {
		return this.stockCurrentResourceApi.getStockCurrentByProductIdUsingGET(productId);
	}

	/**/
	@GetMapping("/findProductBySearchTerm/{searchTerm}/{storeId}")
	public Page<Product> findAllProductBySearchTerm(@PathVariable String searchTerm, @PathVariable String storeId,
			Pageable pageable) {
		return queryService.findAllProductBySearchTerm(searchTerm, storeId, pageable);
	}

	@GetMapping("/findAllCustomer/{searchTerm}")
	public Page<Customer> findAllCustomers(@PathVariable String searchTerm, @PathVariable String storeId,
			Pageable pageable) {
		return queryService.findAllCustomers(searchTerm, pageable);
	}

	@GetMapping("/findAllCustomers")
	public Page<Customer> findAllCustomersWithoutSearch(Pageable pageable) {
		return queryService.findAllCustomersWithoutSearch(pageable);
	}

	@GetMapping("/findAllProducts/{storeId}")
	public Page<Product> findAllProducts(@PathVariable String storeId, Pageable pageable) {
		return queryService.findAllProducts(storeId, pageable);
	}

	@GetMapping("/customers/{id}")
	public ResponseEntity<CustomerDTO> findCustomerById(@PathVariable Long id) {
		return this.customerResourceApi.getCustomerUsingGET(id);
	}

	@GetMapping("contacts/{id}")
	public ResponseEntity<ContactDTO> findContactById(@PathVariable Long id) {
		return this.contactResourceApi.getContactUsingGET(id);
	}

	@GetMapping("/findAllUom")
	public ResponseEntity<List<UomDTO>> findAllUom(@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer size,
			@RequestParam(value = "sort", required = false) ArrayList<String> sort) {

		return uomResourceApi.getAllUomsUsingGET(page, size, sort);
	}

	@GetMapping("/findAllCateogories/{storeId}")
	public ResponseEntity<List<CategoryDTO>> findAllCategories(@PathVariable String storeId) {

		log.info("List categories   >>>>>>>>>>>>>>>>>>>  "+queryService.findAllCategories(storeId).getContent());
		log.info("DTO LIST>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.    "+	categoryResourceApi.listToDToUsingPOST(queryService.findAllCategories(storeId).getContent()));
		return categoryResourceApi.listToDToUsingPOST(queryService.findAllCategories(storeId).getContent());

	}

	@GetMapping("/findAllCategoriesWithOutImage")
	public ResponseEntity<List<CategoryDTO>> findAllCategoriesWithOutImage(@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer size,
			@RequestParam(value = "sort", required = false) ArrayList<String> sort) {
		return ResponseEntity.ok()
				.body(categoryResourceApi.getAllCategoriesUsingGET(page, size, sort).getBody().stream().map(c -> {
					c.setImage(null);
					return c;
				}).collect(Collectors.toList()));
	}

	@GetMapping("/productByStoreId/{storeId}")
	public ResponseEntity<List<ProductDTO>> findAllProduct(@PathVariable String storeId, Pageable page) {
		return productResourceApi.listToDtoUsingPOST(queryService.findAllProduct(storeId, page).getContent());
	}

	@GetMapping("/ticket-lines")
	public ResponseEntity<List<TicketLineDTO>> findAllTicketlines(Integer page, Integer size, ArrayList<String> sort) {
		return ticketLineResourceApi.getAllTicketLinesUsingGET(page, size, sort);
	}

	@GetMapping("/ticket-lines/{saleId}")
	public ResponseEntity<List<TicketLine>> findAllTicketLinesBySaleId(@PathVariable Long saleId) {
		return ResponseEntity.ok().body(queryService.findTicketLinesBySaleId(saleId));
	}

	@GetMapping("/ticket-lines/{id}")
	public ResponseEntity<TicketLineDTO> findOneTicketLines(@PathVariable Long id) {
		return ticketLineResourceApi.getTicketLineUsingGET(id);
	}

	@GetMapping("/products/{id}")
	public ResponseEntity<ProductDTO> findProduct(@PathVariable Long id) {
		return this.productResourceApi.getProductUsingGET(id);
	}

	@GetMapping("/products/export")
	public ResponseEntity<PdfDTO> exportProducts() {
		PdfDTO pdf = new PdfDTO();
		pdf.setPdf(this.productResourceApi.getProductsPriceAsPdfUsingGET().getBody());
		pdf.setContentType("application/pdf");
		return ResponseEntity.ok().body(pdf);
	}

	@GetMapping("/products/pdf")
	public ResponseEntity<byte[]> exportProductsAsPdf() {

		return this.productResourceApi.getProductsPriceAsPdfUsingGET();

	}

	@GetMapping("/customers/export")
	public ResponseEntity<PdfDTO> exportCustomers() {
		PdfDTO pdf = new PdfDTO();
		pdf.setPdf(this.customerResourceApi.getPdfAllCustomersUsingGET().getBody());
		pdf.setContentType("application/pdf");
		return ResponseEntity.ok().body(pdf);
	}

	@GetMapping("/sales/{id}")
	public ResponseEntity<SaleDTO> findSaleById(@PathVariable Long id) {
		return this.saleResourceApi.getSaleUsingGET(id);
	}

	/**/
	@GetMapping("/sales/{storeId}")
	public Page<Sale> findSales(@PathVariable String storeId, Pageable pageable) {
		return queryService.findSales(storeId, pageable);
	}

	@GetMapping("/stocklines/{storeId}")
	public ResponseEntity<List<StockLine>> findAllStockLines(@PathVariable String storeId, Pageable pageable) {
		return ResponseEntity.ok().body(this.queryService.findAllStockLines(storeId, pageable).getContent());
	}

	@GetMapping("/sales/combined/{storeId}")
	public ResponseEntity<Page<SaleAggregate>> findAllSaleAggregates(@PathVariable String storeId, Pageable pageable) {
		List<SaleAggregate> sales = new ArrayList<SaleAggregate>();
		this.findSales(storeId, pageable).getContent().forEach(sale -> {
			SaleAggregate saleAgg = new SaleAggregate();
			saleAgg.setSale(sale);
			sales.add(saleAgg);
		});
		sales.forEach(sale -> {
			sale.setCustomer(this.findCustomerById(sale.getSale().getCustomerId()).getBody());
			sale.setTicketLines(this.findAllTicketLinesBySaleId(sale.getSale().getId()).getBody());
		});
		PageImpl<SaleAggregate> res = new PageImpl<SaleAggregate>(sales);
		return ResponseEntity.ok().body(res);
	}

	@GetMapping("/stockcurrentByStoreId/{storeId}")
	public ResponseEntity<Page<StockCurrent>> getAllStockCurrents(@PathVariable String storeId, Pageable pageable) {
		return ResponseEntity.ok().body(queryService.findAllStockCurrents(storeId, pageable));
	}

	@GetMapping("/stock-currents/{id}")
	public ResponseEntity<StockCurrentDTO> findOneStockCurrent(@PathVariable Long id) {
		return this.stockCurrentResourceApi.getStockCurrentUsingGET(id);
	}

	@GetMapping("/stock-diaries/{storeId}")
	public ResponseEntity<List<StockDiary>> findAllStockDiaries(@PathVariable String storeId, Pageable pageable) {
		return ResponseEntity.ok().body(queryService.findAllStockDiaries(storeId, pageable).getContent());
	}

	@GetMapping("/stock-diaries/{id}")
	public ResponseEntity<StockDiaryDTO> findOneStockDiary(@PathVariable Long id) {
		return this.stockDiaryResourceApi.getStockDiaryUsingGET(id);
	}

	@GetMapping("/stock-current/{searchTerm}")
	public ResponseEntity<List<StockCurrentDTO>> searchStockCurrents(@PathVariable String searchTerm, Integer page,
			Integer size, ArrayList<String> sort) {
		return this.stockCurrentResourceApi.searchStockCurrentsUsingGET(searchTerm, page, size, sort);
	}

	@GetMapping("/stock-diary/{searchTerm}")
	public ResponseEntity<List<StockDiaryDTO>> searchStockDiaries(@PathVariable String searchTerm, Integer page,
			Integer size, ArrayList<String> sort) {
		return this.stockDiaryResourceApi.searchStockDiariesUsingGET(searchTerm, page, size, sort);
	}

	@GetMapping("/reviews")
	public ResponseEntity<List<Review>> findAllReviews(@PathVariable String storeId, Pageable pageable) {
		return ResponseEntity.ok().body(queryService.findAllReviews(storeId, pageable).getContent());
	}

	@GetMapping("/user-ratings")
	public ResponseEntity<List<UserRating>> findAllUserRatings(@PathVariable String storeId, Pageable pageable) {
		return ResponseEntity.ok().body(queryService.findAllUserRatings(storeId, pageable).getContent());
	}

	@GetMapping("/stores/{regNo}")
	public Store findStoreByRegNo(String regNo) {
		return this.queryService.findStoreByRegNo(regNo);
	}

	@GetMapping("/storeBundle/{regNo}")
	public ResponseEntity<StoreBundleDTO> getStoreBundle(@PathVariable String regNo, Integer page, Integer size,
			ArrayList<String> sort) {

		Store store = queryService.findStoreByRegNo(regNo);

		StoreDTO storeDTO = storeResourceApi.getStoreUsingGET(store.getId()).getBody();

		List<DeliveryInfoDTO> deliveryDTOs = deliveryInfoResourceApi
				.listToDtoUsingPOST(queryService.findDeliveryInfoByStoreId(storeDTO.getId()).getContent()).getBody();

		List<TypeDTO> typeDTOs = typeResourceApi.getAllTypesUsingGET(page, size, sort).getBody();

		StoreBundleDTO bundle = new StoreBundleDTO();

		bundle.setStore(storeDTO);

		bundle.setDeliveryInfos(deliveryDTOs);

		bundle.setTypes(typeDTOs);

		return ResponseEntity.ok().body(bundle);

	}

	@PostMapping("/loadProducts")
	public void loadProducts(@RequestParam MultipartFile file) {
		// upload and save the file then load
		log.info("::::::::::::::::::file:::::::::::::::::::::: "+ file);
		loadControllerApi.loadUsingPOST(file);
	}

	@GetMapping("/ordersbystoreId/{storeId}")
	public Page<Order> findOrderLineByStoreId(@PathVariable String storeId, Pageable pageable) {

		return queryService.findOrderByStoreId(storeId, pageable);

	}
}