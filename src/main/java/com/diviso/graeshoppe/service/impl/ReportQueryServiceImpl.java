package com.diviso.graeshoppe.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.termQuery;

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

import com.diviso.graeshoppe.client.report.model.AuxItem;
import com.diviso.graeshoppe.client.report.model.ComboItem;
import com.diviso.graeshoppe.client.report.model.OrderMaster;
import com.diviso.graeshoppe.service.ReportQueryService;
import com.github.vanroy.springdata.jest.JestElasticsearchTemplate;

import io.searchbox.client.JestClient;

@Service
public class ReportQueryServiceImpl implements ReportQueryService {

	
	@Autowired
	ElasticsearchOperations elasticsearchOperations;
	private final JestClient jestClient;
	private final JestElasticsearchTemplate elasticsearchTemplate;

	private final Logger log = LoggerFactory.getLogger(QueryServiceImpl.class);

	public ReportQueryServiceImpl(JestClient jestClient) {
		this.jestClient = jestClient;
		this.elasticsearchTemplate = new JestElasticsearchTemplate(this.jestClient);
	}
	
	@Override
	public OrderMaster findOrderMasterByOrderId(String orderId) {
		
		StringQuery stringQuery = new StringQuery(termQuery("orderNumber.keyword", orderId).toString());

		return elasticsearchOperations.queryForObject(stringQuery, OrderMaster.class);

	}
	
	
	@Override
	public Page<AuxItem> findAuxItemByOrderLineId(Long orderLineId, Pageable pageable) {
		
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(termQuery("orderLine.id", orderLineId)).withPageable(pageable).build();

		return elasticsearchOperations.queryForPage(searchQuery, AuxItem.class);
		
	}

	
	
	@Override
	public Page<ComboItem> findComboItemByOrderLineId(Long orderLineId, Pageable pageable) {

		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(termQuery("orderLine.id", orderLineId)).withPageable(pageable).build();

		return elasticsearchOperations.queryForPage(searchQuery, ComboItem.class);
	}
	
	
	@Override
	public Page<com.diviso.graeshoppe.client.report.model.OrderLine> findOrderLineByOrderMasterId(Long orderMasterId,
			Pageable pageable) {
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(termQuery("orderMaster.id", orderMasterId)).withPageable(pageable).build();

		return elasticsearchOperations.queryForPage(searchQuery, com.diviso.graeshoppe.client.report.model.OrderLine.class);
	}

	
}
