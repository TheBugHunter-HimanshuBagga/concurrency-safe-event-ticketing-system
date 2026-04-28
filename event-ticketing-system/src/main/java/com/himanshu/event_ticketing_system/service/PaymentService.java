package com.himanshu.event_ticketing_system.service;

import com.himanshu.event_ticketing_system.config.RazorpayConfig;
import com.himanshu.event_ticketing_system.dto.PaymentVerifyRequest;
import com.himanshu.event_ticketing_system.entity.Booking;
import com.himanshu.event_ticketing_system.entity.Payment;
import com.himanshu.event_ticketing_system.entity.enums.PaymentStatus;
import com.himanshu.event_ticketing_system.repository.BookingRepository;
import com.himanshu.event_ticketing_system.repository.PaymentRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.razorpay.Utils.verifySignature;

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

    public String verifyPayment(PaymentVerifyRequest request) throws RazorpayException {
        Payment payment = paymentRepository.findByRazorPayOrderId(request.getRazorpayOrderId())
                .orElseThrow(
                        () -> new RuntimeException("Payment Not Found")
                );
        if(payment.getStatus() == PaymentStatus.SUCCESS){
            return "Already Verified";
        }

        boolean isValid = verifySignature(
                request.getRazorpayOrderId(),
                request.getRazorpayPaymentId(),
                request.getRazorpaySignature()
        );

        if(!isValid){
            payment.setStatus(PaymentStatus.FAILED);
            paymentRepository.save(payment);
            throw new RuntimeException("Invalid Payment Signature");
        }

        payment.setRazorPayPaymentId(request.getRazorpayPaymentId());
        payment.setStatus(PaymentStatus.SUCCESS);
        paymentRepository.save(payment);

        return "Payment verified successfully";
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


1. Booking created
2. Order created (CREATED)
3. User pays
4. Frontend sends data
5. verifyPayment() runs
    ↓
    if fake → FAILED
    if real → SUCCESS
 */
