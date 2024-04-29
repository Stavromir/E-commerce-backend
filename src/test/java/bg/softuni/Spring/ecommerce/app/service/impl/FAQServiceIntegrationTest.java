package bg.softuni.Spring.ecommerce.app.service.impl;


import bg.softuni.Spring.ecommerce.app.model.dto.CategoryDto;
import bg.softuni.Spring.ecommerce.app.model.dto.FAQDto;
import bg.softuni.Spring.ecommerce.app.model.dto.ProductDto;
import bg.softuni.Spring.ecommerce.app.model.entity.FAQEntity;
import bg.softuni.Spring.ecommerce.app.repository.FAQRepository;
import bg.softuni.Spring.ecommerce.app.service.CategoryService;
import bg.softuni.Spring.ecommerce.app.service.FAQService;
import bg.softuni.Spring.ecommerce.app.service.ProductService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class FAQServiceIntegrationTest {

    public static final String CATEGORY_NAME = "categoryName";
    public static final String CATEGORY_DESCRIPTION = "categoryDescription";
    public static final Long PRODUCT_PRICE = 1000L;
    public static final String FIRST_PRODUCT_NAME = "firstProductName";
    public static final String FIRST_PRODUCT_DESCRIPTION = "firstProductDescription";
    public static final String FIRST_FAQ_QUESTION = "firstFaqQuestion";
    public static final String SECOND_FAQ_QUESTION = "secondFaqQuestion";
    public static final String FIRST_FAQ_ANSWER = "firstFaqAnswer";
    public static final String SECOND_FAQ_ANSWER = "secondFaqAnswer";

    @Autowired
    private FAQService faqService;

    @Autowired
    private FAQRepository faqRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @BeforeEach
    void SetUp() {
        faqRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        faqRepository.deleteAll();
    }

    @Test
    void testCreateFaq () throws IOException {

        CategoryDto categoryDto = getCategoryDto();

        Long categoryId = categoryService.createCategory(categoryDto).getId();

        ProductDto testProduct = getProductDto(FIRST_PRODUCT_NAME, FIRST_PRODUCT_DESCRIPTION,
                PRODUCT_PRICE, categoryId);

        Long productId = productService.addProduct(testProduct).getId();

        FAQDto testFAQ = getFaqDto(FIRST_FAQ_QUESTION, FIRST_FAQ_ANSWER, productId);

        FAQEntity createdFAQ = faqService.createFAQ(testFAQ);

        Assertions.assertNotNull(createdFAQ);
        Assertions.assertEquals(testFAQ.getAnswer(), createdFAQ.getAnswer());
        Assertions.assertEquals(testFAQ.getQuestion(), createdFAQ.getQuestion());
        Assertions.assertEquals(testFAQ.getProductId(), createdFAQ.getProduct().getId());
    }

    @Test
    void testGetAllFaqByProductId () throws IOException {
        CategoryDto categoryDto = getCategoryDto();
        Long categoryId = categoryService.createCategory(categoryDto).getId();

        ProductDto testProduct = getProductDto(FIRST_PRODUCT_NAME, FIRST_PRODUCT_DESCRIPTION,
                PRODUCT_PRICE, categoryId);

        Long testProductId = productService.addProduct(testProduct).getId();

        FAQDto firstTestFAQ = getFaqDto(FIRST_FAQ_QUESTION, FIRST_FAQ_ANSWER, testProductId);
        FAQDto secondTestFAQ = getFaqDto(SECOND_FAQ_QUESTION, SECOND_FAQ_ANSWER, testProductId);

        FAQEntity firstCreatedFAQ = faqService.createFAQ(firstTestFAQ);
        FAQEntity secondCreatedFAQ = faqService.createFAQ(secondTestFAQ);

        FAQDto firstReturnedFAQ = faqService.getAllFaqByProductId(testProductId).get(0);
        FAQDto secondReturnedFAQ = faqService.getAllFaqByProductId(testProductId).get(1);

        Assertions.assertNotNull(firstReturnedFAQ);
        Assertions.assertEquals(firstCreatedFAQ.getId(), firstReturnedFAQ.getId());
        Assertions.assertEquals(firstCreatedFAQ.getQuestion(), firstReturnedFAQ.getQuestion());
        Assertions.assertEquals(firstCreatedFAQ.getAnswer(), firstReturnedFAQ.getAnswer());


        Assertions.assertNotNull(secondReturnedFAQ);
        Assertions.assertEquals(secondCreatedFAQ.getId(), secondReturnedFAQ.getId());
        Assertions.assertEquals(secondCreatedFAQ.getQuestion(), secondReturnedFAQ.getQuestion());
        Assertions.assertEquals(secondCreatedFAQ.getAnswer(), secondReturnedFAQ.getAnswer());
    }

    private static FAQDto getFaqDto(String question, String answer,Long productId) {
        return new FAQDto()
                .setQuestion(question)
                .setAnswer(answer)
                .setProductId(productId);
    }

    private static ProductDto getProductDto(String name, String description,
                                            Long price,Long categoryId) {

        return new ProductDto()
                .setName(name)
                .setPrice(price)
                .setDescription(description)
                .setCategoryId(categoryId);
    }

    private static CategoryDto getCategoryDto() {
        return new CategoryDto()
                .setName(CATEGORY_NAME)
                .setDescription(CATEGORY_DESCRIPTION);
    }



}