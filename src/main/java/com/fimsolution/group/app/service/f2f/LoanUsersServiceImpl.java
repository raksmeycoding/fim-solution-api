package com.fimsolution.group.app.service.f2f;


import com.fimsolution.group.app.constant.business.f2f.loanuser.PRIORITIZE;
import com.fimsolution.group.app.dto.business.f2f.loanuser.LinkLoanUserToUserReqDto;
import com.fimsolution.group.app.dto.business.f2f.loanuser.LoanUserProjection;
import com.fimsolution.group.app.dto.business.f2f.loanuser.LoanUserReqDto;
import com.fimsolution.group.app.dto.business.f2f.loanuser.LoanUserResDto;
import com.fimsolution.group.app.exception.AlreadyExistException;
import com.fimsolution.group.app.exception.NotFoundException;
import com.fimsolution.group.app.mapper.business.f2f.LoanMapper;
import com.fimsolution.group.app.mapper.business.f2f.LoanUserMapper;
import com.fimsolution.group.app.model.business.f2f.Loan;
import com.fimsolution.group.app.model.business.f2f.LoanUser;
import com.fimsolution.group.app.model.business.f2f.User;
import com.fimsolution.group.app.repository.f2f.LoanRepository;
import com.fimsolution.group.app.repository.f2f.LoanUsersRepository;
import com.fimsolution.group.app.repository.f2f.UsersRepository;
import com.fimsolution.group.app.utils.SecurityUtils;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoanUsersServiceImpl implements LoanUsersService {
    private final LoanUsersRepository loanUsersRepository;
    private final UsersRepository usersRepository;
    private final LoanRepository loanRepository;

    @Override
    public LoanUserResDto createLoanUser(LoanUserReqDto loanUserReqDto) {

        /**
         * @Note: This operation only works for admin and user registration. However, this logic will be adjusted
         * when user performing apply to be as lender or  borrower
         * */

        Optional<Loan> optionalLoan = loanRepository.findById(loanUserReqDto.getLoanId());

        if (optionalLoan.isEmpty())
            throw new NotFoundException("loan not found - Require loan creation");


        Optional<User> userWithEmail = usersRepository.findByEmail(loanUserReqDto.getEmail());

        Optional<User> useWithUserId = usersRepository.findById(loanUserReqDto.getUserId());


        if (useWithUserId.isPresent()) {


            /**
             * @Note:
             * */
            loanUsersRepository.findFirstByUserIdAndLoanId(useWithUserId.get().getId(), loanUserReqDto.getLoanId()).ifPresent(loan -> {
                throw new AlreadyExistException("loan user already exist");
            });


            /**
             * @Note:
             * */
            LoanUser loanUser = LoanUserMapper.toEntity(loanUserReqDto);

            loanUser.setLoan(optionalLoan.get());

            loanUser.setUser(useWithUserId.get());


            /**
             * @Note: check whether you have empty loan user
             * */
            if (useWithUserId.get().getLoanUser().isEmpty()) {
                LoanUser saveLoanUser = loanUsersRepository.save(loanUser);

                return LoanUserMapper.toResDto(saveLoanUser);
            }

            /**
             * @Note: if user have from one loan user or up
             * */
            useWithUserId.get().getLoanUser()
                    .forEach(lu -> {
                        if (lu.getPrioritize().equals(PRIORITIZE.DEFAULT)) {

                            if (loanUser.getPrioritize().equals(PRIORITIZE.DEFAULT)) {
                                loanUsersRepository.save(loanUser);

                                lu.setPrioritize(PRIORITIZE.NONE_DEFAULT);
                                loanUsersRepository.save(lu);


                            } else {
                                loanUsersRepository.save(loanUser);
                            }

                        }
                    });


            /**
             * @Note: Verify role in loan user and
             * */


            return LoanUserMapper.toResDto(loanUser);
        }


        if (userWithEmail.isPresent()) {

            /**
             * @Note:
             * */
            loanUsersRepository.findFirstByUserIdAndLoanId(userWithEmail.get().getId(), loanUserReqDto.getLoanId()).ifPresent(loan -> {
                throw new AlreadyExistException("loan user already exist");
            });


            /**
             * @Note:
             * */
            LoanUser loanUser = LoanUserMapper.toEntity(loanUserReqDto);

            loanUser.setLoan(optionalLoan.get());

            loanUser.setUser(userWithEmail.get());


            /**
             * @Note: check whether you have empty loan user
             * */
            if (userWithEmail.get().getLoanUser().isEmpty()) {
                loanUsersRepository.save(loanUser);

                return LoanUserMapper.toResDto(loanUser);
            }

            /**
             * @Note: if user have from one loan user or up
             * */
            userWithEmail.get().getLoanUser()
                    .forEach(lu -> {
                        if (lu.getPrioritize().equals(PRIORITIZE.DEFAULT)) {

                            if (loanUser.getPrioritize().equals(PRIORITIZE.DEFAULT)) {
                                loanUsersRepository.save(loanUser);

                                lu.setPrioritize(PRIORITIZE.NONE_DEFAULT);
                                loanUsersRepository.save(lu);


                            } else {
                                loanUsersRepository.save(loanUser);
                            }

                        }
                    });


            return LoanUserMapper.toResDto(loanUser);
        }


        throw new NotFoundException("User not found. Required user registration.");


    }

    @Override
    public Boolean linkLonUserToUser(LinkLoanUserToUserReqDto linkLoanUserToUserReqDto) {

        Optional<User> optionalUser = usersRepository.findById(linkLoanUserToUserReqDto.getUserId());

        Optional<LoanUser> optionalLoanUser = loanUsersRepository.findById(linkLoanUserToUserReqDto.getLoanUserId());

        if (optionalLoanUser.isEmpty()) {
            throw new NotFoundException("Loan User Not Found");
        }


        if (optionalUser.isEmpty()) {
            throw new NotFoundException("User not found");
        }

        LoanUser loanUser = optionalLoanUser.get();

        loanUser.setUser(optionalUser.get());

        loanUsersRepository.save(loanUser);

        return true;
    }


    @Override
    public List<LoanUserProjection> getLoanUsers() {
        return loanUsersRepository.findAllLoanUserProjects();
    }

    @Override
    public Optional<LoanUserProjection> checkLoanUserHasDefaultLoan() {

        String username = SecurityUtils.getCurrentUser();

        return loanUsersRepository.checkLoanUserHasDefaultLoanOrNot(username);

    }
}
