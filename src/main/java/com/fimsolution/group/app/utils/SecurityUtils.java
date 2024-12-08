package com.fimsolution.group.app.utils;


import com.fimsolution.group.app.model.security.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SecurityUtils {
    private final static Logger logger = LoggerFactory.getLogger(SecurityUtils.class);


    public static String getCurrentUser() {

        try {
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
            return username;
        } catch (Exception e) {
            logger.info("Get current username:");
            logger.error(e.getMessage());
            throw new SecurityException("Failed to get current user username");
        }
    }


    public static List<String> getRoles() {
        try {
            UserDetailsImpl userDetails = getCurrentUserDetails();
            List<String> userRoles = new ArrayList<>();
            userRoles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
            return userRoles;
        } catch (Exception e) {
            logger.info("Get current user roles failed");
            logger.error(e.getMessage());
            throw new SecurityException("Get current user roles failed");
        }
    }


    public static UserDetailsImpl getCurrentUserDetails() {
        try {
            logger.info(":::getCurrentUserDetails:::");
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return (UserDetailsImpl) authentication.getPrincipal();
        } catch (Exception e) {
            logger.info("Get current user details failed");
            logger.error(e.getMessage());
            throw new SecurityException("Get current user details failed");
        }
    }
}
