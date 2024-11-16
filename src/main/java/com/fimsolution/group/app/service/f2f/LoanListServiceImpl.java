package com.fimsolution.group.app.service.f2f;

import com.fimsolution.group.app.dto.business.f2f.loan.LoanResDto;
import com.fimsolution.group.app.dto.business.f2f.loanList.LoanListResDto;
import com.fimsolution.group.app.exception.NotFoundException;
import com.fimsolution.group.app.mapper.business.f2f.LoanMapper;
import com.fimsolution.group.app.mapper.business.f2f.LoanUserMapper;
import com.fimsolution.group.app.model.business.f2f.Loan;
import com.fimsolution.group.app.model.business.f2f.LoanUser;
import com.fimsolution.group.app.model.business.f2f.User;
import com.fimsolution.group.app.model.security.UserDetailsImpl;
import com.fimsolution.group.app.repository.f2f.LoanUsersRepository;
import com.fimsolution.group.app.repository.f2f.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class LoanListServiceImpl implements LoanListService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final LoanUsersRepository loanUsersRepository;
    private final UsersRepository usersRepository;

    @Override
    public LoanListResDto getLoanListsForCurrentUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        logger.info(":::Current User:::{}", userDetails);

        List<Loan> loanList = new ArrayList<>();

        Optional<User> userOptional = usersRepository.findByEmail(userDetails.getUsername());

        if (userOptional.isEmpty()) {
            throw new NotFoundException("User not found");
        }

        List<LoanResDto> loanResDtoList = userOptional.get().getLoanUser().stream().map(LoanUser::getLoan).map(LoanMapper::toResDto).toList();


        return LoanListResDto.builder().loanResDtoList(loanResDtoList).build();

    }
}
