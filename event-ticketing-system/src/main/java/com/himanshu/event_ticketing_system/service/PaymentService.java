package com.himanshu.event_ticketing_system.service;

import com.himanshu.event_ticketing_system.config.RazorpayConfig;
import com.himanshu.event_ticketing_system.entity.Booking;
import com.himanshu.event_ticketing_system.entity.Payment;
import com.himanshu.event_ticketing_system.entity.enums.PaymentStatus;
import com.himanshu.event_ticketing_system.repository.BookingRepository;
import com.himanshu.event_ticketing_system.repository.PaymentRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final RazorpayClient razorpayClient;
    private final BookingRepository bookingRepository;

    @Value("${razorpay.key.secret}")
    private String keySecret;

    // create order
    public Map<String,Object> createOrder(Long bookingId) throws Exception{
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(
                () -> new RuntimeException("Booking not Found")
        );
        int amount = booking.getSeatsBooked() * 10000;

        JSONObject options = new JSONObject();
        options.put("amount", amount);
        options.put("curreny", "INR");
        options.put("receipt", "order_" + bookingId);

        Order order = razorpayClient.orders.create(options); // backend calling RAZORPAY

        //SAVE POINT
        Payment payment = new Payment();
        payment.setRazorPayOrderId(order.get("id"));
        payment.setStatus(PaymentStatus.CREATED);
        payment.setAmount(amount);
        payment.setCreatedAt(LocalDateTime.now());
        payment.setBooking(booking);

        paymentRepository.save(payment);

        Map<String , Object> response = new HashMap<>();
        response.put("orderId", order.get("id"));
        response.put("amount", order.get("amount"));
        response.put("currency", order.get("currency"));

        return response;
    }

}
/*
TO BE DONE
1. Create Razorpay Order
2. Save Payment in DB
3. Verify Payment
4. Update Status
 */

/*
1. User clicks PAY
2. Backend → createOrder()
3. Razorpay → returns orderId
4. Backend → saves payment (CREATED)
5. Backend → sends orderId to frontend
6. Frontend → opens Razorpay UI
7. User pays (next step)
 */
