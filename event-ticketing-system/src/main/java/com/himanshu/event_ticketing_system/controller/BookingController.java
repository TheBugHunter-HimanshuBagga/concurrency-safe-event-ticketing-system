package com.himanshu.event_ticketing_system.controller;

import com.himanshu.event_ticketing_system.dto.BookingRequest;
import com.himanshu.event_ticketing_system.dto.BookingResponse;
import com.himanshu.event_ticketing_system.entity.Booking;
import com.himanshu.event_ticketing_system.exception.ApiResponse;
import com.himanshu.event_ticketing_system.repository.BookingRepository;
import com.himanshu.event_ticketing_system.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<ApiResponse<BookingResponse>> bookTickets(@RequestBody @Valid BookingRequest bookingRequest){
        BookingResponse bookingResponse = bookingService.bookTickets(bookingRequest);
        ApiResponse<BookingResponse> apiResponse = new ApiResponse<>(bookingResponse);
        return ResponseEntity.ok(apiResponse);
    }

}
