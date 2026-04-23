package com.himanshu.event_ticketing_system.service;

import com.himanshu.event_ticketing_system.config.RazorpayConfig;
import com.himanshu.event_ticketing_system.entity.Payment;
import com.himanshu.event_ticketing_system.repository.BookingRepository;
import com.himanshu.event_ticketing_system.repository.PaymentRepository;
import com.razorpay.RazorpayClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final RazorpayClient razorpayClient;
    private final BookingRepository bookingRepository;

    @Value("${razorpay.key.secret}")
    private String keySecret;

    // create order

}
