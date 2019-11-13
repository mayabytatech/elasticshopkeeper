package com.diviso.graeshoppe.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.termQuery;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.stereotype.Service;

import com.diviso.graeshoppe.client.store.domain.Banner;
import com.diviso.graeshoppe.client.store.domain.DeliveryInfo;
import com.diviso.graeshoppe.client.store.domain.Review;
import com.diviso.graeshoppe.client.store.domain.Store;
import com.diviso.graeshoppe.client.store.domain.StoreType;
import com.diviso.graeshoppe.client.store.domain.Type;
import com.diviso.graeshoppe.client.store.domain.UserRating;
import com.diviso.graeshoppe.service.StoreQueryService;
import com.github.vanroy.springdata.jest.JestElasticsearchTemplate;

import io.searchbox.client.JestClient;

@Service
public class StoreQueryServiceImpl implements StoreQueryService {

	
	@Autowired
	ElasticsearchOperations elasticsearchOperations;
	private final Logger log = LoggerFactory.getLogger(QueryServiceImpl.class);

	private final JestClient jestClient;
	private final JestElasticsearchTemplate elasticsearchTemplate;


	public StoreQueryServiceImpl(JestClient jestClient) {
		this.jestClient = jestClient;
		this.elasticsearchTemplate = new JestElasticsearchTemplate(this.jestClient);
	}
	
	
	
	@Override
	public Page<Review> findAllReviews(String storeId, Pageable pageable) {
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(termQuery("store.regNo", storeId)).build();
		return elasticsearchOperations.queryForPage(searchQuery, Review.class);
	}
	
	@Override
	public Page<UserRating> findAllUserRatings(String storeId, Pageable pageable) {
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(termQuery("store.regNo", storeId)).build();
		return elasticsearchOperations.queryForPage(searchQuery, UserRating.class);
	}
	
	
	@Override
	public Store findStoreByRegNo(String regNo) {
		StringQuery stringQuery = new StringQuery(termQuery("regNo", regNo).toString());

		return elasticsearchOperations.queryForObject(stringQuery, Store.class);
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.diviso.graeshoppe.service.QueryService#findDeliveryInfoByStoreId(java
	 * .lang.Long)
	 */
	@Override
	public Page<DeliveryInfo> findDeliveryInfoByStoreId(Long id) {
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(termQuery("store.id", id)).build();
		return elasticsearchOperations.queryForPage(searchQuery, DeliveryInfo.class);

	}

	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.diviso.graeshoppe.service.QueryService#findAllDeliveryTypesByStoreId(java
	 * .lang.Long, org.springframework.data.domain.Pageable)
	 */
	@Override
	public List<Type> findAllDeliveryTypesByStoreId(String storeId) {

		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(termQuery("store.regNo", storeId)).build();

		Page<DeliveryInfo> deliveryinfos = elasticsearchOperations.queryForPage(searchQuery, DeliveryInfo.class);

		List<Type> types = new ArrayList<Type>();

		deliveryinfos.forEach(deliveryInfo -> {
			types.add(deliveryInfo.getType());

		});

		return types;

	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.diviso.graeshoppe.service.QueryService#findAllStoreTypesByStoreId(
	 * java.lang.String)
	 */
	@Override
	public List<StoreType> findAllStoreTypesByStoreId(String regNo) {

		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(termQuery("store.regNo", regNo)).build();

		return elasticsearchOperations.queryForList(searchQuery, StoreType.class);

	}

	
	@Override
	public Page<Banner> findBannersByStoreId(String storeId) {
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(termQuery("store.regNo", storeId)).build();

		return elasticsearchOperations.queryForPage(searchQuery, Banner.class);

	}


}
