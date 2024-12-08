//package com.fimsolution.group.app.unit.utils;
//
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
//import org.testng.Assert;
//import org.testng.annotations.Test;
//
//@SpringBootTest
//@ActiveProfiles("dev")
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//public class PasswordEncoderTest extends AbstractTestNGSpringContextTests {
//
//    private final static Logger logger = LoggerFactory.getLogger(PasswordEncoderTest.class);
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//
//
//    @Test
//    public void test_encoding_password() {
//
//        logger.info(":::Password encoding test:::");
//        String password1 = "Another$Example2";
//        String password2 = "Another$Example2";
//        String password3 = "Another$Example2";
//
//
//        String encodedPassword1 = passwordEncoder.encode(password1);
//        logger.info("encodedPassword1:::{}", encodedPassword1);
//        String encodedPassword2 = passwordEncoder.encode(password2);
//        logger.info("encodedPassword2:::{}", encodedPassword2);
//        String encodedPassword3 = passwordEncoder.encode(password3);
//        logger.info("encodedPassword3:::{}", encodedPassword3);
//
//
//        Assert.assertNotNull(encodedPassword1);
//        Assert.assertNotNull(encodedPassword2);
//        Assert.assertNotNull(encodedPassword2);
//    }
//}
