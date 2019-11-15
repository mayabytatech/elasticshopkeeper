/**
 * 
 */
package com.diviso.graeshoppe.web.rest.util;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author maya
 *
 */
public class ServiceUtility {
	
	public static <T> Page getSearchResults(SearchResponse response, Pageable page, T t,ObjectMapper objectMapper) {

		SearchHit[] searchHit = response.getHits().getHits();

		List<T> productList = new ArrayList<>();

		for (SearchHit hit : searchHit) {
			System.out.println("............T............"+t);
			productList.add((T)objectMapper.convertValue(hit.getSourceAsMap(), t.getClass()));
		}

		return new PageImpl(productList, page, response.getHits().getTotalHits());
	}
	
	public static <T> T getSearchResult(SearchResponse response, T t,ObjectMapper objectMapper) {

		SearchHit[] searchHit = response.getHits().getHits();
		T type = null;

		for (SearchHit hit : searchHit) {

			type = (T) objectMapper.convertValue(hit.getSourceAsMap(), t.getClass());
		}

		return type;

	}
	
	public SearchRequest generateSearchRequest(String indexName, Integer totalElement, Integer pageNumber,
			SearchSourceBuilder sourceBuilder) {
		SearchRequest searchRequest = new SearchRequest(indexName);

		int offset = 0;
		int totalElements = 0;

		if (pageNumber == 0) {
			offset = 0;
			totalElements = totalElement;

			System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&offset in00000000Page" + offset);

			System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&totalelements in 00000000Page" + totalElements);
		} else {

			offset = totalElement;

			totalElements = (pageNumber * totalElement);
			System.out.println("****************************offset in else Page" + offset);
			System.out.println("************************totalelements in elsePage" + totalElements);

		}
		sourceBuilder.from(offset);
		sourceBuilder.size(totalElements);

		searchRequest.source(sourceBuilder);
		return searchRequest;
	}

}
