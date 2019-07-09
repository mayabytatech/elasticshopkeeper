package com.diviso.graeshoppe.client.product.api;

import org.springframework.cloud.openfeign.FeignClient;

import com.diviso.graeshoppe.client.customer.ClientConfiguration;

@FeignClient(name="${product.name:product}", url="${product.url}", configuration = ClientConfiguration.class)
public interface TaxCategoryResourceApiClient extends TaxCategoryResourceApi {
}