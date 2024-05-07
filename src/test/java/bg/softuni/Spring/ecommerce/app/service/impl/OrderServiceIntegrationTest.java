package bg.softuni.Spring.ecommerce.app.service.impl;

import bg.softuni.Spring.ecommerce.app.model.dto.AddProductInCartDto;
import bg.softuni.Spring.ecommerce.app.model.entity.OrderEntity;
import bg.softuni.Spring.ecommerce.app.model.entity.ProductEntity;
import bg.softuni.Spring.ecommerce.app.model.entity.UserEntity;
import bg.softuni.Spring.ecommerce.app.model.enums.OrderStatusEnum;
import bg.softuni.Spring.ecommerce.app.repository.OrderRepository;
import bg.softuni.Spring.ecommerce.app.service.OrderService;
import bg.softuni.Spring.ecommerce.app.service.testUtils.ProductTestDataUtil;
import bg.softuni.Spring.ecommerce.app.service.testUtils.UserTestDataUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class OrderServiceIntegrationTest {

    public static final String USER_EMAIL = "user@email.com";

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserTestDataUtil userTestDataUtil;
    @Autowired
    private ProductTestDataUtil productTestDataUtil;

    @BeforeEach
    void setUp() {
        orderRepository.deleteAll();
        userTestDataUtil.clearAllTestData();
        productTestDataUtil.clearAllTestData();
    }

    @AfterEach
    void tearDown() {
        orderRepository.deleteAll();
        userTestDataUtil.clearAllTestData();
        productTestDataUtil.clearAllTestData();
    }


    @Test
    void testAddCartItemToOrder() {
        UserEntity testUser = userTestDataUtil.createTestUser(USER_EMAIL);
        ProductEntity testProduct = productTestDataUtil.createProduct();

        AddProductInCartDto addProductInCartDto = new AddProductInCartDto()
                .setUserId(testUser.getId())
                .setProductId(testProduct.getId());

        orderService.createEmptyOrder(testUser);

        orderService.addCartItemToActiveOrder(addProductInCartDto);

        OrderEntity activeOrder = orderRepository.findByUserIdAndOrderStatus(testUser.getId(),
                OrderStatusEnum.PENDING).get();

        Assertions.assertEquals(testProduct.getPrice(), activeOrder.getAmount());
        Assertions.assertEquals(testProduct.getPrice(), activeOrder.getTotalAmount());
        Assertions.assertEquals(1, activeOrder.getCartItems().size());
    }
}