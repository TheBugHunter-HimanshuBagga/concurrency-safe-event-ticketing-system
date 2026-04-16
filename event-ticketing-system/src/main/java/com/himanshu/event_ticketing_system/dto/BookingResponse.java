package com.himanshu.event_ticketing_system.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {
    private Long id;
    private Long eventId;
    private int seatsBooked;
    private LocalDateTime bookingTime;
}
