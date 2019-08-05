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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diviso.graeshoppe.client.customer.api.ContactResourceApi;
import com.diviso.graeshoppe.client.customer.api.CustomerResourceApi;
import com.diviso.graeshoppe.client.customer.domain.Customer;
import com.diviso.graeshoppe.client.customer.model.ContactDTO;
import com.diviso.graeshoppe.client.customer.model.CustomerDTO;
import com.diviso.graeshoppe.client.order.domain.Order;
import com.diviso.graeshoppe.client.product.api.AuxilaryLineItemResourceApi;
import com.diviso.graeshoppe.client.product.api.CategoryResourceApi;
import com.diviso.graeshoppe.client.product.api.ComboLineItemResourceApi;
import com.diviso.graeshoppe.client.product.api.ProductResourceApi;
import com.diviso.graeshoppe.client.product.api.StockCurrentResourceApi;
import com.diviso.graeshoppe.client.product.api.StockEntryResourceApi;
import com.diviso.graeshoppe.client.product.api.UomResourceApi;
import com.diviso.graeshoppe.client.product.model.AuxilaryLineItem;
import com.diviso.graeshoppe.client.product.model.AuxilaryLineItemDTO;
import com.diviso.graeshoppe.client.product.model.Category;
import com.diviso.graeshoppe.client.product.model.CategoryDTO;
import com.diviso.graeshoppe.client.product.model.ComboLineItemDTO;
import com.diviso.graeshoppe.client.product.model.EntryLineItem;
import com.diviso.graeshoppe.client.product.model.Product;
import com.diviso.graeshoppe.client.product.model.ProductDTO;
import com.diviso.graeshoppe.client.product.model.StockCurrent;
import com.diviso.graeshoppe.client.product.model.StockCurrentDTO;
import com.diviso.graeshoppe.client.product.model.StockEntry;
import com.diviso.graeshoppe.client.product.model.StockEntryDTO;
import com.diviso.graeshoppe.client.product.model.UOM;
import com.diviso.graeshoppe.client.product.model.UOMDTO;
import com.diviso.graeshoppe.client.sale.api.SaleResourceApi;
import com.diviso.graeshoppe.client.sale.api.TicketLineResourceApi;
import com.diviso.graeshoppe.client.sale.domain.Sale;
import com.diviso.graeshoppe.client.sale.domain.TicketLine;
import com.diviso.graeshoppe.client.sale.model.SaleDTO;
import com.diviso.graeshoppe.client.sale.model.TicketLineDTO;
import com.diviso.graeshoppe.client.store.api.BannerResourceApi;
import com.diviso.graeshoppe.client.store.api.DeliveryInfoResourceApi;
import com.diviso.graeshoppe.client.store.api.StoreResourceApi;
import com.diviso.graeshoppe.client.store.api.StoreTypeResourceApi;
import com.diviso.graeshoppe.client.store.api.TypeResourceApi;
import com.diviso.graeshoppe.client.store.domain.Store;
import com.diviso.graeshoppe.client.store.domain.Type;
import com.diviso.graeshoppe.client.store.model.BannerDTO;
import com.diviso.graeshoppe.client.store.model.DeliveryInfoDTO;
import com.diviso.graeshoppe.client.store.model.StoreBundleDTO;
import com.diviso.graeshoppe.client.store.model.StoreDTO;
import com.diviso.graeshoppe.client.store.model.StoreTypeDTO;
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
	private StockEntryResourceApi stockEntryResourceApi;

	@Autowired
	private StoreResourceApi storeResourceApi;

	@Autowired
	private DeliveryInfoResourceApi deliveryInfoResourceApi;

	@Autowired
	private TypeResourceApi typeResourceApi;

	@Autowired
	private StoreTypeResourceApi storeTypeResourceApi;

	@Autowired
	private BannerResourceApi bannerResourceApi;

	@Autowired
	ComboLineItemResourceApi comboLineItemResourceApi;
	
	@Autowired
	private AuxilaryLineItemResourceApi auxilaryLineItemResourceApi;

	private final Logger log = LoggerFactory.getLogger(QueryResource.class);

	///////////

	@GetMapping("/findAllProductByCategoryId/{categoryId}/{storeId}")
	public Page<Product> findAllProductsByCategoryId(@PathVariable Long categoryId, @PathVariable String storeId,
			Pageable pageable) {
		return queryService.findProductByCategoryId(categoryId, storeId, pageable);
	}

	@GetMapping("/findProductBySearchTerm/{searchTerm}/{storeId}")
	public Page<Product> findAllProductBySearchTerm(@PathVariable String searchTerm, @PathVariable String storeId,
			Pageable pageable) {
		return queryService.findAllProductBySearchTerm(searchTerm, storeId, pageable);
	}

	@GetMapping("/findAllProducts/{iDPcode}")
	public Page<Product> findAllProducts(@PathVariable String iDPcode, Pageable pageable) {
		return queryService.findAllProducts(iDPcode, pageable);
	}

	@GetMapping("/productByStoreId/{iDPcode}")
	public ResponseEntity<List<ProductDTO>> findAllProduct(@PathVariable String iDPcode, Pageable page) {
		return productResourceApi.listToDtoUsingPOST(queryService.findAllProduct(iDPcode, page).getContent());
	}

	@GetMapping("/products/{id}")
	public ResponseEntity<ProductDTO> findProduct(@PathVariable Long id) {
		return this.productResourceApi.getProductUsingGET(id);
	}

	/*
	 * @GetMapping("/products/export") public ResponseEntity<PdfDTO>
	 * exportProducts() { PdfDTO pdf = new PdfDTO();
	 * pdf.setPdf(this.productResourceApi.getProductsPriceAsPdfUsingGET().
	 * getBody()) ; pdf.setContentType("application/pdf"); return
	 * ResponseEntity.ok().body(pdf); }
	 * 
	 * @GetMapping("/products/pdf") public ResponseEntity<byte[]>
	 * exportProductsAsPdf() {
	 * 
	 * return this.productResourceApi.getProductsPriceAsPdfUsingGET();
	 * 
	 * }
	 */

	///////////

	@GetMapping("/findAllStockCurrentByProductName/{name}/{storeId}")
	public Page<StockCurrent> findAllStockCurrentByProductName(@PathVariable String name, @PathVariable String storeId,
			Pageable pageable) {
		return queryService.findStockCurrentByProductName(name, storeId, pageable);
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

	@GetMapping("/findStockCurrentDTOByProductId/{productId}")
	public ResponseEntity<StockCurrentDTO> findStockCurrentDTOByProductId(@PathVariable Long productId) {
		return this.stockCurrentResourceApi.getStockCurrentByProductIdUsingGET(productId);
	}

	@GetMapping("/stockcurrentByIDPcode/{iDPcode}")
	public ResponseEntity<Page<StockCurrent>> getAllStockCurrentsByIDPcode(@PathVariable String iDPcode,
			Pageable pageable) {
		return ResponseEntity.ok().body(queryService.findAllStockCurrents(iDPcode, pageable));
	}

	@GetMapping("/stock-currents/{id}")
	public ResponseEntity<StockCurrentDTO> findOneStockCurrent(@PathVariable Long id) {
		return this.stockCurrentResourceApi.getStockCurrentUsingGET(id);
	}

	////////////////////////

	@GetMapping("/findAllCustomer/{searchTerm}")
	public Page<Customer> findAllCustomers(@PathVariable String searchTerm, @PathVariable String storeId,
			Pageable pageable) {
		return queryService.findAllCustomers(searchTerm, pageable);
	}

	@GetMapping("/findAllCustomers")
	public Page<Customer> findAllCustomersWithoutSearch(Pageable pageable) {
		return queryService.findAllCustomersWithoutSearch(pageable);
	}

	@GetMapping("/customers/{id}")
	public ResponseEntity<CustomerDTO> findCustomerById(@PathVariable Long id) {
		return this.customerResourceApi.getCustomerUsingGET(id);
	}

	@GetMapping("/contacts/{id}")
	public ResponseEntity<ContactDTO> findContactById(@PathVariable Long id) {
		return this.contactResourceApi.getContactUsingGET(id);
	}

	@GetMapping("/customers/export")
	public ResponseEntity<PdfDTO> exportCustomers() {
		PdfDTO pdf = new PdfDTO();
		pdf.setPdf(this.customerResourceApi.getPdfAllCustomersUsingGET().getBody());
		pdf.setContentType("application/pdf");
		return ResponseEntity.ok().body(pdf);
	}

	/////////////////////

	@GetMapping("/findAllCateogories/{storeId}")
	public ResponseEntity<Page<Category>> findAllCategories(@PathVariable String storeId, Pageable pageable) {

		return ResponseEntity.ok().body(queryService.findAllCategories(storeId, pageable));

	}

	@GetMapping("/findAllCategoriesWithOutImage/{iDPcode}")
	public ResponseEntity<List<CategoryDTO>> findAllCategoriesWithOutImage(@PathVariable String iDPcode,
			Pageable pageable) {
		return ResponseEntity.ok()
				.body(categoryResourceApi
						.listToDToUsingPOST(queryService.findAllCategories(iDPcode, pageable).getContent()).getBody()
						.stream().map(c -> {
							c.setImage(null);
							return c;
						}).collect(Collectors.toList()));
	}

	@PutMapping("/categories")
	public ResponseEntity<CategoryDTO> updateCategory(CategoryDTO categoryDTO) {

		return categoryResourceApi.updateCategoryUsingPUT(categoryDTO);

	}

	////////////////////////////

	@GetMapping("/findStockEntryByProductId/{productId}/{storeId}")
	public ResponseEntity<StockEntry> findStockEntryByProductId(@PathVariable Long productId,
			@PathVariable String storeId) {
		return ResponseEntity.ok().body(queryService.findStockEntryByProductId(productId, storeId));
	}

	///////////////////////////////

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

	//////////////////////////////////

	@GetMapping("/sales/{id}")
	public ResponseEntity<SaleDTO> findSaleById(@PathVariable Long id) {
		return this.saleResourceApi.getSaleUsingGET(id);
	}

	@GetMapping("/sales/{storeId}")
	public Page<Sale> findSales(@PathVariable String storeId, Pageable pageable) {
		return queryService.findSales(storeId, pageable);
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

	//////////////////////////

	@GetMapping("/entryLineItem/{storeId}")
	public ResponseEntity<List<EntryLineItem>> findAllEntryLineItems(@PathVariable String storeId, Pageable pageable) {
		return ResponseEntity.ok().body(this.queryService.findAllEntryLineItems(storeId, pageable).getContent());
	}

	@GetMapping("/stock-entries/{storeId}")
	public ResponseEntity<List<StockEntry>> findAllStockDiaries(@PathVariable String storeId, Pageable pageable) {
		return ResponseEntity.ok().body(queryService.findAllStockEntries(storeId, pageable).getContent());
	}

	@GetMapping("/stock-entries/{id}")
	public ResponseEntity<StockEntryDTO> findOneStockEntry(@PathVariable Long id) {
		return this.stockEntryResourceApi.getStockEntryUsingGET(id);
	}

	@GetMapping("/stock-current/{searchTerm}")
	public ResponseEntity<List<StockCurrentDTO>> searchStockCurrents(@PathVariable String searchTerm, Integer page,
			Integer size, ArrayList<String> sort) {
		return this.stockCurrentResourceApi.searchStockCurrentsUsingGET(searchTerm, page, size, sort);
	}

	///////////////////////////////

	@GetMapping("/stores/{regNo}")
	public Store findStoreByRegNo(@PathVariable String regNo) {
		return this.queryService.findStoreByRegNo(regNo);
	}

	@GetMapping("/storeBundle/{regNo}")
	public ResponseEntity<StoreBundleDTO> getStoreBundle(@PathVariable String regNo, Integer page, Integer size,
			ArrayList<String> sort) {

		Store store = queryService.findStoreByRegNo(regNo);

		StoreDTO storeDTO = storeResourceApi.getStoreUsingGET(store.getId()).getBody();

		List<DeliveryInfoDTO> deliveryDTOs = deliveryInfoResourceApi
				.listToDtoUsingPOST(queryService.findDeliveryInfoByStoreId(storeDTO.getId()).getContent()).getBody();

		List<TypeDTO> typeDTOs = typeResourceApi.listToDtoUsingPOST3(queryService.findAllDeliveryTypesByStoreId(regNo))
				.getBody();

		List<StoreTypeDTO> storeTypeDTO = storeTypeResourceApi
				.listToDtoUsingPOST2(queryService.findAllStoreTypesByStoreId(regNo)).getBody();

		List<BannerDTO> bannerDTO = bannerResourceApi.listToDtoUsingPOST(queryService.findAllBannersByStoreId(regNo))
				.getBody();

		StoreBundleDTO bundle = new StoreBundleDTO();

		bundle.setStore(storeDTO);

		bundle.setDeliveryInfos(deliveryDTOs);

		bundle.setTypes(typeDTOs);

		bundle.setBanners(bannerDTO);

		bundle.setStoreType(storeTypeDTO);

		return ResponseEntity.ok().body(bundle);

	}

	@GetMapping("/ordersbystoreId/{storeId}")
	public Page<Order> findOrderLineByStoreId(@PathVariable String storeId, Pageable pageable) {

		return queryService.findOrderByStoreId(storeId, pageable);

	}

	@GetMapping("/auxilarylineitems/{iDPcode}")
	public ResponseEntity<Page<AuxilaryLineItem>> getAuxilaryLineItemsByStoreId(@PathVariable String iDPcode,
			Pageable pageable) {

		return ResponseEntity.ok().body(queryService.findAuxilaryLineItemsByIDPcode(iDPcode, pageable));

	}

	@GetMapping("/UOM/{iDPcode}")
	public ResponseEntity<Page<UOM>> findUOMByIDPcode(@PathVariable String iDPcode, Pageable pageable) {
		return ResponseEntity.ok().body(queryService.findUOMByIDPcode(iDPcode, pageable));
	}

	@GetMapping("/uom/{id}")
	public ResponseEntity<UOMDTO> findUOM(@PathVariable Long id) {
		return uomResourceApi.getUOMUsingGET(id);
	}

	@GetMapping("/store/{id}")
	public ResponseEntity<StoreDTO> findStore(@PathVariable Long id) {
		return storeResourceApi.getStoreUsingGET(id);
	}

	@GetMapping("/category/{id}")
	public ResponseEntity<CategoryDTO> findCategory(@PathVariable Long id) {
		return categoryResourceApi.getCategoryUsingGET(id);
	}

	@GetMapping("/auxilaryitem/{id}")
	public ResponseEntity<AuxilaryLineItemDTO> findAuxilaryLineItem(@PathVariable Long id) {
		return auxilaryLineItemResourceApi.getAuxilaryLineItemUsingGET(id);
	}
	
	@GetMapping("/combolineitem/{id}")
	public ResponseEntity<ComboLineItemDTO> findCombolineItem(@PathVariable Long id) {
		return comboLineItemResourceApi.getComboLineItemUsingGET(id);
	}

	@GetMapping("/banner/{id}")
	public ResponseEntity<BannerDTO> findBanner(@PathVariable Long id) {
		return bannerResourceApi.getBannerUsingGET(id);
	}
	
	@GetMapping("/not-aux-combo-products/{iDPcode}")
	public ResponseEntity<Page<Product>> getNotAuxNotComboProductsByIDPcode(@PathVariable String iDPcode,
			Pageable pageable) {

		return ResponseEntity.ok().body(queryService.findNotAuxNotComboProductsByIDPcode(iDPcode, pageable));

	}

}
