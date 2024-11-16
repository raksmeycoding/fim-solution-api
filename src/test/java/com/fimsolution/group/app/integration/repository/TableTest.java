//package com.fimsolution.group.app.integration.repository;
//
//
//import com.fimsolution.group.app.model.business.test.Borrower;
//import jakarta.persistence.EntityManager;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
//import org.testng.Assert;
//import org.testng.annotations.Test;
//
//@DataJpaTest
//@ActiveProfiles("dev")
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//public class TableTest extends AbstractTestNGSpringContextTests {
//
//    @Autowired
//    private EntityManager entityManager;
//
//
//    @Test
//    public void testTableExists() {
//        // Check if a specific table exists in the database
//        Long count = (Long) entityManager.createNativeQuery(
//                "SELECT COUNT(*) FROM borrowers "
//        ).getSingleResult();
//
//        Assert.assertEquals(count.intValue(), 0, "Table 'borrowers' should exist in the database.");
//    }
//
//
//
//}
