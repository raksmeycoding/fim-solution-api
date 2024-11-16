package com.fimsolution.group.app.controller.business.f2f;

import com.fimsolution.group.app.dto.RequestDto;
import com.fimsolution.group.app.dto.RespondDto;
import com.fimsolution.group.app.dto.business.f2f.ObligationDto;
import com.fimsolution.group.app.dto.business.f2f.obligation.ObligationReqDto;
import com.fimsolution.group.app.dto.business.f2f.obligation.ObligationResDto;
import com.fimsolution.group.app.exception.NotFoundException;
import com.fimsolution.group.app.service.f2f.ObligationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/f2f")
public class ObligationController {

    private final ObligationService obligationService;

    public ObligationController(ObligationService obligationService) {
        this.obligationService = obligationService;
    }

    // Create a new obligation
    @PostMapping("/obligation")
    public ResponseEntity<RespondDto<ObligationResDto>> createObligation(@RequestBody RequestDto<ObligationReqDto> obligationReqDtoRequestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        RespondDto.<ObligationResDto>builder()
                                .data(obligationService.createObligation(obligationReqDtoRequestDto.getRequest()))
                                .message("Obligation created")
                                .httpStatusName(HttpStatus.CREATED)
                                .httpStatusCode(HttpStatus.CREATED.value())
                                .build()
                );
    }

    // Get an obligation by ID
    @GetMapping("/obligation/{id}")
    public ResponseEntity<ObligationDto> getObligationById(@PathVariable String id) {
        ObligationDto obligationDto = obligationService.getObligationById(id);
        if (obligationDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(obligationDto, HttpStatus.OK);
    }

    // Update an existing obligation
    @PutMapping("/obligation/{id}")
    public ResponseEntity<String> updateObligation(@PathVariable String id, @RequestBody ObligationDto obligationDto) {
        try {
            // Ensure that the ID in the path matches the ID in the DTO
            obligationDto.setId(id);
            obligationService.updateObligation(obligationDto);
            return new ResponseEntity<>("Obligation updated successfully", HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Delete an obligation by ID
    @DeleteMapping("/obligation/{id}")
    public ResponseEntity<String> deleteObligationById(@PathVariable String id) {
        try {
            obligationService.deleteObligationById(id);
            return new ResponseEntity<>("Obligation deleted successfully", HttpStatus.NO_CONTENT);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Get all obligations
    @GetMapping("/obligations")
    public ResponseEntity<List<ObligationResDto>> getAllObligations() {
        List<ObligationResDto> obligations = obligationService.getAllObligations();
        return new ResponseEntity<>(obligations, HttpStatus.OK);
    }
}
