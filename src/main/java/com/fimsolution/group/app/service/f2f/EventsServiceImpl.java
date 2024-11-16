package com.fimsolution.group.app.service.f2f;

import com.fimsolution.group.app.dto.business.f2f.event.EventRequestDto;
import com.fimsolution.group.app.dto.business.f2f.event.EventResDto;
import com.fimsolution.group.app.exception.NotFoundException;
import com.fimsolution.group.app.mapper.business.f2f.EventsMapper;
import com.fimsolution.group.app.model.business.f2f.Event;
import com.fimsolution.group.app.model.business.f2f.Loan;
import com.fimsolution.group.app.model.business.f2f.LoanUser;
import com.fimsolution.group.app.repository.f2f.EventsRepository;
import com.fimsolution.group.app.repository.f2f.LoanRepository;
import com.fimsolution.group.app.repository.f2f.LoanUsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class EventsServiceImpl implements EventsService {
    private final EventsRepository eventsRepository;
    private final LoanUsersRepository loanUsersRepository;

    private final LoanRepository loanRepository;


    @Override
    public EventResDto createEvent(EventRequestDto eventRequestDto) {

        Optional<LoanUser> loanUser = loanUsersRepository.findById(eventRequestDto.getLoanUserId());
        Optional<Loan> loan = loanRepository.findById(eventRequestDto.getLoanId());

        if (loanUser.isEmpty())
            throw new NotFoundException("Loan user not found");

        if (loan.isEmpty())
            throw new NotFoundException("Loan not found");

        Event event = EventsMapper.toEntity(eventRequestDto);
        event.setLoanUser(loanUser.get());
        event.setLoan(loan.get());

        return EventsMapper.toResDto(eventsRepository.save(event));
    }

    @Override
    public Event getEventById(String id) {
        return eventsRepository.findById(id).orElse(null);
    }

    @Override
    public void updateEvent(Event event) {
        eventsRepository.save(event);
    }

    @Override
    public boolean deleteEventById(String id) {
        if (eventsRepository.existsById(id)) {
            eventsRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<EventResDto> getAllEvents() {
        return eventsRepository.findAll().stream().map(EventsMapper::toResDto).collect(Collectors.toList());
    }
}
