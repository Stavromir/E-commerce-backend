package bg.softuni.Spring.ecommerce.app.service.impl;


import bg.softuni.Spring.ecommerce.app.model.dto.CategoryDto;
import bg.softuni.Spring.ecommerce.app.model.dto.FAQDto;
import bg.softuni.Spring.ecommerce.app.model.dto.ProductDto;
import bg.softuni.Spring.ecommerce.app.model.entity.FAQEntity;
import bg.softuni.Spring.ecommerce.app.model.entity.ProductEntity;
import bg.softuni.Spring.ecommerce.app.repository.FAQRepository;
import bg.softuni.Spring.ecommerce.app.repository.ProductRepository;
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
class FAQServiceImplTest {

    public static final String CATEGORY_NAME = "categoryName";
    public static final String CATEGORY_DESCRIPTION = "categoryDescription";
    public static final Long PRODUCT_PRICE = 1000L;
    public static final String FIRST_PRODUCT_NAME = "firstProductName";
    public static final String SECOND_PRODUCT_NAME = "secondProductName";
    public static final String PRODUCT_DESCRIPTION = "productDescription";
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

        ProductDto firstTestProduct = getProductDto(FIRST_PRODUCT_NAME, PRODUCT_DESCRIPTION,
                PRODUCT_PRICE, categoryId);

        Long firstProductId = productService.addProduct(firstTestProduct).getId();

        FAQDto faqDto = getFaqDto(firstProductId);

        FAQEntity faq = faqService.createFAQ(faqDto);

        Assertions.assertNotNull(faq);
        Assertions.assertEquals(faqDto.getAnswer(), faq.getAnswer());
        Assertions.assertEquals(faqDto.getQuestion(), faq.getQuestion());
        Assertions.assertEquals(faqDto.getProductId(), faq.getProduct().getId());
    }

    private static FAQDto getFaqDto(Long firstProductId) {
        return new FAQDto()
                .setQuestion(FIRST_FAQ_QUESTION)
                .setAnswer(FIRST_FAQ_ANSWER)
                .setProductId(firstProductId);
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

    @Test
    void testGetAllFAQs () {



    }



}