
package com.example.swiggy.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.swiggy.event.OrderCreatedEvent;

@Service
public class OrderKafkaConsumer {

    private static final Logger logger =
            LoggerFactory.getLogger(OrderKafkaConsumer.class);

    @KafkaListener(
            topics = "swiggy-order-created",
            groupId = "swiggy-order-group"
    )

    public void consumeOrderCreatedEvent(OrderCreatedEvent event) {

        logger.info(
                "Received order event: orderId={}, foodName={}, quantity={}, price={}, status={}",
                event.getOrderId(),
                event.getFoodName(),
                event.getQuantity(),
                event.getPrice(),
                event.getStatus()
        );
    }
}
