package com.diviso.graeshoppe.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import com.diviso.graeshoppe.client.customer.domain.Customer;
import com.diviso.graeshoppe.service.CustomerQueryService;
import com.github.vanroy.springdata.jest.JestElasticsearchTemplate;

import io.searchbox.client.JestClient;

@Service
public class CustomerQueryServiceImpl implements CustomerQueryService{
	
	
	private final JestClient jestClient;
	private final JestElasticsearchTemplate elasticsearchTemplate;

	private final Logger log = LoggerFactory.getLogger(QueryServiceImpl.class);

	public CustomerQueryServiceImpl(JestClient jestClient) {
		this.jestClient = jestClient;
		this.elasticsearchTemplate = new JestElasticsearchTemplate(this.jestClient);
	}


	@Autowired
	ElasticsearchOperations elasticsearchOperations;
	
	@Override
	public Page<Customer> findAllCustomers(String searchTerm, Pageable pageable) {
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
				.withQuery(matchQuery("name", searchTerm).prefixLength(3)).build();

		return elasticsearchOperations.queryForPage(searchQuery, Customer.class);

	}
	
	
	@Override
	public Page<Customer> findAllCustomersWithoutSearch(Pageable pageable) {
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchAllQuery()).withPageable(pageable)
				.build();
		return elasticsearchOperations.queryForPage(searchQuery, Customer.class);
	}
	
}
