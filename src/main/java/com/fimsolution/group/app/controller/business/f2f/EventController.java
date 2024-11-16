package com.fimsolution.group.app.controller.business.f2f;


import com.fimsolution.group.app.dto.GenericDto;
import com.fimsolution.group.app.dto.RequestDto;
import com.fimsolution.group.app.dto.RespondDto;
import com.fimsolution.group.app.dto.business.f2f.EventsDto;
import com.fimsolution.group.app.dto.business.f2f.event.EventRequestDto;
import com.fimsolution.group.app.dto.business.f2f.event.EventResDto;
import com.fimsolution.group.app.mapper.business.f2f.EventsMapper;
import com.fimsolution.group.app.model.business.f2f.Event;
import com.fimsolution.group.app.service.f2f.EventsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/f2f")
public class EventController {

    private final EventsService eventsService;


    public EventController(EventsService eventsService) {
        this.eventsService = eventsService;
    }

    @PostMapping("/event")
    public ResponseEntity<RespondDto<EventResDto>> createEvent(@RequestBody RequestDto<EventRequestDto> eventRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(RespondDto.<EventResDto>builder()
                        .data(eventsService.createEvent(eventRequestDto.getRequest()))
                        .httpStatusCode(HttpStatus.CREATED.value()).httpStatusName(HttpStatus.CREATED)
                        .message("Event is created").build());
    }

    @GetMapping("/event/{id}")
    public ResponseEntity<GenericDto<?>> getEventById(@PathVariable String id) {
        Event event = eventsService.getEventById(id);
        if (event == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(GenericDto.builder().code("404").message("Event not found").build());
        }
        EventsDto eventsDto = EventsMapper.fromEventsEntityToEventsDto(event);
        return ResponseEntity.ok(GenericDto.<EventsDto>builder().code("200").message("Event retrieved").data(eventsDto).build());
    }

    @PutMapping("/event//{id}")
    public ResponseEntity<GenericDto<?>> updateEvent(@PathVariable String id,
                                                     @RequestBody RequestDto<EventsDto> eventRequestDto) {
        Event existingEvent = eventsService.getEventById(id);
        if (existingEvent == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(GenericDto.builder().code("404").message("Event not found").build());
        }
        Event updatedEvent = EventsMapper.fromEventsDtoToEntity(eventRequestDto.getRequest());
        updatedEvent.setId(id);  // Preserve the existing ID
        eventsService.updateEvent(updatedEvent);
        return ResponseEntity.ok(GenericDto.builder().code("200").message("Event updated").build());
    }

    @DeleteMapping("/event/{id}")
    public ResponseEntity<GenericDto<?>> deleteEvent(@PathVariable String id) {
        if (!eventsService.deleteEventById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(GenericDto.builder().code("404").message("Event not found").build());
        }
        return ResponseEntity.ok(GenericDto.builder().code("200").message("Event deleted").build());
    }

    @GetMapping("/events")
    public ResponseEntity<RespondDto<List<EventResDto>>> getAllEvents() {
        return ResponseEntity
                .ok(RespondDto.<List<EventResDto>>builder().httpStatusName(HttpStatus.OK)
                        .httpStatusCode(HttpStatus.OK.value()).message("Events retrieved")
                        .data(eventsService.getAllEvents())
                        .build());
    }


}
