package com.diviso.graeshoppe.client.report.api;

import org.springframework.cloud.openfeign.FeignClient;
import com.diviso.graeshoppe.client.report.ClientConfiguration;

@FeignClient(name="${report.name:report}", url="${report.url:dev.ci2.divisosofttech.com:8077/}", configuration = ClientConfiguration.class)
public interface OrderLineResourceApiClient extends OrderLineResourceApi {
}