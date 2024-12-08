//package com.fimsolution.group.app.unit.utils;
//
//import com.fimsolution.group.app.annotation.PasswordValidator;
//import jakarta.validation.ConstraintValidatorContext;
//import org.junit.jupiter.api.BeforeEach;
//import org.mockito.Mockito;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.DataProvider;
//import org.testng.annotations.Test;
//
//import static org.testng.Assert.assertFalse;
//import static org.testng.Assert.assertTrue;
//
//
//@SpringBootTest
//@ActiveProfiles("dev")
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//public class PasswordValidatorTest {
//
//    private PasswordValidator passwordValidator;
//    private ConstraintValidatorContext context;
//
//    @BeforeClass
//    public void setUp() {
//        passwordValidator = new PasswordValidator();
//        context = Mockito.mock(ConstraintValidatorContext.class);
//    }
//
//
//    @Test
//    public void testValidPasswords() {
//        String[] validPasswords = {
//                "StrongPass1!",
//                "Another$Example2",
//                "Test@1234",
//                "SecurePassword123!",
//                "Valid1Password!"
//        };
//
//        for (String password : validPasswords) {
//            assertTrue(passwordValidator.isValid(password, context),
//                    "Expected valid password but got invalid for: " + password);
//        }
//    }
//
//    @Test
//    public void testInvalidPasswords() {
//        String[] invalidPasswords = {
//                "12345678",                // Only digits
//                "abcdefgh",                // Only lowercase
//                "ABCDEFGH",                // Only uppercase
//                "abc123$$$",               // No uppercase letter
//                "ABC123$$$",               // No lowercase letter
//                "ABC$$$$$$",               // No digit
//                "java REGEX 123",          // No special character
//                "java REGEX 123 %",        // Invalid special character
//                "________",                // No valid characters
//                "--------",                // No valid characters
//                " ",                       // Empty space
//                "",                        // Empty string
//        };
//
//        for (String password : invalidPasswords) {
//            assertFalse(passwordValidator.isValid(password, context),
//                    "Expected invalid password but got valid for: " + password);
//        }
//    }
//
//
//}
