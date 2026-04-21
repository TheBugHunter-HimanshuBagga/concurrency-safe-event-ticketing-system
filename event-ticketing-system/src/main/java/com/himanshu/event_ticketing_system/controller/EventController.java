package com.himanshu.event_ticketing_system.controller;

import com.himanshu.event_ticketing_system.dto.EventRequest;
import com.himanshu.event_ticketing_system.dto.EventResponse;
import com.himanshu.event_ticketing_system.entity.Event;
import com.himanshu.event_ticketing_system.exception.ApiResponse;
import com.himanshu.event_ticketing_system.service.EventService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EventResponse>> getEventById(@PathVariable Long id){
        EventResponse eventResponse = eventService.getEventById(id);
        ApiResponse<EventResponse> apiResponse = new ApiResponse<>(eventResponse);
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EventResponse>> updateEvent(@PathVariable Long id , @RequestBody EventRequest eventRequest){
        EventResponse eventResponse = eventService.updateEvent(id , eventRequest);
        ApiResponse<EventResponse> apiResponse = new ApiResponse<>(eventResponse);
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteEvent(@PathVariable Long id){
        eventService.deleteEvent(id);
        ApiResponse<String> apiResponse = new ApiResponse<>("Event Deleted Successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<EventResponse>>> searchEvents(@RequestParam(required = false) String keyword,

                                                                             @RequestParam(required = false)
                                                                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                                             LocalDateTime dateTime,

                                                                             @RequestParam(defaultValue = "0") int page,
                                                                             @RequestParam(defaultValue = "5") int size){
            Page<EventResponse> eventResponses = eventService.searchEvents(keyword , dateTime , page , size);
            ApiResponse<Page<EventResponse>> apiResponse = new ApiResponse<>(eventResponses);
            return ResponseEntity.ok(apiResponse);
    }
}
