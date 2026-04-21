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
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;


    public BookingResponse bookTickets(BookingRequest bookingRequest){
        int attempts = 0;
        while(true){
            try{
                return processBooking(bookingRequest);
            }catch(OptimisticLockException optimisticLockException){
                attempts++;
                if(attempts > 2){
                    throw optimisticLockException;
                }
            }
        }
    }



    // bookTickets
    @Transactional
    public BookingResponse processBooking(BookingRequest bookingRequest){
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
        booking.setSeatsBooked(bookingRequest.getSeats());
        booking.setBookingTime(LocalDateTime.now());

        Booking savedBooking = bookingRepository.save(booking);
        return mapToResponse(savedBooking);
    }

    // get Users Bookings
    public List<BookingResponse> getMyBookings(){
        // to see his booking user should be loggedIn
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("User Not Found")
        );

        List<Booking> bookings = bookingRepository.findByUser(user);
        return bookings.stream()
                .map(this::mapToResponse)
                .toList();

    }

    private BookingResponse mapToResponse(Booking booking){
        BookingResponse bookingResponse = new BookingResponse();
        bookingResponse.setId(booking.getId());
        bookingResponse.setEventId(booking.getEvent().getId());
        bookingResponse.setSeatsBooked(booking.getSeatsBooked());
        bookingResponse.setBookingTime(booking.getBookingTime());
        return bookingResponse;
    }

    @Transactional
    public void cancelBooking(Long bookingId){
        // get logged-in user
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("User Doesn't exists")
        );
        // get the user booking
        Booking booking = bookingRepository.findByIdAndUser(bookingId , user).orElseThrow(
                () -> new RuntimeException("Booking Not Found!")
        );
        //
        Event event = booking.getEvent();

        event.setAvailableSeats(event.getAvailableSeats() + booking.getSeatsBooked());
        bookingRepository.delete(booking);
    }
}
/*
Part	Role
@Version	                     enables concurrency control
OptimisticLockException	         detects conflict
Retry loop	                     handles conflict

“I implemented concurrency using optimistic locking with a version field and handled conflicts using retry logic.”
 */
