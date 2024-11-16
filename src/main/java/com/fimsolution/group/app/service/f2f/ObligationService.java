package com.fimsolution.group.app.service.f2f;

import com.fimsolution.group.app.dto.business.f2f.ObligationDto;
import com.fimsolution.group.app.dto.business.f2f.obligation.ObligationReqDto;
import com.fimsolution.group.app.dto.business.f2f.obligation.ObligationResDto;

import java.util.List;

public interface ObligationService {
    ObligationResDto createObligation(ObligationReqDto obligationReqDto);

    ObligationDto getObligationById(String id);

    void updateObligation(ObligationDto obligationDto);

    void deleteObligationById(String id);

    List<ObligationResDto> getAllObligations();


}
