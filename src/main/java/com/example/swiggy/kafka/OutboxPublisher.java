
package com.example.swiggy.kafka;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.swiggy.entity.OutboxEvent;
import com.example.swiggy.event.OrderCreatedEvent;
import com.example.swiggy.repository.OutboxEventRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class OutboxPublisher {

    private static final Logger logger =
            LoggerFactory.getLogger(OutboxPublisher.class);

    private final OutboxEventRepository outboxEventRepository;
    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public OutboxPublisher(
            OutboxEventRepository outboxEventRepository,
            KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate,
            ObjectMapper objectMapper) {

        this.outboxEventRepository = outboxEventRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @Scheduled(fixedDelay = 5000)
    public void publishPendingEvents() {

        List<OutboxEvent> pendingEvents =
                outboxEventRepository
                        .findTop50ByStatusOrderByCreatedAtAscIdAsc("PENDING");

        for (OutboxEvent outboxEvent : pendingEvents) {
            try {
                OrderCreatedEvent event = objectMapper.readValue(
                        outboxEvent.getPayload(),
                        OrderCreatedEvent.class
                );

                kafkaTemplate.send(
                        "swiggy-order-created",
                        String.valueOf(outboxEvent.getAggregateId()),
                        event
                ).get(10, TimeUnit.SECONDS);

                outboxEvent.setStatus("PUBLISHED");
                outboxEvent.setPublishedAt(LocalDateTime.now());

                outboxEventRepository.save(outboxEvent);

                logger.info(
                        "Outbox event published successfully: outboxId={}, orderId={}",
                        outboxEvent.getId(),
                        outboxEvent.getAggregateId()
                );

            } catch (Exception exception) {
                logger.error(
                        "Failed to publish outbox event: outboxId={}, orderId={}. It will be retried.",
                        outboxEvent.getId(),
                        outboxEvent.getAggregateId(),
                        exception
                );
            }
        }
    }
}
