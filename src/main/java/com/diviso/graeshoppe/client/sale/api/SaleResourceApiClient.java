package com.diviso.graeshoppe.client.sale.api;

import org.springframework.cloud.openfeign.FeignClient;
import com.diviso.graeshoppe.client.sale.ClientConfiguration;

@FeignClient(name="${sale.name:sale}", url="${sale.url:35.231.213.38:8086/}", configuration = ClientConfiguration.class)
public interface SaleResourceApiClient extends SaleResourceApi {
}