package bg.softuni.Spring.ecommerce.app.service.impl;

import bg.softuni.Spring.ecommerce.app.model.dto.AddProductInCartDto;
import bg.softuni.Spring.ecommerce.app.model.dto.OrderDto;
import bg.softuni.Spring.ecommerce.app.model.entity.CouponEntity;
import bg.softuni.Spring.ecommerce.app.model.entity.OrderEntity;
import bg.softuni.Spring.ecommerce.app.model.entity.ProductEntity;
import bg.softuni.Spring.ecommerce.app.model.entity.UserEntity;
import bg.softuni.Spring.ecommerce.app.model.enums.OrderStatusEnum;
import bg.softuni.Spring.ecommerce.app.repository.OrderRepository;
import bg.softuni.Spring.ecommerce.app.service.OrderService;
import bg.softuni.Spring.ecommerce.app.service.testUtils.CouponTestDataUtil;
import bg.softuni.Spring.ecommerce.app.service.testUtils.OrderTestDataUtil;
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

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductTestDataUtil productTestDataUtil;
    @Autowired
    private OrderTestDataUtil orderTestDataUtil;
    @Autowired
    private CouponTestDataUtil couponTestDataUtil;

    @BeforeEach
    void setUp() {
        productTestDataUtil.clearAllTestData();
        orderTestDataUtil.clearAllTestData();
        couponTestDataUtil.clearAllTestData();
    }

    @AfterEach
    void tearDown() {
        productTestDataUtil.clearAllTestData();
        orderTestDataUtil.clearAllTestData();
        couponTestDataUtil.clearAllTestData();
    }


    @Test
    void testAddCartItemToOrder() {

        OrderEntity emptyOrder = orderTestDataUtil.createEmptyOrder();
        Long testUserId = emptyOrder.getUser().getId();

        ProductEntity testProduct = productTestDataUtil.createProduct();

        AddProductInCartDto addProductInCartDto = new AddProductInCartDto()
                .setUserId(testUserId)
                .setProductId(testProduct.getId());

        orderService.addCartItemToActiveOrder(addProductInCartDto);

        OrderEntity activeOrder = orderRepository.findByUserIdAndOrderStatus(testUserId,
                OrderStatusEnum.PENDING).get();

        Assertions.assertEquals(testProduct.getPrice(), activeOrder.getAmount());
        Assertions.assertEquals(testProduct.getPrice(), activeOrder.getTotalAmount());
        Assertions.assertEquals(1, activeOrder.getCartItems().size());
    }

    @Test
    void testApplyCoupon() {
        CouponEntity testCoupon = couponTestDataUtil.createValidCouponEntity();
        OrderEntity filledOrder = orderTestDataUtil.createFilledOrder();

        Long amountAfterCouponApply = 900L;
        Long discountAfterCouponApply = 100L;

        Long testUserId = filledOrder.getUser().getId();
        String couponCode = testCoupon.getCode();

        orderService.applyCoupon(testUserId, couponCode);

        OrderEntity orderAfterCouponApply = orderRepository.findById(filledOrder.getId()).get();

        Assertions.assertNotNull(orderAfterCouponApply.getCoupon());
        Assertions.assertEquals(testCoupon.getCode(), orderAfterCouponApply.getCoupon().getCode());
        Assertions.assertEquals(testCoupon.getDiscount(), orderAfterCouponApply.getCoupon().getDiscount());
        Assertions.assertEquals(amountAfterCouponApply, orderAfterCouponApply.getAmount());
        Assertions.assertEquals(filledOrder.getTotalAmount(), orderAfterCouponApply.getTotalAmount());
        Assertions.assertEquals(discountAfterCouponApply, orderAfterCouponApply.getDiscount());
    }
}