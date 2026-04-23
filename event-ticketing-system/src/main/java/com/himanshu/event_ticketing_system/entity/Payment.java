package com.himanshu.event_ticketing_system.entity;

import com.himanshu.event_ticketing_system.entity.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String razorPayOrderId;

    private String razorPayPaymentId;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private int amount;

    private LocalDateTime createdAt;

    @OneToOne
    @JoinColumn(name = "booking_id") //owning side
    private Booking booking;

}
/*
1. Booking created
2. Payment happens later
3. Payment links to booking

“Payment is the owning side because it is created after booking and depends on it, so it holds the foreign key for a cleaner and more realistic data model.”
 */
