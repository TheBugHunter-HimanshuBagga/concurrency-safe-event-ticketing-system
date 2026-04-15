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

    // createEvent
    public EventResponse createEvent(EventRequest eventRequest){
        Event event = modelMapper.map(eventRequest , Event.class); // here when i get the eventRequest from admin we convert it into entity to be saved inside the repo easily

        event.setAvailableSeats(eventRequest.getTotalSeats()); // initially set all seats as empty
        Event savedEvent = eventRepository.save(event);
        return modelMapper.map(savedEvent , EventResponse.class);
    }

    // getAllEvents
    public List<EventResponse> getAllEvents(){ // when ever there is a list we need to collect each of its element ine bu one  by following the stream -> map -> collect order
        return eventRepository.findAll()
                .stream()
                .map(event -> modelMapper.map(event , EventResponse.class))
                .collect(Collectors.toList());
    }

    // getEventById
    public EventResponse getEventById(Long id){
        Event event = eventRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Event Not Found!!")
        ); // since its optional we will add the exception case
        return modelMapper.map(event , EventResponse.class);
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
