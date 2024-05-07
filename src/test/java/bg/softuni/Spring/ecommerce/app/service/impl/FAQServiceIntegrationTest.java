package bg.softuni.Spring.ecommerce.app.service.impl;

import bg.softuni.Spring.ecommerce.app.model.dto.FAQDto;
import bg.softuni.Spring.ecommerce.app.model.entity.FAQEntity;
import bg.softuni.Spring.ecommerce.app.service.FAQService;
import bg.softuni.Spring.ecommerce.app.service.testUtils.FAQTestDataUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class FAQServiceIntegrationTest {

    @Autowired
    private FAQService faqService;

    @Autowired
    private FAQTestDataUtil faqTestDataUtil;

    @BeforeEach
    void SetUp() {
        faqTestDataUtil.clearAllTestData();
    }

    @AfterEach
    void tearDown() {
        faqTestDataUtil.clearAllTestData();
    }

    @Test
    void testCreateFaq() {

        FAQDto testFAQ = faqTestDataUtil.createFaqDto();

        FAQEntity createdFAQ = faqService.createFAQ(testFAQ);

        Assertions.assertNotNull(createdFAQ);
        Assertions.assertEquals(testFAQ.getAnswer(), createdFAQ.getAnswer());
        Assertions.assertEquals(testFAQ.getQuestion(), createdFAQ.getQuestion());
        Assertions.assertEquals(testFAQ.getProductId(), createdFAQ.getProduct().getId());
    }

    @Test
    void testGetAllFaqByProductId() {

        FAQEntity testFAQ = faqTestDataUtil.createFaqEntity();
        Long testProductId = testFAQ.getProduct().getId();

        FAQDto firstReturnedFAQ = faqService.getAllFaqByProductId(testProductId).get(0);

        Assertions.assertNotNull(firstReturnedFAQ);
        Assertions.assertEquals(testFAQ.getId(), firstReturnedFAQ.getId());
        Assertions.assertEquals(testFAQ.getQuestion(), firstReturnedFAQ.getQuestion());
        Assertions.assertEquals(testFAQ.getAnswer(), firstReturnedFAQ.getAnswer());

    }
}