package com.aspire.blog.orchestrator.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class OrchestratorKafkaConsumer {

    private final Logger log = LoggerFactory.getLogger(OrchestratorKafkaConsumer.class);
    private static final String TOPIC = "topic_orchestrator";

    @KafkaListener(topics = "topic_orchestrator", groupId = "group_id")
    public void consume(String message) throws IOException {
        log.info("Consumed message in {} : {}", TOPIC, message);
    }
}
