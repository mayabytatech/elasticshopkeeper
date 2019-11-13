package com.diviso.graeshoppe.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.termQuery;

import java.util.List;

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
import org.springframework.stereotype.Service;

import com.diviso.graeshoppe.client.sale.domain.Sale;
import com.diviso.graeshoppe.client.sale.domain.TicketLine;
import com.diviso.graeshoppe.service.SaleQueryService;
import com.github.vanroy.springdata.jest.JestElasticsearchTemplate;

import io.searchbox.client.JestClient;

@Service
public class SaleQueryServiceImpl implements SaleQueryService {

	@Autowired
	ElasticsearchOperations elasticsearchOperations;
	private final JestClient jestClient;
	private final JestElasticsearchTemplate elasticsearchTemplate;

	private final Logger log = LoggerFactory.getLogger(QueryServiceImpl.class);

	public SaleQueryServiceImpl(JestClient jestClient) {
		this.jestClient = jestClient;
		this.elasticsearchTemplate = new JestElasticsearchTemplate(this.jestClient);
	}
	
	
	
	@Override
	public Page<Sale> findSales(String storeId, Pageable pageable) {
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(termQuery("userId", storeId))
				.withSort(SortBuilders.fieldSort("date").order(SortOrder.DESC)).withPageable(pageable).build();
		return elasticsearchOperations.queryForPage(searchQuery, Sale.class);

	}
	
	
	@Override
	public List<TicketLine> findTicketLinesBySaleId(Long saleId) {
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(termQuery("sale.id", saleId)).build();
		return elasticsearchOperations.queryForPage(searchQuery, TicketLine.class).getContent();
	}
	
}
