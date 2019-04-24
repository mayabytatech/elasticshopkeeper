package com.diviso.graeshoppe.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.common.unit.Fuzziness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import com.diviso.graeshoppe.client.customer.domain.Customer;
/*import com.diviso.graeshoppe.client.product.domain.Category;
import com.diviso.graeshoppe.client.product.domain.Product;
import com.diviso.graeshoppe.domain.Result;*/
import com.diviso.graeshoppe.service.QueryService;
import com.github.vanroy.springdata.jest.JestElasticsearchTemplate;
import com.github.vanroy.springdata.jest.mapper.JestResultsExtractor;
import com.google.gson.JsonObject;

import io.searchbox.client.JestClient;
import io.searchbox.core.SearchResult;

@Service
public class QueryServiceImpl implements QueryService {
	private final JestClient jestClient;
	private final JestElasticsearchTemplate elasticsearchTemplate;

	public QueryServiceImpl(JestClient jestClient) {
		this.jestClient = jestClient;
		this.elasticsearchTemplate = new JestElasticsearchTemplate(this.jestClient);
	}

	@Autowired
	ElasticsearchOperations elasticsearchOperations;

	/*@Override
	public Page<Category> findAllCategories(Pageable pageable) {
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchAllQuery()).build();

		return elasticsearchOperations.queryForPage(searchQuery, Category.class);

	}

	@Override
	public Page<Product> findProductByCategoryId(Long categoryId, Pageable pageable) {
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchQuery("categoryId", categoryId))
				.build();

		return elasticsearchOperations.queryForPage(searchQuery, Product.class);

	}

	public List<Result> findAll(String searchTerm, Pageable pageable) {
		List<Result> values = new ArrayList<Result>();
		;

		SearchQuery searchQuery = new NativeSearchQueryBuilder()
				.withQuery(matchQuery("name", searchTerm).fuzziness(Fuzziness.TWO)).build();

		elasticsearchTemplate.query(searchQuery, new JestResultsExtractor<List<Result>>() {
			@Override
			public List<Result> extract(SearchResult response) {

				for (SearchResult.Hit<JsonObject, Void> searchHit : response.getHits(JsonObject.class)) {
					Result result = new Result();
					// sampleEntity.setId(searchHit.source.get(JestResult.ES_METADATA_ID).getAsString());
					result.setName(searchHit.source.get("name").getAsString());

					values.add(result);

				}
				return values;
			}
		});
		return values;

	}
*/
	@Override
	public Page<Customer> findAllCustomers(String searchTerm, Pageable pageable) {
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
				.withQuery(matchQuery("name", searchTerm).prefixLength(3).fuzziness(Fuzziness.TWO)).build();

		return elasticsearchOperations.queryForPage(searchQuery, Customer.class);

	}

}
