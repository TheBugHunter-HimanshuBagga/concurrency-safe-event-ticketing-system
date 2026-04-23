package com.himanshu.event_ticketing_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.converter.json.GsonBuilderUtils;

@SpringBootApplication
public class EventTicketingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventTicketingSystemApplication.class, args);
	}

}


/*
Integrating payments [using RazorPAY]
Frontend → Backend → Razorpay → Frontend → Backend (verify)

1. User clicks PAY
2. Backend creates ORDER (Razorpay)
3. Frontend opens Razorpay UI
4. User pays
5. Razorpay returns paymentId + signature
6. Backend verifies signature
7. Booking → CONFIRMED ✅

You NEVER handle card details directly
Stripe does:
    Card details → Stripe
    Your backend → only handles payment status
This is for security (PCI compliance)

(❁´◡`❁)full project overview
Book Ticket → Create Booking (PENDING)
↓
Create Payment Intent (Stripe)
↓
User pays
↓
Stripe confirms
↓
Booking → CONFIRMED

KEY STRIPE Concept
->PaymentIntent -> Represents a payment attempt
->Client Secret -> Secret sent to frontend to complete payment
->Webhook -> Stripe → calls your backend when payment succeeds



 */