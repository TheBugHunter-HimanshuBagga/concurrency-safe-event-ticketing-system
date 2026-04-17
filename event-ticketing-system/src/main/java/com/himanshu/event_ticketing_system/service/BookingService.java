package com.himanshu.event_ticketing_system.service;

import com.himanshu.event_ticketing_system.dto.BookingRequest;
import com.himanshu.event_ticketing_system.dto.BookingResponse;
import com.himanshu.event_ticketing_system.dto.UserResponse;
import com.himanshu.event_ticketing_system.entity.Booking;
import com.himanshu.event_ticketing_system.entity.Event;
import com.himanshu.event_ticketing_system.entity.User;
import com.himanshu.event_ticketing_system.repository.BookingRepository;
import com.himanshu.event_ticketing_system.repository.EventRepository;
import com.himanshu.event_ticketing_system.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    // bookTickets
    @Transactional
    public BookingResponse bookTickets(BookingRequest bookingRequest){

        // get logged-In
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("User Not Found")
        );

        // getEvent
        Event event = eventRepository.findById(bookingRequest.getEventId()).orElseThrow(
                () -> new RuntimeException("Event Not Found")
        );

        // check seat availability
        if(event.getAvailableSeats() < bookingRequest.getSeats()){
            throw new RuntimeException("Not Enough seats available");
        }
        event.setAvailableSeats(event.getAvailableSeats() - bookingRequest.getSeats());

        // create Booking
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setEvent(event);
        booking.setSeatsBooked(booking.getSeatsBooked());
        booking.setBookingTime(LocalDateTime.now());

        Booking savedUser = bookingRepository.save(booking);
        return modelMapper.map(savedUser , BookingRequest.class);
    }

}
