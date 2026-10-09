
package com.example.swiggy.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.swiggy.event.OrderCreatedEvent;

@Service
public class OrderKafkaProducer {

    private static final Logger logger =
            LoggerFactory.getLogger(OrderKafkaProducer.class);

    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    public OrderKafkaProducer(
            KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


public void publishOrderCreatedEvent(OrderCreatedEvent event) {

    kafkaTemplate.send(
            "swiggy-order-created",
            String.valueOf(event.getOrderId()),
            event
    ).whenComplete((result, exception) -> {

        if (exception != null) {
            logger.error(
                    "Failed to publish order event for orderId: {}",
                    event.getOrderId(),
                    exception
            );
        } else {
            logger.info(
                    "Order event delivered: orderId={}, partition={}, offset={}",
                    event.getOrderId(),
                    result.getRecordMetadata().partition(),
                    result.getRecordMetadata().offset()
            );
        }
    });
}

}
