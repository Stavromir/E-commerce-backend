package bg.softuni.Spring.ecommerce.app.service.impl;


import bg.softuni.Spring.ecommerce.app.model.dto.FAQDto;
import bg.softuni.Spring.ecommerce.app.model.dto.ProductDto;
import bg.softuni.Spring.ecommerce.app.model.entity.FAQEntity;
import bg.softuni.Spring.ecommerce.app.model.entity.ProductEntity;
import bg.softuni.Spring.ecommerce.app.repository.FAQRepository;
import bg.softuni.Spring.ecommerce.app.repository.ProductRepository;
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
    private ProductRepository productRepository;

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

        ProductDto testProduct = new ProductDto()
                .setName("Product")
                .setPrice(1000L)
                .setDescription("Description");

        productRepository.save()

        FAQDto faqDto = new FAQDto()
                .setQuestion("q")
                .setAnswer("a")
                .setProductId(productId);

        FAQEntity faq = faqService.createFAQ(faqDto);

        Assertions.assertNotNull(faq);

    }



}