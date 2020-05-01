package com.aspire.blog.orchestrator.client;

import java.net.URISyntaxException;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.aspire.blog.orchestrator.config.FeignConfiguration;
import com.aspire.blog.orchestrator.service.dto.OrderDTO;

@FeignClient(name = "order", configuration = FeignConfiguration.class)
public interface OrderFeignClientInterceptor {

	@DeleteMapping("/api/orders/{id}")
	public Void deleteOrder(@RequestHeader("Authorization") String authToken, @PathVariable("id") Long id);

	@PutMapping("/api/orders")
	public ResponseEntity<OrderDTO> updateOrder(@RequestHeader("Authorization") String authToken,
			@RequestBody Long orderId);
}
