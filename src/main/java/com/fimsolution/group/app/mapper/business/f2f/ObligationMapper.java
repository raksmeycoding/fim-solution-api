package com.fimsolution.group.app.mapper.business.f2f;


import com.fimsolution.group.app.dto.business.f2f.ObligationDto;
import com.fimsolution.group.app.dto.business.f2f.obligation.ObligationReqDto;
import com.fimsolution.group.app.dto.business.f2f.obligation.ObligationResDto;
import com.fimsolution.group.app.model.business.f2f.Obligation;

public class ObligationMapper {
    public static Obligation fromObligationDtoToEntity(ObligationDto obligationDto) {
        return Obligation.builder()
                .use(obligationDto.getUse())
                .free(obligationDto.getFree())
                .change(obligationDto.getChange())
                .createdAt(obligationDto.getCreatedAt())
                .event(obligationDto.getEvent())
                .source(obligationDto.getSource())
                .build();
    }

    public static ObligationDto fromObligationEntityToDto(Obligation obligation) {
        return ObligationDto.builder()
                .id(obligation.getId())
                .use(obligation.getUse())
                .free(obligation.getFree())
                .change(obligation.getChange())
                .createdAt(obligation.getCreatedAt())
                .event(obligation.getEvent())
                .source(obligation.getSource())
                .build();
    }



    // Convert Obligation to ObligationDto
    public static ObligationDto toDto(Obligation obligation) {
        if (obligation == null) {
            return null;
        }

        return ObligationDto.builder()
                .id(obligation.getId())
                .createdAt(obligation.getCreatedAt())
                .free(obligation.getFree())
                .source(obligation.getSource())
                .use(obligation.getUse())
                .change(obligation.getChange())
                .event(obligation.getEvent())
                .build();
    }

    // Convert ObligationDto to Obligation
    public static Obligation toEntity(ObligationDto dto) {
        if (dto == null) {
            return null;
        }

        Obligation obligation = new Obligation();
        obligation.setId(dto.getId());
        obligation.setCreatedAt(dto.getCreatedAt());
        obligation.setFree(dto.getFree());
        obligation.setSource(dto.getSource());
        obligation.setUse(dto.getUse());
        obligation.setChange(dto.getChange());
        obligation.setEvent(dto.getEvent());

        return obligation;
    }

    // Convert ObligationReqDto to Obligation
    public static Obligation toEntity(ObligationReqDto reqDto) {
        if (reqDto == null) {
            return null;
        }

        Obligation obligation = new Obligation();
        obligation.setCreatedAt(reqDto.getCreatedAt());
        obligation.setFree(reqDto.getFree());
        obligation.setSource(reqDto.getSource());
        obligation.setUse(reqDto.getUse());
        obligation.setChange(reqDto.getChange());

        return obligation;
    }

    // Convert Obligation to ObligationResDto
    public static ObligationResDto toResDto(Obligation obligation) {
        if (obligation == null) {
            return null;
        }

        return ObligationResDto.builder()
                .id(obligation.getId())
                .createdAt(obligation.getCreatedAt())
                .free(obligation.getFree())
                .source(obligation.getSource())
                .use(obligation.getUse())
                .change(obligation.getChange())
                .build();
    }



}
