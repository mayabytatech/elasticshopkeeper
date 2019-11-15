package com.diviso.graeshoppe.service.impl;

import static org.elasticsearch.action.search.SearchType.QUERY_THEN_FETCH;
import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.util.PageCacheRecycler;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.stereotype.Service;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import com.diviso.graeshoppe.client.order.model.Order;
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
import com.diviso.graeshoppe.web.rest.util.ServiceUtility;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ProductQueryServiceImpl implements ProductQueryService {

	private ServiceUtility serviceUtility = new ServiceUtility();

	private RestHighLevelClient restHighLevelClient;

	private ObjectMapper objectMapper;

	public ProductQueryServiceImpl(ObjectMapper objectMapper, RestHighLevelClient restHighLevelClient) {
		this.objectMapper = objectMapper;
		this.restHighLevelClient = restHighLevelClient;
	}

	@Override
	public Page<Product> findProductByCategoryId(Long categoryId, String storeId, Pageable pageable) {

		SearchSourceBuilder builder = new SearchSourceBuilder();

		/*
		 * String[] include = new String[] { "" };
		 * 
		 * String[] exclude = new String[] {};
		 * 
		 * builder.fetchSource(include, exclude);
		 */

		builder.query(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("category.id", categoryId))
				.must(QueryBuilders.matchQuery("iDPcode", storeId)));

		SearchRequest searchRequest = serviceUtility.generateSearchRequest("product", pageable.getPageSize(),
				pageable.getPageNumber(), builder);

		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}
		return serviceUtility.getSearchResults(searchResponse, pageable, new Product(), objectMapper);
	}

	@Override
	public Page<Product> findAllProductBySearchTerm(String searchTerm, String storeId, Pageable pageable) {

		SearchSourceBuilder builder = new SearchSourceBuilder();

		/*
		 * String[] include = new String[] { "" };
		 * 
		 * String[] exclude = new String[] {};
		 * 
		 * builder.fetchSource(include, exclude);
		 */

		builder.query(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("name", searchTerm).prefixLength(3))
				.must(QueryBuilders.matchQuery("iDPcode", storeId)));

		SearchRequest searchRequest = serviceUtility.generateSearchRequest("product", pageable.getPageSize(),
				pageable.getPageNumber(), builder);

		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}
		return serviceUtility.getSearchResults(searchResponse, pageable, new Product(), objectMapper);

	}

	@Override
	public Page<Product> findAllProducts(String storeId, Pageable pageable) {

		if (findProducts(pageable) == null) {
			throw new BadRequestAlertException("NO products exist", "Product", "no products");
		}

		SearchSourceBuilder builder = new SearchSourceBuilder();

		/*
		 * String[] include = new String[] { "" };
		 * 
		 * String[] exclude = new String[] {};
		 * 
		 * builder.fetchSource(include, exclude);
		 */

		builder.query(termQuery("iDPcode", storeId)).sort("id", SortOrder.DESC);

		SearchRequest searchRequest = serviceUtility.generateSearchRequest("product", pageable.getPageSize(),
				pageable.getPageNumber(), builder);

		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}
		return serviceUtility.getSearchResults(searchResponse, pageable, new Product(), objectMapper);

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
		SearchSourceBuilder builder = new SearchSourceBuilder();

		/*
		 * String[] include = new String[] { "" };
		 * 
		 * String[] exclude = new String[] {};
		 * 
		 * builder.fetchSource(include, exclude);
		 */

		builder.query(matchAllQuery());

		SearchRequest searchRequest = serviceUtility.generateSearchRequest("product", pageable.getPageSize(),
				pageable.getPageNumber(), builder);

		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}
		return serviceUtility.getSearchResults(searchResponse, pageable, new Product(), objectMapper);

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

		SearchSourceBuilder builder = new SearchSourceBuilder();

		/*
		 * String[] include = new String[] { "" };
		 * 
		 * String[] exclude = new String[] {};
		 * 
		 * builder.fetchSource(include, exclude);
		 */

		builder.query(termQuery("iDPcode", iDPcode));

		SearchRequest searchRequest = serviceUtility.generateSearchRequest("product", pageable.getPageSize(),
				pageable.getPageNumber(), builder);

		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}

		List<Product> products = serviceUtility.getSearchResults(searchResponse, pageable, new Product(), objectMapper)
				.getContent();

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

		SearchSourceBuilder builder = new SearchSourceBuilder();

		/*
		 * String[] include = new String[] { "" };
		 * 
		 * String[] exclude = new String[] {};
		 * 
		 * builder.fetchSource(include, exclude);
		 */

		builder.query(termQuery("iDPcode", storeId));

		Pageable pageable = PageRequest.of(2, 20);

		SearchRequest searchRequest = serviceUtility.generateSearchRequest("product", pageable.getPageSize(),
				pageable.getPageNumber(), builder);

		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}

		List<Product> products = serviceUtility.getSearchResults(searchResponse, pageable, new Product(), objectMapper)
				.getContent();

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
		SearchSourceBuilder builder = new SearchSourceBuilder();

		/*
		 * String[] include = new String[] { "" };
		 * 
		 * String[] exclude = new String[] {};
		 * 
		 * builder.fetchSource(include, exclude);
		 */

		builder.query(termQuery("id", id));

		SearchRequest searchRequest = new SearchRequest("product");

		searchRequest.source(builder);
		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}

		return serviceUtility.getSearchResult(searchResponse, new Product(), objectMapper);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.diviso.graeshoppe.service.QueryService#findAllCategories(org.
	 * springframework.data.domain.Pageable)
	 */
	@Override
	public Page<Category> findAllCategories(Pageable pageable) {
		SearchSourceBuilder builder = new SearchSourceBuilder();

		/*
		 * String[] include = new String[] { "" };
		 * 
		 * String[] exclude = new String[] {};
		 * 
		 * builder.fetchSource(include, exclude);
		 */

		builder.query(matchAllQuery());

		SearchRequest searchRequest = serviceUtility.generateSearchRequest("category", pageable.getPageSize(),
				pageable.getPageNumber(), builder);

		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}

		return serviceUtility.getSearchResults(searchResponse, pageable, new Category(), objectMapper);

	}

	@Override
	public List<String> findAllUom(Pageable pageable) {
		List<String> uomList = new ArrayList<String>();
		/*
		 * SearchQuery searchQuery = new
		 * NativeSearchQueryBuilder().withQuery(matchAllQuery())
		 * .withSearchType(QUERY_THEN_FETCH).withIndices("uom").withTypes("uom")
		 * .addAggregation(AggregationBuilders.terms("distinct_uom").field(
		 * "name.keyword")).build();
		 * 
		 * AggregatedPage<UOM> result = elasticsearchTemplate.queryForPage(searchQuery,
		 * UOM.class); TermsAggregation uomAgg = result.getAggregation("distinct_uom",
		 * TermsAggregation.class);
		 * 
		 * System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<" +
		 * uomAgg.getBuckets().size());
		 * 
		 * for (int i = 0; i < uomAgg.getBuckets().size(); i++) {
		 * uomList.add(uomAgg.getBuckets().get(i).getKey()); }
		 */
		return uomList;
	}

	@Override
	public Page<EntryLineItem> findAllEntryLineItems(String storeId, Pageable pageable) {
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(termQuery("product.iDPcode", storeId))
				.build();
		SearchSourceBuilder builder = new SearchSourceBuilder();

		/*
		 * String[] include = new String[] { "" };
		 * 
		 * String[] exclude = new String[] {};
		 * 
		 * builder.fetchSource(include, exclude);
		 */

		builder.query(termQuery("product.iDPcode", storeId));

		SearchRequest searchRequest = serviceUtility.generateSearchRequest("entrylineitem", pageable.getPageSize(),
				pageable.getPageNumber(), builder);

		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}

		return serviceUtility.getSearchResults(searchResponse, pageable, new EntryLineItem(), objectMapper);

	}

	@Override
	public Page<StockCurrent> findAllStockCurrents(String storeId, Pageable pageable) {
		SearchSourceBuilder builder = new SearchSourceBuilder();

		/*
		 * String[] include = new String[] { "" };
		 * 
		 * String[] exclude = new String[] {};
		 * 
		 * builder.fetchSource(include, exclude);
		 */

		builder.query(termQuery("iDPcode", storeId));

		SearchRequest searchRequest = serviceUtility.generateSearchRequest("stockcurrent", pageable.getPageSize(),
				pageable.getPageNumber(), builder);

		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}

		return serviceUtility.getSearchResults(searchResponse, pageable, new StockCurrent(), objectMapper);

	}

	@Override
	public Page<StockEntry> findAllStockEntries(String storeId, Pageable pageable) {

		SearchSourceBuilder builder = new SearchSourceBuilder();

		/*
		 * String[] include = new String[] { "" };
		 * 
		 * String[] exclude = new String[] {};
		 * 
		 * builder.fetchSource(include, exclude);
		 */

		builder.query(termQuery("iDPcode", storeId));

		SearchRequest searchRequest = serviceUtility.generateSearchRequest("stockentry", pageable.getPageSize(),
				pageable.getPageNumber(), builder);

		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}

		return serviceUtility.getSearchResults(searchResponse, pageable, new StockEntry(), objectMapper);
	}

	@Override
	public Page<StockCurrent> findAllStockCurrentByCategoryId(Long categoryId, String storeId, Pageable pageable) {

		SearchSourceBuilder builder = new SearchSourceBuilder();

		/*
		 * String[] include = new String[] { "" };
		 * 
		 * String[] exclude = new String[] {};
		 * 
		 * builder.fetchSource(include, exclude);
		 */

		builder.query(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("product.category.id", categoryId))
				.must(QueryBuilders.matchQuery("iDPcode", storeId)));

		SearchRequest searchRequest = serviceUtility.generateSearchRequest("stockcurrent", pageable.getPageSize(),
				pageable.getPageNumber(), builder);

		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}

		return serviceUtility.getSearchResults(searchResponse, pageable, new StockCurrent(), objectMapper);
	}

	@Override
	public StockCurrent findStockCurrentByProductId(Long productId, String storeId) {

		SearchSourceBuilder builder = new SearchSourceBuilder();

		/*
		 * String[] include = new String[] { "" };
		 * 
		 * String[] exclude = new String[] {};
		 * 
		 * builder.fetchSource(include, exclude);
		 */

		builder.query(QueryBuilders.boolQuery().must(QueryBuilders.termQuery("product.id", productId))
				.must(QueryBuilders.termQuery("product.iDPcode", storeId)));

		SearchRequest searchRequest = new SearchRequest("stockcurrent");

		searchRequest.source(builder);
		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}

		return serviceUtility.getSearchResult(searchResponse, new StockCurrent(), objectMapper);
	}

	@Override
	public StockEntry findStockEntryByProductId(Long productId, String storeId) {

		SearchSourceBuilder builder = new SearchSourceBuilder();

		/*
		 * String[] include = new String[] { "" };
		 * 
		 * String[] exclude = new String[] {};
		 * 
		 * builder.fetchSource(include, exclude);
		 */

		builder.query(QueryBuilders.boolQuery().must(QueryBuilders.termQuery("product.id", productId))
				.must(QueryBuilders.termQuery("product.userId", storeId)));

		SearchRequest searchRequest = new SearchRequest("stockentry");

		searchRequest.source(builder);
		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}

		return serviceUtility.getSearchResult(searchResponse, new StockEntry(), objectMapper);
	}

	@Override
	public Page<StockCurrent> findStockCurrentByProductName(String name, String storeId, Pageable pageable) {

		SearchSourceBuilder builder = new SearchSourceBuilder();

		/*
		 * String[] include = new String[] { "" };
		 * 
		 * String[] exclude = new String[] {};
		 * 
		 * builder.fetchSource(include, exclude);
		 */

		builder.query(QueryBuilders.boolQuery()
				.must(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("product.name", name))
						.must(QueryBuilders.matchQuery("product.iDPcode", storeId))));

		SearchRequest searchRequest = serviceUtility.generateSearchRequest("stockcurrent", pageable.getPageSize(),
				pageable.getPageNumber(), builder);

		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}

		Page<StockCurrent> stockCurrentpage = serviceUtility.getSearchResults(searchResponse, pageable,
				new StockCurrent(), objectMapper);

		List<StockCurrent> stockList = stockCurrentpage.stream()
				.filter(stockcurrent -> (stockcurrent.getProduct().isIsAuxilaryItem() == false))
				.collect(Collectors.toList());

		return new PageImpl(stockList);
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

		SearchSourceBuilder builder = new SearchSourceBuilder();

		/*
		 * String[] include = new String[] { "" };
		 * 
		 * String[] exclude = new String[] {};
		 * 
		 * builder.fetchSource(include, exclude);
		 */

		builder.query(termQuery("product.iDPcode", iDPcode));

		SearchRequest searchRequest = serviceUtility.generateSearchRequest("auxilarylineitem", pageable.getPageSize(),
				pageable.getPageNumber(), builder);

		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}

		return serviceUtility.getSearchResults(searchResponse, pageable, new AuxilaryLineItem(), objectMapper);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.diviso.graeshoppe.service.QueryService#findUOMByStoreId(java.lang.
	 * String, org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<UOM> findUOMByIDPcode(String iDPcode, Pageable pageable) {
		SearchSourceBuilder builder = new SearchSourceBuilder();

		/*
		 * String[] include = new String[] { "" };
		 * 
		 * String[] exclude = new String[] {};
		 * 
		 * builder.fetchSource(include, exclude);
		 */

		builder.query(termQuery("iDPcode", iDPcode));

		SearchRequest searchRequest = serviceUtility.generateSearchRequest("uom", pageable.getPageSize(),
				pageable.getPageNumber(), builder);

		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}

		return serviceUtility.getSearchResults(searchResponse, pageable, new UOM(), objectMapper);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.diviso.graeshoppe.service.QueryService#findCategoryById(java.lang.Long)
	 */
	@Override
	public Category findCategoryById(Long id) {
		SearchSourceBuilder builder = new SearchSourceBuilder();

		/*
		 * String[] include = new String[] { "" };
		 * 
		 * String[] exclude = new String[] {};
		 * 
		 * builder.fetchSource(include, exclude);
		 */

		builder.query(termQuery("id", id));

		SearchRequest searchRequest = new SearchRequest("category");

		searchRequest.source(builder);
		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}

		return serviceUtility.getSearchResult(searchResponse, new Category(), objectMapper);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.diviso.graeshoppe.service.QueryService#findUOMById(java.lang.Long)
	 */
	@Override
	public UOM findUOMById(Long id) {

		SearchSourceBuilder builder = new SearchSourceBuilder();

		/*
		 * String[] include = new String[] { "" };
		 * 
		 * String[] exclude = new String[] {};
		 * 
		 * builder.fetchSource(include, exclude);
		 */

		builder.query(termQuery("id", id));

		SearchRequest searchRequest = new SearchRequest("uom");

		searchRequest.source(builder);
		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}

		return serviceUtility.getSearchResult(searchResponse, new UOM(), objectMapper);
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

		SearchSourceBuilder builder = new SearchSourceBuilder();

		/*
		 * String[] include = new String[] { "" };
		 * 
		 * String[] exclude = new String[] {};
		 * 
		 * builder.fetchSource(include, exclude);
		 */

		builder.query(termQuery("product.id", id));

		Pageable pageable = PageRequest.of(2, 20);
		SearchRequest searchRequest = serviceUtility.generateSearchRequest("combolineitem", pageable.getPageSize(),
				pageable.getPageNumber(), builder);

		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}

		return serviceUtility.getSearchResults(searchResponse, pageable, new ComboLineItem(), objectMapper)
				.getContent();
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

		SearchSourceBuilder builder = new SearchSourceBuilder();

		/*
		 * String[] include = new String[] { "" };
		 * 
		 * String[] exclude = new String[] {};
		 * 
		 * builder.fetchSource(include, exclude);
		 */

		builder.query(termQuery("product.id", productId));

		Pageable pageable = PageRequest.of(2, 20);
		SearchRequest searchRequest = serviceUtility.generateSearchRequest("auxilarylineitem", pageable.getPageSize(),
				pageable.getPageNumber(), builder);

		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}

		return serviceUtility.getSearchResults(searchResponse, pageable, new AuxilaryLineItem(), objectMapper)
				.getContent();

	}

	@Override
	public StockEntry findStockEntryById(Long id) {

		SearchSourceBuilder builder = new SearchSourceBuilder();

		/*
		 * String[] include = new String[] { "" };
		 * 
		 * String[] exclude = new String[] {};
		 * 
		 * builder.fetchSource(include, exclude);
		 */

		builder.query(termQuery("id", id));

		SearchRequest searchRequest = new SearchRequest("stockentry");

		searchRequest.source(builder);
		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}

		return serviceUtility.getSearchResult(searchResponse, new StockEntry(), objectMapper);

	}

	@Override
	public Discount findDiscountByProductId(Long productId) {

		SearchSourceBuilder builder = new SearchSourceBuilder();

		/*
		 * String[] include = new String[] { "" };
		 * 
		 * String[] exclude = new String[] {};
		 * 
		 * builder.fetchSource(include, exclude);
		 */

		builder.query(termQuery("id", productId));

		SearchRequest searchRequest = new SearchRequest("discount");

		searchRequest.source(builder);
		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}

		return serviceUtility.getSearchResult(searchResponse, new Discount(), objectMapper);

	}

	@Override
	public List<EntryLineItem> findAllEntryLineItemsByStockEntryId(Long id) {

		SearchSourceBuilder builder = new SearchSourceBuilder();

		/*
		 * String[] include = new String[] { "" };
		 * 
		 * String[] exclude = new String[] {};
		 * 
		 * builder.fetchSource(include, exclude);
		 */

		builder.query(termQuery("stockentry.id", id));

		Pageable pageable = PageRequest.of(2, 20);
		SearchRequest searchRequest = serviceUtility.generateSearchRequest("entrylineitem", pageable.getPageSize(),
				pageable.getPageNumber(), builder);

		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}

		return serviceUtility.getSearchResults(searchResponse, pageable, new EntryLineItem(), objectMapper)
				.getContent();

	}

	@Override
	public Reason findReasonByStockEntryId(Long id) {

		SearchSourceBuilder builder = new SearchSourceBuilder();

		/*
		 * String[] include = new String[] { "" };
		 * 
		 * String[] exclude = new String[] {};
		 * 
		 * builder.fetchSource(include, exclude);
		 */

		builder.query(termQuery("id", id));

		SearchRequest searchRequest = new SearchRequest("reason");

		searchRequest.source(builder);
		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}

		return serviceUtility.getSearchResult(searchResponse, new Reason(), objectMapper);

	}

	@Override
	public Location findLocationByStockEntryId(Long id) {

		SearchSourceBuilder builder = new SearchSourceBuilder();

		/*
		 * String[] include = new String[] { "" };
		 * 
		 * String[] exclude = new String[] {};
		 * 
		 * builder.fetchSource(include, exclude);
		 */

		builder.query(termQuery("id", id));

		SearchRequest searchRequest = new SearchRequest("stockentry");

		searchRequest.source(builder);
		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}

		StockEntry stockentry = serviceUtility.getSearchResult(searchResponse, new StockEntry(), objectMapper);

		return stockentry.getLocation();
	}

	@Override
	public Page<Location> findLocationByIdpcode(String idpcode, Pageable pageable) {

		SearchSourceBuilder builder = new SearchSourceBuilder();

		/*
		 * String[] include = new String[] { "" };
		 * 
		 * String[] exclude = new String[] {};
		 * 
		 * builder.fetchSource(include, exclude);
		 */

		builder.query(termQuery("iDPcode", idpcode));

		SearchRequest searchRequest = serviceUtility.generateSearchRequest("location", pageable.getPageSize(),
				pageable.getPageNumber(), builder);

		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}

		return serviceUtility.getSearchResults(searchResponse, pageable, new Location(), objectMapper);

	}

	@Override
	public Page<Reason> findReasonByIdpcode(String idpcode, Pageable pageable) {
	
		SearchSourceBuilder builder = new SearchSourceBuilder();

		/*
		 * String[] include = new String[] { "" };
		 * 
		 * String[] exclude = new String[] {};
		 * 
		 * builder.fetchSource(include, exclude);
		 */

		builder.query(termQuery("iDPcode", idpcode));

		SearchRequest searchRequest = serviceUtility.generateSearchRequest("reason", pageable.getPageSize(),
				pageable.getPageNumber(), builder);

		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}

		return serviceUtility.getSearchResults(searchResponse, pageable, new Reason(), objectMapper);


	}

	@Override
	public Page<EntryLineItem> findAllEntryLineItemsByStockEntryId(String id, Pageable pageable) {
	
		SearchSourceBuilder builder = new SearchSourceBuilder();

		/*
		 * String[] include = new String[] { "" };
		 * 
		 * String[] exclude = new String[] {};
		 * 
		 * builder.fetchSource(include, exclude);
		 */

		builder.query(termQuery("stockentry.id", id));

		SearchRequest searchRequest = serviceUtility.generateSearchRequest("entrylineitem", pageable.getPageSize(),
				pageable.getPageNumber(), builder);

		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}

		return serviceUtility.getSearchResults(searchResponse, pageable, new EntryLineItem(), objectMapper);

	}

	@Override
	public Address findAddressByStockEntryId(Long id) {


		SearchSourceBuilder builder = new SearchSourceBuilder();

		/*
		 * String[] include = new String[] { "" };
		 * 
		 * String[] exclude = new String[] {};
		 * 
		 * builder.fetchSource(include, exclude);
		 */

		builder.query(termQuery("id", id));

		SearchRequest searchRequest = new SearchRequest("location");

		searchRequest.source(builder);
		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}

		Location location = serviceUtility.getSearchResult(searchResponse, new Location(), objectMapper);

		return location.getAddress();

	}

	@Override
	public Page<Category> findAllCategoryBySearchTerm(String searchTerm, String storeId, Pageable pageable) {
	

		SearchSourceBuilder builder = new SearchSourceBuilder();

		/*
		 * String[] include = new String[] { "" };
		 * 
		 * String[] exclude = new String[] {};
		 * 
		 * builder.fetchSource(include, exclude);
		 */

		builder.query(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("name", searchTerm).prefixLength(3))
				.must(QueryBuilders.matchQuery("iDPcode", storeId)));

		SearchRequest searchRequest = serviceUtility.generateSearchRequest("category", pageable.getPageSize(),
				pageable.getPageNumber(), builder);

		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}

		return serviceUtility.getSearchResults(searchResponse, pageable, new Category(), objectMapper);

	}

	@Override
	public Page<Category> findAllCategories(String iDPcode, Pageable pageable) {

		SearchSourceBuilder builder = new SearchSourceBuilder();

		/*
		 * String[] include = new String[] { "" };
		 * 
		 * String[] exclude = new String[] {};
		 * 
		 * builder.fetchSource(include, exclude);
		 */

		builder.query(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("iDPcode", iDPcode)));

		SearchRequest searchRequest = serviceUtility.generateSearchRequest("category", pageable.getPageSize(),
				pageable.getPageNumber(), builder);

		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}

		return serviceUtility.getSearchResults(searchResponse, pageable, new Category(), objectMapper);

	}

}
