//package com.fimsolution.group.app.integration.repository;
//
//
//import com.fimsolution.group.app.model.security.UserCredential;
//import com.fimsolution.group.app.repository.UserCredentialRepository;
//import jakarta.transaction.Transactional;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
//import org.testng.annotations.Test;
//
//import java.util.Optional;
//
//
//
//@ActiveProfiles("dev")
//@SpringBootTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//public class UserCredentialRepositoryTest  extends AbstractTestNGSpringContextTests {
//    private final static Logger LOGGER = LoggerFactory.getLogger(UserCredentialRepositoryTest.class);
//
//
//    @Autowired
//   private UserCredentialRepository userCredentialRepository;
//
//
//
//    @Test
//    @Transactional
//    public void retrieve_userCredential_and_its_related_entity_by_username() {
//        LOGGER.info(":::Start Testing:::");
//        String userName = "johndoe";
//        Optional<UserCredential> optionalUserCredentials = userCredentialRepository.findByUsername(userName);
//
//        if (optionalUserCredentials.isPresent()) {
//            LOGGER.info(":::Testing Result:::{}", optionalUserCredentials.get().toString());
//            LOGGER.info(":::Testing Result Nested:::{}", optionalUserCredentials.get().getUserRole().toString());
//
//        } else {
//            LOGGER.warn("UserCredentials not found for username: {}", userName);
//        }
//        LOGGER.info(":::Testing Result:::{}", optionalUserCredentials.get().toString());
//
//    }
//
//}
