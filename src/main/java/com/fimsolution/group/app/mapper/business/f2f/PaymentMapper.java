package com.fimsolution.group.app.mapper.business.f2f;

import com.fimsolution.group.app.dto.business.f2f.payment.PaymentDto;
import com.fimsolution.group.app.dto.business.f2f.payment.PaymentReqDto;
import com.fimsolution.group.app.dto.business.f2f.payment.PaymentResDto;
import com.fimsolution.group.app.model.business.f2f.Payment;
import com.fimsolution.group.app.model.business.f2f.Schedule;

import java.util.Optional;

public class PaymentMapper {
    // Convert Payment entity to PaymentDto
    public static PaymentDto toDto(Payment payment) {
        if (payment == null) {
            return null;
        }

        return PaymentDto.builder()
                .id(payment.getId())
                .schedule(payment.getSchedule())
                .paymentDate(payment.getPaymentDate())
                .createdAt(payment.getCreatedAt())
                .amount(payment.getAmount())
                .adjust(payment.getAdjust())
                .principle(payment.getPrinciple())
                .fimFee(payment.getFimFee())
                .receipt(payment.getReceipt())
                .balance(payment.getBalance())
                .type(payment.getType())
                .status(payment.getStatus())
                .source(payment.getSource())
                .memo(payment.getMemo())
                .build();
    }

    // Convert PaymentDto to Payment entity
    public static Payment toEntity(PaymentDto paymentDto) {
        if (paymentDto == null) {
            return null;
        }

        Payment payment = new Payment();
        payment.setId(paymentDto.getId());
        payment.setSchedule(paymentDto.getSchedule());
        payment.setPaymentDate(paymentDto.getPaymentDate());
        payment.setCreatedAt(paymentDto.getCreatedAt());
        payment.setAmount(paymentDto.getAmount());
        payment.setAdjust(paymentDto.getAdjust());
        payment.setPrinciple(paymentDto.getPrinciple());
        payment.setFimFee(paymentDto.getFimFee());
        payment.setReceipt(paymentDto.getReceipt());
        payment.setBalance(paymentDto.getBalance());
        payment.setType(paymentDto.getType());
        payment.setStatus(paymentDto.getStatus());
        payment.setSource(paymentDto.getSource());
        payment.setMemo(paymentDto.getMemo());

        return payment;
    }

    // Convert Payment entity to PaymentResDto
    public static PaymentResDto toResDto(Payment payment) {
        if (payment == null) {
            return null;
        }

        return PaymentResDto.builder()
                .id(payment.getId())
                .paymentDate(payment.getPaymentDate())
                .createdAt(payment.getCreatedAt())
                .amount(payment.getAmount())
                .adjust(payment.getAdjust())
                .principle(payment.getPrinciple())
                .fimFee(payment.getFimFee())
                .receipt(payment.getReceipt())
                .balance(payment.getBalance())
                .type(payment.getType())
                .status(payment.getStatus())
                .source(payment.getSource())
                .memo(payment.getMemo())
                .build();
    }

    // Convert PaymentReqDto to Payment entity
    public static Payment toEntity(PaymentReqDto paymentReqDto) {
        if (paymentReqDto == null) {
            return null;
        }

        Payment payment = new Payment();
        payment.setPaymentDate(paymentReqDto.getPaymentDate());
        payment.setCreatedAt(paymentReqDto.getCreatedAt());
        payment.setAmount(paymentReqDto.getAmount());
        payment.setAdjust(paymentReqDto.getAdjust());
        payment.setPrinciple(paymentReqDto.getPrinciple());
        payment.setFimFee(paymentReqDto.getFimFee());
        payment.setReceipt(paymentReqDto.getReceipt());
        payment.setBalance(paymentReqDto.getBalance());
        payment.setType(paymentReqDto.getType());
        payment.setStatus(paymentReqDto.getStatus());
        payment.setSource(paymentReqDto.getSource());
        payment.setMemo(paymentReqDto.getMemo());

        return payment;
    }

    // Convert Payment entity to PaymentReqDto (if needed)
    public static PaymentReqDto toReqDto(Payment payment) {
        if (payment == null) {
            return null;
        }

        return PaymentReqDto.builder()
                .paymentDate(payment.getPaymentDate())
                .createdAt(payment.getCreatedAt())
                .amount(payment.getAmount())
                .adjust(payment.getAdjust())
                .principle(payment.getPrinciple())
                .fimFee(payment.getFimFee())
                .receipt(payment.getReceipt())
                .balance(payment.getBalance())
                .type(payment.getType())
                .status(payment.getStatus())
                .source(payment.getSource())
                .memo(payment.getMemo())
                .build();
    }
}
