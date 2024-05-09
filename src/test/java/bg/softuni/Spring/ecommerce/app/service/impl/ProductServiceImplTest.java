package bg.softuni.Spring.ecommerce.app.service.impl;

import bg.softuni.Spring.ecommerce.app.repository.ProductRepository;
import bg.softuni.Spring.ecommerce.app.service.ProductService;
import bg.softuni.Spring.ecommerce.app.service.testUtils.OrderTestDataUtil;
import bg.softuni.Spring.ecommerce.app.service.testUtils.ProductTestDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

}