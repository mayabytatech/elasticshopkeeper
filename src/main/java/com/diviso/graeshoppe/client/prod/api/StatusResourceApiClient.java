package com.diviso.graeshoppe.client.prod.api;

import org.springframework.cloud.openfeign.FeignClient;
import com.diviso.graeshoppe.client.prod.ProdClientConfiguration;

@FeignClient(name="${prod.name:prod}", url="${prod.url:34.73.191.107:8084/}", configuration = ProdClientConfiguration.class)
public interface StatusResourceApiClient extends StatusResourceApi {
}