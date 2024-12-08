package com.fimsolution.group.app.service.f2f;

import com.fimsolution.group.app.constant.business.f2f.loanuser.PRIORITIZE;
import com.fimsolution.group.app.constant.business.f2f.schedule.SOURCE;
import com.fimsolution.group.app.constant.business.f2f.schedule.STATUS;
import com.fimsolution.group.app.dto.business.f2f.schedule.ScheduleAmountDueDto;
import com.fimsolution.group.app.dto.business.f2f.schedule.ScheduleDto;
import com.fimsolution.group.app.dto.business.f2f.schedule.ScheduleReqDto;
import com.fimsolution.group.app.dto.business.f2f.schedule.ScheduleResDto;
import com.fimsolution.group.app.exception.NotFoundException;
import com.fimsolution.group.app.mapper.business.f2f.ScheduleMapper;
import com.fimsolution.group.app.model.business.f2f.Loan;
import com.fimsolution.group.app.model.business.f2f.LoanUser;
import com.fimsolution.group.app.model.business.f2f.Schedule;
import com.fimsolution.group.app.model.security.UserDetailsImpl;
import com.fimsolution.group.app.repository.f2f.LoanRepository;
import com.fimsolution.group.app.repository.f2f.LoanUsersRepository;
import com.fimsolution.group.app.repository.f2f.ObligationRepository;
import com.fimsolution.group.app.repository.f2f.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.annotation.QueryAnnotation;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Qualifier("ScheduleServiceImpl")
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ObligationRepository obligationRepository;
    private final LoanRepository loanRepository;
    private final LoanUsersRepository loanUsersRepository;
    private final static Logger logger = LoggerFactory.getLogger(ScheduleServiceImpl.class);

    @Override
    public ScheduleResDto createSchedule(ScheduleReqDto scheduleReqDto) {
        logger.info(":::Performing create schedule:::");
        /*
         * Note: will check condition letter if obligation is existed or not. But now let it passes.
         * */
//        Optional<Obligation> optionalObligation = obligationRepository.findById(scheduleReqDto.getObligationId());

//        if (optionalObligation.isEmpty()) {
//            throw new NotFoundException("Obligation not found with id: " + scheduleReqDto.getObligationId());
//        }

        /**
         * @Note: Check loan id
         * */
        Optional<Loan> loanOptional = loanRepository.findById(scheduleReqDto.getLoanId());
        if (loanOptional.isEmpty())
            throw new NotFoundException("loan id not found");


        Schedule schedule = ScheduleMapper.fromScheduleReqDtoToScheduleEntity(scheduleReqDto);
//
//        schedule.setObligation(optionalObligation.get());
        schedule.setLoan(loanOptional.get());


        Schedule scheduleRecord = scheduleRepository.save(schedule);

        ScheduleResDto scheduleResDto = ScheduleMapper.fromScheduleEntityToScheduleResDto(scheduleRecord);

        logger.info("...Schedule created...");

        return scheduleResDto;
    }

    @Override
    public ScheduleResDto getScheduleById(String scheduleId) {

        logger.info(":::Performing find schedule by scheduleId:{} :::", scheduleId);
        Optional<Schedule> optionalSchedule = scheduleRepository.findById(scheduleId);

        /* if the schedule has not been found*/
        if (optionalSchedule.isEmpty()) {
            logger.info("...Schedule has not been found...");
            throw new NotFoundException("Schedule not found with id: " + scheduleId);
        }


        logger.info("...Schedule found...");
        return ScheduleMapper.fromScheduleEntityToScheduleResDto(optionalSchedule.get());


    }

    @Override
    public ScheduleDto updateScheduleById(String scheduleId, ScheduleDto scheduleDto) {
        logger.info(":::Performing update schedule:::");
        Optional<Schedule> optionalSchedule = scheduleRepository.findById(scheduleId);
        if (optionalSchedule.isEmpty())
            throw new NotFoundException("Schedule not found");

        logger.info(":::Performing update schedule by scheduleId:{} :::", scheduleId);
        Schedule schedule = optionalSchedule.get();

        schedule.setAmount(scheduleDto.getAmount());
        schedule.setAdjustment(scheduleDto.getAdjustment());
        schedule.setPaid(scheduleDto.getPaid());
        schedule.setDue(scheduleDto.getDue());
        schedule.setInterest(scheduleDto.getInterest());
        schedule.setPrinciple(scheduleDto.getPrinciple());
        schedule.setFimFee(scheduleDto.getFimFee());
        schedule.setBalance(scheduleDto.getBalance());
        schedule.setSource(optionalSchedule.get().getSource());
        schedule.setStatus(optionalSchedule.get().getStatus());
        schedule.setDelinquency(optionalSchedule.get().getDelinquency());
        schedule.setMemo(optionalSchedule.get().getMemo());


        scheduleRepository.save(ScheduleMapper.fromScheduleDtoToScheduleEntity(scheduleDto));
        return null;
    }

    @Override
    public void deleteScheduleById(String scheduleId) {

    }

    @Override
    public List<ScheduleDto> getSchedules() {
        return List.of();
    }

    @Override
    public List<ScheduleDto> getSchedulesByObligationId(String obligationId) {
        return List.of();
    }


    @Override
    public List<ScheduleResDto> getAllSchedules() {
        return scheduleRepository.findAll().stream().map(ScheduleMapper::fromScheduleEntityToScheduleResDto).collect(Collectors.toList());
    }


    @Override
    public List<ScheduleResDto> getScheduleByLoanId(String loanId) {

        Optional<Loan> loanOptional = loanRepository.findById(loanId);

        if (loanOptional.isEmpty())
            throw new NotFoundException("loan id not found");

        return loanOptional.get().getSchedules().stream().map(ScheduleMapper::fromScheduleEntityToScheduleResDto).collect(Collectors.toList());


    }


    @Override
    public ScheduleAmountDueDto getAmountDueForCurrentUser() {

        logger.info(":::getAmountDueForCurrentUser:::");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        logger.info("Authenticated user object:{}", userDetails);
        /**
         * @Note: Get amount for borrower or for lender, and also allow admin to review to see it as well
         * @Query_Param: The next schedule that close today (schedule date)
         * status = "FUTURE", source = "BORROWER | LENDER", loanId  (default loan), due
         * */
        String username = userDetails.getUsername();
//        List<String> authorities = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        logger.info("Authenticated user name:{}", username);
        if (username == null)
            throw new RuntimeException("username is null");

        Optional<LoanUser> loanUserOptional = loanUsersRepository.findLoanUserByUserEmailAndPrioritize(username, PRIORITIZE.DEFAULT);

        if (loanUserOptional.isEmpty())
            throw new NotFoundException("No default loan use has been not found");

        var foundedLoan = loanUserOptional.get().getLoan();

        if (foundedLoan == null) {
            throw new NotFoundException("No default loan found");
        }

        List<Schedule> scheduleList = foundedLoan.getSchedules().stream().filter(schedule -> SOURCE.BORROWER.equals(schedule.getSource())).toList();

//        List<Schedule> scheduleList = foundedLoan.getSchedules();

        if (scheduleList == null || scheduleList.isEmpty()) {
            throw new NotFoundException("No schedules list found");
        }

        LocalDateTime now = LocalDateTime.now();


        // Initialize the closestDateTime and minimum difference
//        LocalDateTime closestDateTime = null;
        Duration minDifference = null;
        Schedule closestSchedule = null;

        for (Schedule schedule : scheduleList) {

            // check the schedule is after current timestamp
            if (schedule.getCreateAt().isAfter(now)) {
                Duration difference = Duration.between(now, schedule.getCreateAt()); // No need for abs() since we're only interested in future schedules

                // Find the closest future schedule
                if (minDifference == null || difference.compareTo(minDifference) < 0) {
                    minDifference = difference;
                    closestSchedule = schedule;
                }
            }

        }


        if (closestSchedule == null) {
            throw new NotFoundException("No schedule found");
        }


        ScheduleAmountDueDto scheduleAmountDueDto = ScheduleAmountDueDto.builder().build();
        scheduleAmountDueDto.setScheduleId(closestSchedule.getId() != null ? closestSchedule.getId() : null);
        scheduleAmountDueDto.setCreatedAt(closestSchedule.getCreateAt() != null ? closestSchedule.getCreateAt() : null);
        scheduleAmountDueDto.setDue(closestSchedule.getDue() != null ? closestSchedule.getDue() : null);
        scheduleAmountDueDto.setStatus(closestSchedule.getStatus() != null ? closestSchedule.getStatus() : null);
        scheduleAmountDueDto.setSource(closestSchedule.getSource() != null ? closestSchedule.getSource() : null);
//        scheduleAmountDueDto.setLoanId(closestSchedule.getLoan().getLoanId() != null ? closestSchedule.getLoan().getLoanId() : null);


        return scheduleAmountDueDto;


    }


    @Override
    public Optional<ScheduleAmountDueDto> getAmountDueForCurrentUserV2() {
        return Optional.empty();
    }

    @Override
    public Optional<ScheduleAmountDueDto> getAmountDueByLoanIdAndSourceEqualBorrower(String LoanId) {
        return Optional.empty();
    }

    @Override
    public Optional<ScheduleAmountDueDto> getAmountDueByLoanIdAndSourceEqualLender(String LoanId) {
        return Optional.empty();
    }

    @Override
    public ScheduleAmountDueDto getYouAreScheduleToReceive() {
        logger.info(":::getAmountDueForCurrentUser:::");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        logger.info("Authenticated user object:{}", userDetails);
        /**
         * @Note: Get amount for borrower or for lender, and also allow admin to review to see it as well
         * @Query_Param: The next schedule that close today (schedule date)
         * status = "FUTURE", source = "BORROWER | LENDER", loanId  (default loan), due
         * */
        String username = userDetails.getUsername();
//        List<String> authorities = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        logger.info("Authenticated user name:{}", username);
        if (username == null)
            throw new RuntimeException("username is null");

        Optional<LoanUser> loanUserOptional = loanUsersRepository.findLoanUserByUserEmailAndPrioritize(username, PRIORITIZE.DEFAULT);

        if (loanUserOptional.isEmpty())
            throw new NotFoundException("No default loan user has been not found");

        var foundedLoan = loanUserOptional.get().getLoan();

        if (foundedLoan == null) {
            throw new NotFoundException("No default loan found");
        }

        // Must have only one when admin created
        List<Schedule> scheduleList = foundedLoan.getSchedules().stream().filter(schedule -> SOURCE.LENDER.equals(schedule.getSource())).toList();

        if (scheduleList == null || scheduleList.isEmpty()) {
            throw new NotFoundException("No schedules list of source lender found");
        }

        LocalDateTime now = LocalDateTime.now();


        // Initialize the closestDateTime and minimum difference
//        LocalDateTime closestDateTime = null;
        Duration minDifference = null;
        Schedule closestSchedule = null;

        for (Schedule schedule : scheduleList) {

            // check the schedule is after current timestamp
            if (schedule.getCreateAt().isAfter(now)) {
                Duration difference = Duration.between(now, schedule.getCreateAt()); // No need for abs() since we're only interested in future schedules

                // Find the closest future schedule
                if (minDifference == null || difference.compareTo(minDifference) < 0) {
                    minDifference = difference;
                    closestSchedule = schedule;
                }
            }

        }


        if (closestSchedule == null) {
            throw new NotFoundException("No schedule found");
        }


        ScheduleAmountDueDto scheduleAmountDueDto = ScheduleAmountDueDto.builder().build();
        scheduleAmountDueDto.setScheduleId(closestSchedule.getId() != null ? closestSchedule.getId() : null);
        scheduleAmountDueDto.setCreatedAt(closestSchedule.getCreateAt() != null ? closestSchedule.getCreateAt() : null);
        scheduleAmountDueDto.setDue(closestSchedule.getDue() != null ? closestSchedule.getDue() : null);
        scheduleAmountDueDto.setStatus(closestSchedule.getStatus() != null ? closestSchedule.getStatus() : null);
        scheduleAmountDueDto.setSource(closestSchedule.getSource() != null ? closestSchedule.getSource() : null);
//        scheduleAmountDueDto.setLoanId(closestSchedule.getLoan().getLoanId() != null ? closestSchedule.getLoan().getLoanId() : null);


        return scheduleAmountDueDto;
    }


    @Override
    public ScheduleAmountDueDto getCalculatedPassedDue() {
        logger.info(":::getAmountDueForCurrentUser:::");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        logger.info("Authenticated user object:{}", userDetails);
        /**
         * @Note: Get amount for borrower or for lender, and also allow admin to review to see it as well
         * @Query_Param: The next schedule that close today (schedule date)
         * status = "FUTURE", source = "BORROWER | LENDER", loanId  (default loan), due
         * */
        String username = userDetails.getUsername();
//        List<String> authorities = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        logger.info("Authenticated user name:{}", username);
        if (username == null)
            throw new RuntimeException("username is null");

        Optional<LoanUser> loanUserOptional = loanUsersRepository.findLoanUserByUserEmailAndPrioritize(username, PRIORITIZE.DEFAULT);

        if (loanUserOptional.isEmpty())
            throw new NotFoundException("No default loan user has been not found");

        var foundedLoan = loanUserOptional.get().getLoan();

        if (foundedLoan == null) {
            throw new NotFoundException("No default loan found");
        }

        // set type of use borrower or lender or proxy
        ScheduleAmountDueDto scheduleAmountResDto = ScheduleAmountDueDto.builder().build();
        scheduleAmountResDto.setBorrowerOrLender(loanUserOptional.get().getRole().name());

        // get schedule list base one the default loan
        List<Schedule> scheduleList = foundedLoan.getSchedules().stream().filter(schedule -> schedule.getStatus().equals(STATUS.PAST_DUE_5) || schedule.getStatus().equals(STATUS.PAST_DUE_30) || schedule.getStatus().equals(STATUS.PAST_DUE_60) || schedule.getStatus().equals(STATUS.PAST_DUE_90)).toList();
        logger.info("filter past due schedule:{}", scheduleList.size());

        // after filter the schedule list calculate on the schedule that before current data
//        List<Schedule> newFilterSchedule = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        Double dueCalculation = 0D;
        for (Schedule schedule : scheduleList) {
            if (schedule.getCreateAt().isBefore(now)) {
//                newFilterSchedule.add(schedule);
                dueCalculation += schedule.getDue();
            }
        }


        scheduleAmountResDto.setDue(dueCalculation);
        scheduleAmountResDto.setStatus(scheduleAmountResDto.getStatus());
        scheduleAmountResDto.setSource(scheduleAmountResDto.getSource());

        return scheduleAmountResDto;
    }

    @Override
    public ScheduleAmountDueDto getCalculatedPassedDueByLoanId(String LoanId) {
        return null;
    }
}
