package com.himanshu.event_ticketing_system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private LocalDateTime eventDateTime;

    private int totalSeats;

    private int availableSeats;

    @OneToMany(mappedBy = "event" , cascade = CascadeType.ALL) // Inverse Side
    @JsonIgnore // prevents infinte Loops in API response
    private List<Booking> bookings;

}
