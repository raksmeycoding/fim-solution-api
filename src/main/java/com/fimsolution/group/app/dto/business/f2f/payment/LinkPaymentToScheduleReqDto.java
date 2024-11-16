package com.fimsolution.group.app.dto.business.f2f.payment;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LinkPaymentToScheduleReqDto {
    @Schema(description = "ScheduleId", example = "cf843b17-3d7e-443d-8e7b-4a64cbec62fe")
    private String scheduleId;
    @Schema(description = "PaymentId", example = "b11a8ef4-d454-4e68-8e9c-1a59e3cddb2f")
    private String paymentId;
}
