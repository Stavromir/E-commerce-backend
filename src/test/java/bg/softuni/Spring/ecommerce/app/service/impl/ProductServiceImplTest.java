package bg.softuni.Spring.ecommerce.app.service.impl;

import bg.softuni.Spring.ecommerce.app.model.dto.ProductDto;
import bg.softuni.Spring.ecommerce.app.model.entity.ProductEntity;
import bg.softuni.Spring.ecommerce.app.repository.ProductRepository;
import bg.softuni.Spring.ecommerce.app.service.ProductService;
import bg.softuni.Spring.ecommerce.app.service.testUtils.OrderTestDataUtil;
import bg.softuni.Spring.ecommerce.app.service.testUtils.ProductTestDataUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceImplTest {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductTestDataUtil productTestDataUtil;
    @Autowired
    private OrderTestDataUtil orderTestDataUtil;


    @Test
    void testAddProduct() throws IOException {
        ProductDto testProductDto = productTestDataUtil.createProductDto();

        ProductDto returnedProductDto = productService.addProduct(testProductDto);
        Long productId = returnedProductDto.getId();

        ProductEntity product = productRepository.findById(productId).get();

        Assertions.assertNotNull(product);
        Assertions.assertEquals(testProductDto.getName(), product.getName());
        Assertions.assertEquals(testProductDto.getDescription(), product.getDescription());
        Assertions.assertEquals(testProductDto.getPrice(), product.getPrice());
        Assertions.assertEquals(testProductDto.getCategoryId(), product.getCategory().getId());
    }

}