package com.himanshu.event_ticketing_system.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentVerifyRequest {
    private String razorpayOrderId;
    private String razorpayPaymentId;
    private String razorpaySignature;
}

