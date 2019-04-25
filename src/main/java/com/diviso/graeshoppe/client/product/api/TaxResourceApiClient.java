package com.diviso.graeshoppe.client.product.api;

import org.springframework.cloud.openfeign.FeignClient;
import com.diviso.graeshoppe.client.product.ClientConfiguration;

@FeignClient(name="${product.name:product}", url="${product.url:35.231.213.38:8084/}", configuration = ClientConfiguration.class)
public interface TaxResourceApiClient extends TaxResourceApi {
}