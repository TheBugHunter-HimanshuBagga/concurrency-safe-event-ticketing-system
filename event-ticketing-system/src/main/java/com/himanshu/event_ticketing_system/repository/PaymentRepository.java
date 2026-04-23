package com.himanshu.event_ticketing_system.repository;

import com.himanshu.event_ticketing_system.entity.Payment;
import org.aspectj.lang.JoinPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.parameters.P;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment , Long> {
    Optional<Payment> findByRazorPayOrderId(String razorPayOrderId);
}
