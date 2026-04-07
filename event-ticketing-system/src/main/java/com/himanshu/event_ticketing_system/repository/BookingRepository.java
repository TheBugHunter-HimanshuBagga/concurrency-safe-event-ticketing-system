package com.himanshu.event_ticketing_system.repository;

import com.himanshu.event_ticketing_system.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {

}
