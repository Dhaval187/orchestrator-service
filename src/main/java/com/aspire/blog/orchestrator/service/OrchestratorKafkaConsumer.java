package com.aspire.blog.orchestrator.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.aspire.blog.orchestrator.client.InventoryFeignClientInterceptor;
import com.aspire.blog.orchestrator.client.OrderFeignClientInterceptor;
import com.aspire.blog.orchestrator.config.Constants;
import com.aspire.blog.orchestrator.service.dto.OrderDTO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service
public class OrchestratorKafkaConsumer {

	private final Logger log = LoggerFactory.getLogger(OrchestratorKafkaConsumer.class);

	@Autowired
	private InventoryFeignClientInterceptor inventoryFeignClientInterceptor;

	@Autowired
	private OrderFeignClientInterceptor orderFeignClientInterceptor;

	@Autowired
	private OrchestratorKafkaProducer orchestratorKafkaProducer;

	@KafkaListener(topics = Constants.TOPIC_ORDER_PLACED, groupId = "group_id")
	public void consumeOrderPlaced(String message) {
		log.info("Consumed message in {} : {}", Constants.TOPIC_ORDER_PLACED, message);
		JsonParser jsonParser = new JsonParser();
		JsonObject details = jsonParser.parse(message).getAsJsonObject();
		String order = details.get("data").getAsString();
		String authToken = details.get("authToken").getAsString();
		OrderDTO orderDTO = new Gson().fromJson(order, OrderDTO.class);
		try {
			inventoryFeignClientInterceptor.occupyInventory(authToken, orderDTO.getInventory(), orderDTO.getId());

			// complete transaction
			orderFeignClientInterceptor.updateOrder(authToken, orderDTO.getId());
			JsonObject responseObject = new JsonObject();
			responseObject.addProperty("data", orderDTO.getId());
			orchestratorKafkaProducer.sendMessage(Constants.TOPIC_ORDER_PLACED_SUCCESS, responseObject.toString());
		} catch (Exception e) {
			log.error("Error occured while occupy inventory : {}", e);
			orderFeignClientInterceptor.deleteOrder(authToken, orderDTO.getId());
		}

	}

}
