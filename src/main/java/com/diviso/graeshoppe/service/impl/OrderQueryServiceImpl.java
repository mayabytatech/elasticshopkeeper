package com.diviso.graeshoppe.service.impl;

import static org.elasticsearch.action.search.SearchType.QUERY_THEN_FETCH;
import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.index.query.QueryBuilders.rangeQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
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

import com.diviso.graeshoppe.client.order.model.Notification;
import com.diviso.graeshoppe.client.order.model.Order;
import com.diviso.graeshoppe.client.order.model.OrderLine;
import com.diviso.graeshoppe.service.OrderQueryService;
import com.github.vanroy.springdata.jest.JestElasticsearchTemplate;
import com.github.vanroy.springdata.jest.aggregation.AggregatedPage;

import io.searchbox.client.JestClient;
import io.searchbox.core.search.aggregation.TermsAggregation;
import io.searchbox.core.search.aggregation.TermsAggregation.Entry;

@Service
public class OrderQueryServiceImpl implements OrderQueryService {
	
	int i = 0;
	Long count = 0L;

	
	@Autowired
	ElasticsearchOperations elasticsearchOperations;
	private final Logger log = LoggerFactory.getLogger(QueryServiceImpl.class);

	private final JestClient jestClient;
	private final JestElasticsearchTemplate elasticsearchTemplate;


	public OrderQueryServiceImpl(JestClient jestClient) {
		this.jestClient = jestClient;
		this.elasticsearchTemplate = new JestElasticsearchTemplate(this.jestClient);
	}
	
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.diviso.graeshoppe.service.QueryService#findOrderByStatusName(java.lang.
	 * String)
	 */
	@Override
	public Page<Order> findOrderByStatusNameAndDeliveryType(String statusName, String storeId, String deliveryType, Pageable pageable) {
		SearchQuery searchQuery =null;
		if(deliveryType.equals("all")) {
			 searchQuery = new NativeSearchQueryBuilder()
					.withQuery(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("status.name.keyword", statusName))
							.must(QueryBuilders.matchQuery("storeId", storeId)))
					.withSort(SortBuilders.fieldSort("id").order(SortOrder.DESC))
					.withPageable(pageable).build();
		}
		else {
			 searchQuery = new NativeSearchQueryBuilder()
					.withQuery(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("status.name.keyword", statusName))
							.must(QueryBuilders.matchQuery("storeId", storeId))
							.must(QueryBuilders.matchQuery("deliveryInfo.deliveryType.keyword",deliveryType )))
					.withSort(SortBuilders.fieldSort("id").order(SortOrder.DESC))
					.withPageable(pageable).build();
		}
		

		return elasticsearchOperations.queryForPage(searchQuery, Order.class);
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.diviso.graeshoppe.service.QueryService#findOrderLinesByStoreId(java.
	 * lang.String)
	 */
	@Override
	public Page<Order> findOrderByStoreId(String storeId, Pageable pageable) {
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(termQuery("storeId", storeId))
				.withSort(SortBuilders.fieldSort("id").order(SortOrder.DESC)).build();

		Page<Order> orderPage = elasticsearchOperations.queryForPage(searchQuery, Order.class);

		orderPage.forEach(order -> {

			order.setOrderLines(new HashSet<OrderLine>(findOrderLinesByOrderId(order.getId())));

		});

		return orderPage;
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.diviso.graeshoppe.service.QueryService#findOrderByStoreId(java.lang.
	 * String)
	 */
	@Override
	public List<OrderLine> findOrderLinesByOrderId(Long orderId) {
		StringQuery searchQuery = new StringQuery(termQuery("order.id", orderId).toString());
		return elasticsearchOperations.queryForList(searchQuery, OrderLine.class);
	}

	
	
	@Override
	public Page<Notification> findNotificationByReceiverId(String receiverId, Pageable pageable) {

		SearchQuery searchQuery = new NativeSearchQueryBuilder()
				.withQuery(termQuery("receiverId", receiverId))
				.withSort(SortBuilders.fieldSort("id").order(SortOrder.DESC))
				.withPageable(pageable).build();

		return elasticsearchOperations.queryForPage(searchQuery, Notification.class);
	}
	
	
	public Long findOrderCountByDateAndStatusName(String statusName, Instant date) {

		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchAllQuery())
				.withSearchType(QUERY_THEN_FETCH).withIndices("order").withTypes("order")
				.addAggregation(AggregationBuilders.terms("date").field("date.keyword")
						.order(org.elasticsearch.search.aggregations.bucket.terms.Terms.Order.aggregation("avgPrice",
								true))
						.subAggregation(AggregationBuilders.avg("avgPrice").field("grandTotal"))
						.subAggregation(AggregationBuilders.terms("statusName").field("status.name.keyword")))
				.build();

		AggregatedPage<Order> result = elasticsearchTemplate.queryForPage(searchQuery, Order.class);

		TermsAggregation orderAgg = result.getAggregation("date", TermsAggregation.class);
		List<Entry> storeBasedEntry = new ArrayList<Entry>();
		log.info(",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,," + orderAgg.getBuckets());
		orderAgg.getBuckets().forEach(bucket -> {

			List<Entry> listStore = bucket.getAggregation("statusName", TermsAggregation.class).getBuckets();
			log.info(",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,," + listStore);
			for (int i = 0; i < listStore.size(); i++) {
				log.info(",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,," + listStore.get(i));
				if (bucket.getKey().equals(date.toString())) {
					if (listStore.get(i).getKey().equals(statusName)) {

						storeBasedEntry
								.add(bucket.getAggregation("statusName", TermsAggregation.class).getBuckets().get(i));
					}
				}

			}

		});
		storeBasedEntry.forEach(e -> {
			count = e.getCount();
		});
		return count;
	}
	
	
	@Override
	public Page<Order> findOrderByDatebetweenAndStoreId(Instant from, Instant to, String storeId) {
		// .........
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(QueryBuilders.boolQuery()
				.must(termQuery("storeId", storeId)).must(rangeQuery("date").gte(from).lte(to))).build();

		return elasticsearchOperations.queryForPage(searchQuery, Order.class);
	}
	
	
	@Override
	public Long getNotificationCountByReceiveridAndStatus(String status, String receiverId) {
		log.info(".............." + status + ".............." + receiverId);
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(QueryBuilders.boolQuery()
				.must(termQuery("status.keyword", status)).must(QueryBuilders.matchQuery("receiverId", receiverId)))
				.build();

		List<Notification> notifications = new ArrayList<Notification>();

		elasticsearchOperations.queryForPage(searchQuery, Notification.class).getContent().forEach(notifications::add);
		return (long) notifications.size();
	}
	
	
	@Override
	public Long findNotificationCountByReceiverIdAndStatusName(String receiverId, String status) {
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
				.withQuery(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("receiverId", receiverId))
						.must(QueryBuilders.matchQuery("status", status)))
				.build();
		return elasticsearchOperations.count(searchQuery, Order.class);
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.diviso.graeshoppe.service.QueryService#findOrderByOrderId(java.lang.
	 * String)
	 */
	@Override
	public Order findOrderByOrderId(String orderId) {

		StringQuery stringQuery = new StringQuery(termQuery("orderId.keyword", orderId).toString());

		Order test = elasticsearchOperations.queryForObject(stringQuery, Order.class);
		log.info("+++++++++++++++++++++++++++++++++++++++", test);
		return test;
	}






}
