package com.himanshu.event_ticketing_system.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {

    @NotNull(message = "Event Id is Required")
    private Long eventId;
    @Min(value = 1, message = "At least 1 seat must be booked")
    private int seats;

}
