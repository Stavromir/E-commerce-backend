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

        CategoryDto categoryDto = new CategoryDto()
                .setName("testCategory")
                .setDescription("testDescription");

        Long categoryId = categoryService.createCategory(categoryDto).getId();

        ProductDto testProduct = new ProductDto()
                .setName("Product")
                .setPrice(1000L)
                .setDescription("Description")
                .setCategoryId(categoryId);

        ProductDto productDto = productService.addProduct(testProduct);

        Long id = productDto.getId();

        FAQDto faqDto = new FAQDto()
                .setQuestion("q")
                .setAnswer("a")
                .setProductId(id);

        FAQEntity faq = faqService.createFAQ(faqDto);

        Assertions.assertNotNull(faq);
        Assertions.assertEquals(faqDto.getAnswer(), faq.getAnswer());
        Assertions.assertEquals(faqDto.getQuestion(), faq.getQuestion());
        Assertions.assertEquals(faqDto.getProductId(), faq.getProduct().getId());

    }



}