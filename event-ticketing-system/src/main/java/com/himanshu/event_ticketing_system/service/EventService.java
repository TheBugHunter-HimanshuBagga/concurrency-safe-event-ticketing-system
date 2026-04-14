package com.himanshu.event_ticketing_system.service;

import com.himanshu.event_ticketing_system.dto.EventRequest;
import com.himanshu.event_ticketing_system.dto.EventResponse;
import com.himanshu.event_ticketing_system.entity.Event;
import com.himanshu.event_ticketing_system.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final ModelMapper modelMapper;

    private EventResponse createEvent(EventRequest eventRequest){
        Event event = modelMapper.map(eventRequest , Event.class); // here when i get the eventRequest from admin we convert it into entity to be saved inside the repo easily

        event.setAvailableSeats(eventRequest.getTotalSeats()); // initially set all seats as empty
        Event savedEvent = eventRepository.save(event);
        return modelMapper.map(savedEvent , EventResponse.class);
    }

    private List<EventResponse> getAllEvents(){ // when ever there is a list we need to collect each of its element ine bu one  by following the stream -> map -> collect order
        return eventRepository.findAll()
                .stream()
                .map(event -> modelMapper.map(event , EventResponse.class))
                .collect(Collectors.toList());
    }
}
/*
🤷‍♂️CREATE EVENT
Admin (Client)
   ↓
Controller (EventRequest DTO)
   ↓
Service Layer
   ↓
ModelMapper (DTO → Entity)
   ↓
Repository
   ↓
Database





 */
