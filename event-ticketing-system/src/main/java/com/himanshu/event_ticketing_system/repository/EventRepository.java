package com.himanshu.event_ticketing_system.repository;

import com.himanshu.event_ticketing_system.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface EventRepository extends JpaRepository<Event , Long> {
    Page<Event> findByTitleContainingIgnoreCaseAndEventDateTimeAfter( //“Find events where title contains the keyword, ignoring case” AND eventDateTime > given date
        String keyword,
        LocalDateTime dateTime,
        Pageable pageable
    );
}
