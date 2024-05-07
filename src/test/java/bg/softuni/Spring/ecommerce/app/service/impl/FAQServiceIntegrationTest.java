package bg.softuni.Spring.ecommerce.app.service.impl;


import bg.softuni.Spring.ecommerce.app.model.dto.CategoryDto;
import bg.softuni.Spring.ecommerce.app.model.dto.FAQDto;
import bg.softuni.Spring.ecommerce.app.model.dto.ProductDto;
import bg.softuni.Spring.ecommerce.app.model.entity.CategoryEntity;
import bg.softuni.Spring.ecommerce.app.model.entity.FAQEntity;
import bg.softuni.Spring.ecommerce.app.model.entity.ProductEntity;
import bg.softuni.Spring.ecommerce.app.repository.FAQRepository;
import bg.softuni.Spring.ecommerce.app.service.CategoryService;
import bg.softuni.Spring.ecommerce.app.service.FAQService;
import bg.softuni.Spring.ecommerce.app.service.ProductService;
import bg.softuni.Spring.ecommerce.app.service.testUtils.CategoryTestDataUtil;
import bg.softuni.Spring.ecommerce.app.service.testUtils.ProductTestDataUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class FAQServiceIntegrationTest {

    public static final String FIRST_FAQ_QUESTION = "firstFaqQuestion";
    public static final String FIRST_FAQ_ANSWER = "firstFaqAnswer";


    @Autowired
    private FAQService faqService;

    @Autowired
    private FAQRepository faqRepository;

    @Autowired
    private ProductTestDataUtil productTestDataUtil;

    @BeforeEach
    void SetUp() {
        faqRepository.deleteAll();
        productTestDataUtil.clearAllTestData();
    }

    @AfterEach
    void tearDown() {
        faqRepository.deleteAll();
        productTestDataUtil.clearAllTestData();
    }

    @Test
    void testCreateFaq () throws IOException {

        ProductEntity testProduct = productTestDataUtil.createProduct();
        Long productId = testProduct.getId();

        FAQDto testFAQ = getFaqDto(FIRST_FAQ_QUESTION, FIRST_FAQ_ANSWER, productId);

        FAQEntity createdFAQ = faqService.createFAQ(testFAQ);

        Assertions.assertNotNull(createdFAQ);
        Assertions.assertEquals(testFAQ.getAnswer(), createdFAQ.getAnswer());
        Assertions.assertEquals(testFAQ.getQuestion(), createdFAQ.getQuestion());
        Assertions.assertEquals(testFAQ.getProductId(), createdFAQ.getProduct().getId());
    }

    @Test
    void testGetAllFaqByProductId () throws IOException {

        ProductEntity testProduct = productTestDataUtil.createProduct();
        Long testProductId = testProduct.getId();

        FAQDto firstTestFAQ = getFaqDto(FIRST_FAQ_QUESTION, FIRST_FAQ_ANSWER, testProductId);

        FAQEntity firstCreatedFAQ = faqService.createFAQ(firstTestFAQ);

        FAQDto firstReturnedFAQ = faqService.getAllFaqByProductId(testProductId).get(0);

        Assertions.assertNotNull(firstReturnedFAQ);
        Assertions.assertEquals(firstCreatedFAQ.getId(), firstReturnedFAQ.getId());
        Assertions.assertEquals(firstCreatedFAQ.getQuestion(), firstReturnedFAQ.getQuestion());
        Assertions.assertEquals(firstCreatedFAQ.getAnswer(), firstReturnedFAQ.getAnswer());

    }

    private static FAQDto getFaqDto(String question, String answer,Long productId) {
        return new FAQDto()
                .setQuestion(question)
                .setAnswer(answer)
                .setProductId(productId);
    }
}