package com.fimsolution.group.app.service.f2f;

import com.fimsolution.group.app.dto.business.f2f.ObligationDto;
import com.fimsolution.group.app.dto.business.f2f.obligation.ObligationReqDto;
import com.fimsolution.group.app.dto.business.f2f.obligation.ObligationResDto;
import com.fimsolution.group.app.exception.NotFoundException;
import com.fimsolution.group.app.mapper.business.f2f.ObligationMapper;
import com.fimsolution.group.app.model.business.f2f.Event;
import com.fimsolution.group.app.model.business.f2f.Obligation;
import com.fimsolution.group.app.repository.f2f.EventsRepository;
import com.fimsolution.group.app.repository.f2f.ObligationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ObligationServiceImpl implements ObligationService {


    private final ObligationRepository obligationRepository;
    private final EventsRepository eventsRepository;


    @Override
    public ObligationResDto createObligation(ObligationReqDto obligationReqDto) {

        /**
         * @Note: business required to validate the event first
         * */

        Optional<Event> optionalEvent = eventsRepository.findById(obligationReqDto.getEventId());

        if (optionalEvent.isEmpty())
            throw new NotFoundException("Event not found");


        Obligation prePareObligation = ObligationMapper.toEntity(obligationReqDto);

        prePareObligation.setEvent(optionalEvent.get());


        Obligation obligation = obligationRepository.save(prePareObligation);


        return ObligationMapper.toResDto(obligation);
    }

    @Override
    public ObligationDto getObligationById(String id) {
        Obligation savedObligation = obligationRepository.findById(id).orElse(null);
        if (savedObligation == null) {
            return ObligationDto.empty();
        }
        return ObligationMapper.fromObligationEntityToDto(savedObligation);
    }

    @Override
    public void updateObligation(ObligationDto obligationDto) {
        Optional<Obligation> optionalObligation = obligationRepository.findById(obligationDto.getId());

        if (optionalObligation.isEmpty()) {
            throw new NotFoundException("Obligation not found");
        }

        Obligation obligation = optionalObligation.get();

        obligation = ObligationMapper.fromObligationDtoToEntity(obligationDto);

        obligationRepository.save(obligation);

    }

    @Override
    public void deleteObligationById(String id) {
        Optional<Obligation> optionalObligation = obligationRepository.findById(id);

        if (optionalObligation.isEmpty()) {
            throw new NotFoundException("Obligation not found");
        }

    }

    @Override
    public List<ObligationResDto> getAllObligations() {
        return obligationRepository.findAll().stream().map(ObligationMapper::toResDto).collect(Collectors.toList());
    }
}
