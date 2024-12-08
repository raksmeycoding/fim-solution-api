package com.fimsolution.group.app.service.f2f;


import com.fimsolution.group.app.constant.business.f2f.loanuser.PRIORITIZE;
import com.fimsolution.group.app.dto.business.f2f.loan.LoanReqDto;
import com.fimsolution.group.app.dto.business.f2f.loan.LoanResDto;
import com.fimsolution.group.app.dto.business.f2f.loanuser.LoanUserReqDto;
import com.fimsolution.group.app.dto.business.f2f.loanuser.LoanUserResDto;
import com.fimsolution.group.app.exception.NotFoundException;
import com.fimsolution.group.app.mapper.business.f2f.LoanMapper;
import com.fimsolution.group.app.model.business.f2f.Loan;
import com.fimsolution.group.app.model.business.f2f.LoanUser;
import com.fimsolution.group.app.model.business.f2f.Schedule;
import com.fimsolution.group.app.model.business.f2f.User;
import com.fimsolution.group.app.model.security.UserCredential;
import com.fimsolution.group.app.model.security.UserDetailsImpl;
import com.fimsolution.group.app.repository.UserCredentialRepository;
import com.fimsolution.group.app.repository.f2f.LoanRepository;
import com.fimsolution.group.app.repository.f2f.LoanUsersRepository;
import com.fimsolution.group.app.repository.f2f.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final LoanUsersRepository loanUsersRepository;
    private final UserCredentialRepository userCredentialRepository;
    private final ScheduleRepository scheduleRepository;

    private final Logger logger = LoggerFactory.getLogger(LoanServiceImpl.class);


    @Override
    public LoanResDto createLoan(LoanReqDto loanResDto) {
        Loan loan = loanRepository.save(LoanMapper.toEntity(loanResDto));
        return LoanMapper.toResDto(loan);
    }


    @Override
    public Loan getLoanById(String id) {
        return loanRepository.findById(id).orElseThrow(() -> new NotFoundException("Loan not found"));
    }

    @Override
    public void updateLoan(Loan loan) {
        loanRepository.findById(loan.getId())
                .orElseThrow(() -> new NotFoundException("Loan not found"));
        loanRepository.save(loan);
    }

    @Override
    public boolean deleteLoanById(String id) {
        if (loanRepository.existsById(id)) {
            loanRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }


    @Override
    public LoanResDto getCurrentUserLoanLoan() {
        logger.info(":::getCurrentUserLoanLoan:::");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String email = userDetails.getUsername();

        Optional<UserCredential> userCredentialOptional = userCredentialRepository.findByUsername(email);

        if (userCredentialOptional.isEmpty()) {
            throw new NotFoundException("No registration user has been found");
        }

        Optional<User> userOptional = Optional.ofNullable(userCredentialOptional.get().getUser());
        if (userOptional.isEmpty()) {
            throw new NotFoundException("You are not a loan user yet (contact admin or register as loan user)");
        }

        Optional<LoanUser> loanUser = loanUsersRepository.findLoanUserByUserEmailAndPrioritize(userOptional.get().getEmail(), PRIORITIZE.DEFAULT);


        if (loanUser.isEmpty()) {
            throw new NotFoundException("You are not a loan user yet (contact admin or register as loan user)");
        }

        // Get schedule the latest data of the schedule
        List<Schedule> scheduleList = loanUser.get().getLoan().getSchedules();


        Optional<Schedule> latestSchedule = scheduleList.stream().max(Comparator.comparing(Schedule::getCreateAt));

        latestSchedule.ifPresent(schedule -> System.out.printf("found schedule:" + schedule.getId()));


        LoanResDto loanResDto = LoanMapper.toResDto(loanUser.get().getLoan());

        /**
         * @Note: Developer requirement
         * */
        loanResDto.setRole(loanUser.get().getRole());



        latestSchedule.ifPresent(schedule -> loanResDto.setEndDate(schedule.getCreateAt()));


        logger.info("loanResDto:{}", loanResDto);


        return loanResDto;


    }




}
