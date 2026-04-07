package com.himanshu.event_ticketing_system.repository;

import com.himanshu.event_ticketing_system.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event , Long> {
}
