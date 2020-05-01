package com.aspire.blog.orchestrator.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.aspire.blog.orchestrator.config.FeignConfiguration;

@FeignClient(name = "inventory", configuration = FeignConfiguration.class)
public interface InventoryFeignClientInterceptor {

	@PostMapping("/api/inventories/{inventoryId}/{orderId}")
	public ResponseEntity<Long> occupyInventory(@RequestHeader("Authorization") String authToken,
			@PathVariable("inventoryId") Long inventoryId, @PathVariable("orderId") Long orderId);

}
