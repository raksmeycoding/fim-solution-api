package com.fimsolution.group.app.mapper.business.f2f;

import com.fimsolution.group.app.dto.business.f2f.EventsDto;
import com.fimsolution.group.app.dto.business.f2f.event.EventDto;
import com.fimsolution.group.app.dto.business.f2f.event.EventRequestDto;
import com.fimsolution.group.app.dto.business.f2f.event.EventResDto;
import com.fimsolution.group.app.model.business.f2f.Event;

public class EventsMapper {

    public static Event fromEventsDtoToEntity(EventsDto eventsDto) {
        return Event.builder()
                .id(eventsDto.getId())
                .createdEventDateTime(eventsDto.getCreatedEventDateTime())
                .type(eventsDto.getType())
                .medium(eventsDto.getMedium())
                .memo(eventsDto.getMemo())
                .build();
    }

    public static EventsDto fromEventsEntityToEventsDto(Event event) {
        return EventsDto.builder()
                .id(event.getId())
                .loan(event.getLoan())
                .createdEventDateTime(event.getCreatedEventDateTime())
                .type(event.getType())
                .medium(event.getMedium())
                .memo(event.getMemo())
                .build();
    }


    // Convert Event to EventDto
    public static EventDto toDto(Event event) {
        if (event == null) {
            return null;
        }

        return EventDto.builder()
                .id(event.getId())
                .createdEventDateTime(event.getCreatedEventDateTime())
                .type(event.getType())
                .medium(event.getMedium())
                .memo(event.getMemo())
                .loanUser(event.getLoanUser())
                .loan(event.getLoan())
                .build();
    }

    // Convert EventDto to Event
    public static Event toEntity(EventDto dto) {
        if (dto == null) {
            return null;
        }

        Event event = new Event();
        event.setId(dto.getId());
        event.setCreatedEventDateTime(dto.getCreatedEventDateTime());
        event.setType(dto.getType());
        event.setMedium(dto.getMedium());
        event.setMemo(dto.getMemo());
        event.setLoanUser(dto.getLoanUser());
        event.setLoan(dto.getLoan());

        return event;
    }

    // Convert EventRequestDto to Event
    public static Event toEntity(EventRequestDto reqDto) {
        if (reqDto == null) {
            return null;
        }

        Event event = new Event();
        event.setCreatedEventDateTime(reqDto.getCreatedEventDateTime());
        event.setType(reqDto.getType());
        event.setMedium(reqDto.getMedium());
        event.setMemo(reqDto.getMemo());

        return event;
    }

    // Convert Event to EventResDto
    public static EventResDto toResDto(Event event) {
        if (event == null) {
            return null;
        }

        return EventResDto.builder()
                .id(event.getId())
                .createdEventDateTime(event.getCreatedEventDateTime())
                .type(event.getType())
                .medium(event.getMedium())
                .memo(event.getMemo())
//                .loanUser(event.getLoanUser())
//                .loan(event.getLoan())
                .build();
    }

}