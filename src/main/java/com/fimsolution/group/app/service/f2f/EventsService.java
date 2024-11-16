package com.fimsolution.group.app.service.f2f;

import com.fimsolution.group.app.dto.business.f2f.event.EventRequestDto;
import com.fimsolution.group.app.dto.business.f2f.event.EventResDto;
import com.fimsolution.group.app.model.business.f2f.Event;

import java.util.List;



public interface EventsService {

    EventResDto createEvent(EventRequestDto eventRequestDto);
    Event getEventById(String id);
    void updateEvent(Event event);
    boolean deleteEventById(String id);
    List<EventResDto> getAllEvents();
}
