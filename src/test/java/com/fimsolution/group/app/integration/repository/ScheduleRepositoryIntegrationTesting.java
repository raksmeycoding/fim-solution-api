package com.fimsolution.group.app.integration.repository;


import com.fimsolution.group.app.constant.LOAN_TYPE;
import com.fimsolution.group.app.constant.business.f2f.loanuser.PRIORITIZE;
import com.fimsolution.group.app.constant.business.f2f.loanuser.ROLE;
import com.fimsolution.group.app.constant.business.f2f.loanuser.TYPE;
import com.fimsolution.group.app.constant.business.f2f.schedule.DELINQUENCY;
import com.fimsolution.group.app.constant.business.f2f.schedule.SOURCE;
import com.fimsolution.group.app.constant.business.f2f.schedule.STATUS;
import com.fimsolution.group.app.model.business.f2f.Loan;
import com.fimsolution.group.app.model.business.f2f.LoanUser;
import com.fimsolution.group.app.model.business.f2f.Schedule;
import com.fimsolution.group.app.model.business.f2f.User;
import com.fimsolution.group.app.repository.f2f.LoanRepository;
import com.fimsolution.group.app.repository.f2f.LoanUsersRepository;
import com.fimsolution.group.app.repository.f2f.ScheduleRepository;
import com.fimsolution.group.app.repository.f2f.UsersRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class ScheduleRepositoryIntegrationTesting {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private LoanUsersRepository loanUsersRepository;

    @Autowired
    private UsersRepository usersRepository;


    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Loan loan;
    private User user;
    private Schedule schedule;
    private LoanUser loanUser;
    private List<Schedule> scheduleListTest = new ArrayList<>();

    @BeforeEach
    public void setUp() {

        loan = Loan.builder()
                .nickName("Nick name loan")
                .interest(300.0)
                .loanType(LOAN_TYPE.FIXED)
                .amount(200.0)
                .fee(20.0)
                .note("This loan must be critical")
                .createdDate(LocalDateTime.now())
                .endDate(LocalDateTime.now())
                .build();

        user = User.builder()
                .email("raksmeykoung1@gmail.com")
                .familyName("raksmeykoung")
                .givenName("raksmeykoung")
                .middleName("raksmeykoung")
                .build();

        loanUser = LoanUser.builder()
                .memo("Memo")
                .role(ROLE.BORROWER)
                .loanName("LoanUserName")
                .type(TYPE.ACCESS)
                .email("raksmeykoung@gmail.com")
                .prioritize(PRIORITIZE.DEFAULT)
                .build();

        Schedule newOldTestSchedule = createSchedule(LocalDateTime.of(2024, 1, 18, 4, 20));

        for (int i = 0; i <= 5; i++) {
            scheduleListTest.add(createSchedule(LocalDateTime.now().plusMonths(i + 1)));
        }

        scheduleListTest.add(newOldTestSchedule);


    }

    @Test
    @Transactional
    @Rollback(false)
    public void testGetScheduleByDefaultLoanAndUserEmailOrUserId() {
        // Test logic to verify the repository method
        Loan SaveLoan = loanRepository.save(loan);
        logger.info("Save loan: " + SaveLoan);

        User saveUser = usersRepository.save(user);
        logger.info("Save user: " + saveUser);

        loanUser.setUser(user);
        loanUser.setLoan(loan);

        LoanUser saveLoanUser = loanUsersRepository.save(loanUser);
        logger.info("Save loan user: " + saveLoanUser);


        for (Schedule schedule : scheduleListTest) {
            schedule.setLoan(loan);
            scheduleRepository.save(schedule);
            logger.info("Save schedule:{} ", schedule);
        }


        Optional<Schedule> amountDueScheduleOptional =
                scheduleRepository.findAllScheduleByDefaultLoanAndUserEmailAndSourceAndDue("raksmeykoung@gmail.com");
        amountDueScheduleOptional.ifPresentOrElse(
                (present) -> {
                    logger.info("Schedule is presented:{} ", present);
                }, () -> {
                    logger.info("Schedule is not presented");
                }
        );

        assertThat(amountDueScheduleOptional.isPresent()).isTrue();
    }

    @Test
    public void testNoScheduleFound() {
        // Test when no schedule is found for a non-existent user
        String email = "nonexistent@example.com";
        String userId = "nonexistent-user-id";

//        Optional<Schedule> scheduleOptional = scheduleRepository.getScheduleByDefaultLoanAndUserEmailOrUserId(email, userId);
//
//        assertFalse(scheduleOptional.isPresent(), "Schedule should not be found for a non-existent user");
    }

    private Schedule createSchedule(LocalDateTime endOfLoan) {
        return Schedule.builder()
                .createAt(endOfLoan)
                .amount(400.0)
                .adjustment(200.0)
                .paid(400.0)
                .due(2400.0)
                .interest(300.0)
                .principle(200.0)
                .fimFee(100.0)
                .balance(300.0)
                .source(SOURCE.BORROWER)
                .status(STATUS.FUTURE)
                .delinquency(DELINQUENCY.DEFAULT)
                .memo("Memo")
                .build();
    }

    public List<Schedule> getScheduleListTest() {
        return scheduleListTest;
    }

    public void setScheduleListTest(List<Schedule> scheduleListTest) {
        this.scheduleListTest = scheduleListTest;
    }
}
