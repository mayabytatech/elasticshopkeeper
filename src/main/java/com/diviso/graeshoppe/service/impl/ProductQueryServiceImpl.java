package com.diviso.graeshoppe.service.impl;

import static org.elasticsearch.action.search.SearchType.QUERY_THEN_FETCH;
import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.stereotype.Service;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import com.diviso.graeshoppe.client.product.model.Address;
import com.diviso.graeshoppe.client.product.model.AuxilaryLineItem;
import com.diviso.graeshoppe.client.product.model.Category;
import com.diviso.graeshoppe.client.product.model.ComboLineItem;
import com.diviso.graeshoppe.client.product.model.Discount;
import com.diviso.graeshoppe.client.product.model.EntryLineItem;
import com.diviso.graeshoppe.client.product.model.Location;
import com.diviso.graeshoppe.client.product.model.Product;
import com.diviso.graeshoppe.client.product.model.Reason;
import com.diviso.graeshoppe.client.product.model.StockCurrent;
import com.diviso.graeshoppe.client.product.model.StockEntry;
import com.diviso.graeshoppe.client.product.model.UOM;
import com.diviso.graeshoppe.service.ProductQueryService;
import com.diviso.graeshoppe.web.rest.errors.BadRequestAlertException;
import com.github.vanroy.springdata.jest.JestElasticsearchTemplate;
import com.github.vanroy.springdata.jest.aggregation.AggregatedPage;

import io.searchbox.client.JestClient;
import io.searchbox.core.search.aggregation.TermsAggregation;

@Service
public class ProductQueryServiceImpl implements ProductQueryService {

	@Autowired
	ElasticsearchOperations elasticsearchOperations;
	private final Logger log = LoggerFactory.getLogger(QueryServiceImpl.class);

	private final JestClient jestClient;
	private final JestElasticsearchTemplate elasticsearchTemplate;


	public ProductQueryServiceImpl(JestClient jestClient) {
		this.jestClient = jestClient;
		this.elasticsearchTemplate = new JestElasticsearchTemplate(this.jestClient);
	}
	
