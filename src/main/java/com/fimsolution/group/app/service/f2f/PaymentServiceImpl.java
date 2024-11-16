package com.fimsolution.group.app.service.f2f;

import com.fimsolution.group.app.constant.business.f2f.loanuser.PRIORITIZE;
import com.fimsolution.group.app.dto.business.f2f.payment.LinkPaymentToScheduleReqDto;
import com.fimsolution.group.app.dto.business.f2f.payment.PaymentReqDto;
import com.fimsolution.group.app.dto.business.f2f.payment.PaymentResDto;
import com.fimsolution.group.app.dto.business.f2f.payment.PaymentWithSchedule;
import com.fimsolution.group.app.exception.NotFoundException;
import com.fimsolution.group.app.mapper.business.f2f.LoanUserMapper;
import com.fimsolution.group.app.mapper.business.f2f.PaymentMapper;
import com.fimsolution.group.app.mapper.business.f2f.ScheduleMapper;
import com.fimsolution.group.app.model.business.f2f.*;
import com.fimsolution.group.app.model.security.UserDetailsImpl;
import com.fimsolution.group.app.repository.f2f.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentsRepository paymentsRepository;
    private final ScheduleRepository scheduleRepository;
    private final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);
    private final LoanUsersRepository loanUsersRepository;
    private final UsersRepository usersRepository;
    private final LoanRepository loanRepository;


    @Override
    public PaymentResDto createPayment(PaymentReqDto paymentReqDto) {
        /**
         * TODO: if check check later in case business require.
         * */
        Optional<Schedule> scheduleOptional = scheduleRepository.findById(paymentReqDto.getScheduleId());
        if (scheduleOptional.isEmpty())
            throw new NotFoundException("Schedule not found");

        Optional<Loan> loanOptional = loanRepository.findById(paymentReqDto.getLoanId());
        if (loanOptional.isEmpty())
            throw new NotFoundException("LoanId not found or required");

        Payment recordPayment = PaymentMapper.toEntity(paymentReqDto);

        recordPayment.setSchedule(scheduleOptional.get());
        recordPayment.setLoan(loanOptional.get());

        var savePayment = paymentsRepository.save(recordPayment);

        return PaymentMapper.toResDto(savePayment);
    }

    @Override
    public boolean linkPaymentToSchedule(LinkPaymentToScheduleReqDto body) {
        Optional<Schedule> optionalSchedule = scheduleRepository.findById(body.getScheduleId());

        Optional<Payment> optionalPayment = paymentsRepository.findById(body.getPaymentId());


        if (optionalSchedule.isEmpty()) {
            throw new NotFoundException("Schedule not found");
        }

        if (optionalPayment.isEmpty()) {
            throw new NotFoundException("Payment not found");
        }

        Payment payment = optionalPayment.get();
        payment.setSchedule(optionalSchedule.get());

        paymentsRepository.save(payment);


        return true;
    }


    @Override
    public List<PaymentResDto> getAllPayments() {
        List<PaymentResDto> paymentResDtoList = paymentsRepository.findAll()
                .stream()
                .map(PaymentMapper::toResDto)
                .toList();
        logger.info("paymentResDtoList:{}", paymentResDtoList);
        return paymentResDtoList;
    }


//    @Override
//    public List<PaymentResDto> getPaymentByLoanUserId(String loanUserId) {
//        logger.info(":::Find Payment By LoanUser:::");
//        List<Payment> paymentList = paymentsRepository.findPaymentsByLoanUserId(loanUserId);
//        logger.info("...Found Payment By LoanUser...");
//        return paymentList.stream().map(PaymentMapper::toResDto).collect(Collectors.toList());
//    }


    @Override
    public List<PaymentResDto> getPaymentByScheduleId(String scheduleId) {
        Optional<Schedule> scheduleOptional = scheduleRepository.findById(scheduleId);

        if (scheduleOptional.isEmpty())
            throw new NotFoundException("Schedule not found");

        return scheduleOptional.get().getPayment().stream().map(PaymentMapper::toResDto).collect(Collectors.toList());
    }


    @Override
    public List<PaymentResDto> getLast4PaymentsForDefaultLoan() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        logger.info(":::Current user object:::{}", userDetails);

        String userName = userDetails.getUsername();

        if (userName.isEmpty() || userName.isBlank()) {
            throw new NotFoundException("No registration uer has been found");
        }


        Optional<User> optionalUser = usersRepository.findByEmail(userName);

        if (optionalUser.isEmpty()) {
            throw new NotFoundException("You are not a loan user yet (contact admin or register as loan user)");
        }

        Optional<LoanUser> defaultLoanUserOptional = loanUsersRepository.findLoanUserByUserEmailAndPrioritize(optionalUser.get().getEmail(), PRIORITIZE.DEFAULT);

        if (defaultLoanUserOptional.isEmpty())
            throw new NotFoundException("You are not a loan user yet (contact admin or register as loan user)");

//        {
//            PaymentWithSchedule
//                    .builder()
//                    .schedule(ScheduleMapper.fromScheduleEntityToScheduleResDto(schedule))
//                    .paymentResDtos(schedule.getPayment().stream().map(PaymentMapper::toResDto).collect(Collectors.toList()))
//                    .build();

//        return defaultLoanUserOptional.get().getLoan().getSchedules().stream().flatMap(schedule -> Stream.of(PaymentWithSchedule
//                .builder()
//                .schedule(ScheduleMapper.fromScheduleEntityToScheduleResDto(schedule))
//                .paymentResDtos(schedule.getPayment().stream().map(PaymentMapper::toResDto).collect(Collectors.toList()))
//                .build())).toList();


//                logger.info(":::defaultLoanUserOptional:::{}", LoanUserMapper.toResDto(defaultLoanUserOptional.get()));


        // Last for payment
        List<Payment> last4Payments = defaultLoanUserOptional.get()
                .getLoan()
                .getPayments()
                .stream()
                .sorted(Comparator.comparing(Payment::getPaymentDate).reversed())
                .limit(4)
                .toList();

        List<PaymentResDto> paymentResDtos = new ArrayList<>();
        for (Payment payment : last4Payments) {
            PaymentResDto paymentResDto = PaymentMapper.toResDto(payment);
            paymentResDto.setScheduleResDto(ScheduleMapper.fromScheduleEntityToScheduleResDto(payment.getSchedule()));
            paymentResDtos.add(paymentResDto);

        }


        return paymentResDtos;


    }
}
