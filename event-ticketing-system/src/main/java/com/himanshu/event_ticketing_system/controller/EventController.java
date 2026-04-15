package com.himanshu.event_ticketing_system.controller;

import com.himanshu.event_ticketing_system.dto.EventRequest;
import com.himanshu.event_ticketing_system.dto.EventResponse;
import com.himanshu.event_ticketing_system.entity.Event;
import com.himanshu.event_ticketing_system.exception.ApiResponse;
import com.himanshu.event_ticketing_system.service.EventService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @PostMapping
    public ResponseEntity<ApiResponse<EventResponse>> createEvent(@RequestBody EventRequest eventRequest){
        EventResponse eventResponse = eventService.createEvent(eventRequest);
        ApiResponse<EventResponse> apiResponse = new ApiResponse<>(eventResponse);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<EventResponse>>> getAllEvents(){
        List<EventResponse> eventResponse = eventService.getAllEvents();
        ApiResponse<List<EventResponse>> apiResponse = new ApiResponse<>(eventResponse);
        return ResponseEntity.ok(apiResponse);
    }
}