	@Override
	public Page<Product> findProductByCategoryId(Long categoryId, String storeId, Pageable pageable) {
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
				.withQuery(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("category.id", categoryId))
						.must(QueryBuilders.matchQuery("iDPcode", storeId)))
				.withPageable(pageable).build();
		return elasticsearchOperations.queryForPage(searchQuery, Product.class);
	}
	
	
	@Override
	public Page<Product> findAllProductBySearchTerm(String searchTerm, String storeId, Pageable pageable) {
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
				.withQuery(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("name", searchTerm).prefixLength(3))
						.must(QueryBuilders.matchQuery("iDPcode", storeId)))
				.withPageable(pageable).build();

		return elasticsearchOperations.queryForPage(searchQuery, Product.class);

	}
	
	@Override
	public Page<Product> findAllProducts(String storeId, Pageable pageable) {

		if (findProducts(pageable) == null) {
			throw new BadRequestAlertException("NO products exist", "Product", "no products");
		}
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(termQuery("iDPcode", storeId))
				.withSort(SortBuilders.fieldSort("id").order(SortOrder.DESC)).withPageable(pageable).build();

		return elasticsearchOperations.queryForPage(searchQuery, Product.class);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.diviso.graeshoppe.service.QueryService#findProducts(org.springframework.
	 * data.domain.Pageable)
	 */
	@Override
	public Page<Product> findProducts(Pageable pageable) {
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchAllQuery()).build();

		return elasticsearchOperations.queryForPage(searchQuery, Product.class);
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.diviso.graeshoppe.service.QueryService#
	 * findNotAuxNotComboProductsByIDPcode(java.lang.String,
	 * org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<Product> findNotAuxNotComboProductsByIDPcode(String iDPcode, Pageable pageable) {

		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(termQuery("iDPcode", iDPcode)).build();

		List<Product> products = elasticsearchOperations.queryForList(searchQuery, Product.class);

		List<Product> notAuxNotComboProducts = new ArrayList<Product>();

		products.forEach(p -> {

			if ((p.isIsAuxilaryItem() == false) && (p.getComboLineItems() == null)) {

				notAuxNotComboProducts.add(p);
			}

		});

		return new PageImpl(notAuxNotComboProducts);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.diviso.graeshoppe.service.QueryService#findAllAuxilaryProducts()
	 */
	@Override
	public Page<Product> findAllAuxilaryProducts(String storeId) {
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(termQuery("iDPcode", storeId)).build();

		List<Product> products = elasticsearchOperations.queryForList(searchQuery, Product.class);

		List<Product> auxilaryProducts = new ArrayList<Product>();
		products.forEach(p -> {

			if (p.isIsAuxilaryItem().equals(true)) {
				auxilaryProducts.add(p);
			}
		});

		return new PageImpl(auxilaryProducts);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.diviso.graeshoppe.service.QueryService#findProductById(java.lang.Long)
	 */
	@Override
	public Product findProductById(Long id) {
		StringQuery stringQuery = new StringQuery(termQuery("id", id).toString());
		return elasticsearchOperations.queryForObject(stringQuery, Product.class);
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.diviso.graeshoppe.service.QueryService#findAllCategories(org.
	 * springframework.data.domain.Pageable)
	 */
	@Override
	public Page<Category> findAllCategories(Pageable pageable) {
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchAllQuery()).build();
		return elasticsearchOperations.queryForPage(searchQuery, Category.class);

	}
	
	
	@Override
	public List<String> findAllUom(Pageable pageable) {
		List<String> uomList = new ArrayList<String>();
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchAllQuery())
				.withSearchType(QUERY_THEN_FETCH).withIndices("uom").withTypes("uom")
				.addAggregation(AggregationBuilders.terms("distinct_uom").field("name.keyword")).build();

		AggregatedPage<UOM> result = elasticsearchTemplate.queryForPage(searchQuery, UOM.class);
		TermsAggregation uomAgg = result.getAggregation("distinct_uom", TermsAggregation.class);

		System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<" + uomAgg.getBuckets().size());

		for (int i = 0; i < uomAgg.getBuckets().size(); i++) {
			uomList.add(uomAgg.getBuckets().get(i).getKey());
		}

		return uomList;
	}
	
	@Override
	public Page<EntryLineItem> findAllEntryLineItems(String storeId, Pageable pageable) {
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(termQuery("product.iDPcode", storeId))
				.build();
		return elasticsearchOperations.queryForPage(searchQuery, EntryLineItem.class);
	}
	
	
	@Override
	public Page<StockCurrent> findAllStockCurrents(String storeId, Pageable pageable) {

		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(termQuery("iDPcode", storeId)).build();

		return elasticsearchOperations.queryForPage(searchQuery, StockCurrent.class);
	}
	
	
	@Override
	public Page<StockEntry> findAllStockEntries(String storeId, Pageable pageable) {
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(termQuery("iDPcode", storeId)).withPageable(pageable).
				build();
		return elasticsearchOperations.queryForPage(searchQuery, StockEntry.class);
	}
	
	
	@Override
	public Page<StockCurrent> findAllStockCurrentByCategoryId(Long categoryId, String storeId, Pageable pageable) {
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
				.withQuery(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("product.category.id", categoryId))
						.must(QueryBuilders.matchQuery("iDPcode", storeId)))
				.build();
		return elasticsearchOperations.queryForPage(searchQuery, StockCurrent.class);
	}
	
	
	@Override
	public StockCurrent findStockCurrentByProductId(Long productId, String storeId) {
		StringQuery stringQuery = new StringQuery(
				QueryBuilders.boolQuery().must(QueryBuilders.termQuery("product.id", productId))
						.must(QueryBuilders.termQuery("product.iDPcode", storeId)).toString());
		return elasticsearchOperations.queryForObject(stringQuery, StockCurrent.class);
	}
	
	
	@Override
	public StockEntry findStockEntryByProductId(Long productId, String storeId) {
		StringQuery stringQuery = new StringQuery(
				QueryBuilders.boolQuery().must(QueryBuilders.termQuery("product.id", productId))
						.must(QueryBuilders.termQuery("product.userId", storeId)).toString());
		return elasticsearchOperations.queryForObject(stringQuery, StockEntry.class);
	}
	
	
	@Override
	public Page<StockCurrent> findStockCurrentByProductName(String name, String storeId, Pageable pageable) {
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
				.withQuery(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("product.name", name))
						.must(QueryBuilders.matchQuery("product.iDPcode", storeId)))
				.build();

		return new PageImpl(elasticsearchOperations.queryForPage(searchQuery, StockCurrent.class).stream()
				.filter(stockcurrent -> (stockcurrent.getProduct().isIsAuxilaryItem() == false))
				.collect(Collectors.toList()));
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.diviso.graeshoppe.service.QueryService#findAuxilaryLineItemsByStoreId
	 * (java.lang.String, org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<AuxilaryLineItem> findAuxilaryLineItemsByIDPcode(String iDPcode, Pageable pageable) {

		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(termQuery("product.iDPcode", iDPcode))
				.build();

		return elasticsearchOperations.queryForPage(searchQuery, AuxilaryLineItem.class);
	}

	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.diviso.graeshoppe.service.QueryService#findUOMByStoreId(java.lang.
	 * String, org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<UOM> findUOMByIDPcode(String iDPcode, Pageable pageable) {

		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(termQuery("iDPcode", iDPcode)).build();

		return elasticsearchOperations.queryForPage(searchQuery, UOM.class);
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.diviso.graeshoppe.service.QueryService#findCategoryById(java.lang.Long)
	 */
	@Override
	public Category findCategoryById(Long id) {
		StringQuery stringQuery = new StringQuery(termQuery("id", id).toString());
		return elasticsearchOperations.queryForObject(stringQuery, Category.class);
	}

	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.diviso.graeshoppe.service.QueryService#findUOMById(java.lang.Long)
	 */
	@Override
	public UOM findUOMById(Long id) {
		StringQuery stringQuery = new StringQuery(termQuery("id", id).toString());
		return elasticsearchOperations.queryForObject(stringQuery, UOM.class);
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.diviso.graeshoppe.service.QueryService#finAllComboLineItemsByProductId(
	 * java.lang.Long)
	 */
	@Override
	public List<ComboLineItem> finAllComboLineItemsByProductId(Long id) {
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(termQuery("product.id", id)).build();

		return elasticsearchOperations.queryForList(searchQuery, ComboLineItem.class);
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.diviso.graeshoppe.service.QueryService#findAllAuxilaryProductsByProductId
	 * ()
	 */
	@Override
	public List<AuxilaryLineItem> findAllAuxilaryProductsByProductId(Long productId) {

		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(termQuery("product.id", productId)).build();

		return elasticsearchOperations.queryForList(searchQuery, AuxilaryLineItem.class);
	}
	
	
	@Override
	public StockEntry findStockEntryById(Long id) {

		StringQuery stringQuery = new StringQuery(termQuery("id", id).toString());
		return elasticsearchOperations.queryForObject(stringQuery, StockEntry.class);
	}
	
	
	@Override
	public Discount findDiscountByProductId(Long productId) {
		StringQuery searchQuery = new StringQuery(termQuery("id", productId).toString());
		Product product=elasticsearchOperations.queryForObject(searchQuery, Product.class);
		return product.getDiscount();
	}
	
	
	@Override
	public List<EntryLineItem> findAllEntryLineItemsByStockEntryId(Long id) {


		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(termQuery("stockentry.id", id)).build();

		return elasticsearchOperations.queryForList(searchQuery, EntryLineItem.class);
	}
	
	
	@Override
	public Reason findReasonByStockEntryId(Long id) {
		StringQuery stringQuery = new StringQuery(termQuery("id", id).toString());
		StockEntry stockentry= elasticsearchOperations.queryForObject(stringQuery, StockEntry.class);
		return stockentry.getReason();
	}

	
	@Override
	public Location findLocationByStockEntryId(Long id) {
		StringQuery stringQuery = new StringQuery(termQuery("id", id).toString());
		StockEntry stockentry= elasticsearchOperations.queryForObject(stringQuery, StockEntry.class);
		return stockentry.getLocation();
	}
	
	
	@Override
	public Page<Location> findLocationByIdpcode(String idpcode, Pageable pageable) {
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(termQuery("iDPcode", idpcode)).withPageable(pageable).build();

		return elasticsearchOperations.queryForPage(searchQuery, Location.class);
	
	}

	
	@Override
	public Page<Reason> findReasonByIdpcode(String idpcode, Pageable pageable) {
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(termQuery("iDPcode", idpcode)).withPageable(pageable).build();

		return elasticsearchOperations.queryForPage(searchQuery, Reason.class);
	
	}
	
	
	@Override
	public Page<EntryLineItem> findAllEntryLineItemsByStockEntryId(String id, Pageable pageable) {
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(termQuery("stockentry.id", id)).withPageable(pageable).build();

		return elasticsearchOperations.queryForPage(searchQuery, EntryLineItem.class);
		
	}

	
	@Override
	public Address findAddressByStockEntryId(Long id) {
		StringQuery stringQuery = new StringQuery(termQuery("id", id).toString());
		Location location= elasticsearchOperations.queryForObject(stringQuery, Location.class);
		
		return location.getAddress();
		
		
	}

	@Override
	public Page<Category> findAllCategoryBySearchTerm(String searchTerm, String storeId, Pageable pageable) {
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
				.withQuery(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("name", searchTerm).prefixLength(3))
						.must(QueryBuilders.matchQuery("iDPcode", storeId)))
				.withPageable(pageable).build();

		return elasticsearchOperations.queryForPage(searchQuery, Category.class);

	}
	
	@Override
	public Page<Category> findAllCategories(String iDPcode, Pageable pageable) {
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
				.withQuery(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("iDPcode", iDPcode))).build();
		return elasticsearchOperations.queryForPage(searchQuery, Category.class);
	}

}

