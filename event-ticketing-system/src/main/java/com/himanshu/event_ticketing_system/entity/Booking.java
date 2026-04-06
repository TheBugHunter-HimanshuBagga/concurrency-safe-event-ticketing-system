package com.himanshu.event_ticketing_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bookings")
public class Booking {
    // user x booked y seats for Event z at Time T
    // it will connect to users , events , seats , time
    // user_Id is a foreign key and Event_ID is a foreign key

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // many bookings belongs to 1 user
    @JoinColumn(name = "user_id" , nullable = false)
    private User user;// it will fetch its primaryKey from the users.id and connect it to user_is FK

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id" , nullable = false)
    private Event event; // it will fetch its primaryKey from the events.id and connect it to event_id FK

    @Column(nullable = false)
    private int seatsBooked;

    @Column(nullable = false)
    private LocalDateTime bookingTime;

}
