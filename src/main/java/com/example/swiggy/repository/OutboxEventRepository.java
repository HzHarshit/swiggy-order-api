
package com.example.swiggy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.swiggy.entity.OutboxEvent;

public interface OutboxEventRepository
        extends JpaRepository<OutboxEvent, Long> {

    List<OutboxEvent> findTop50ByStatusOrderByCreatedAtAscIdAsc(
            String status
    );
}
