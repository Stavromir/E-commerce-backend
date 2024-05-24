package bg.softuni.Spring.ecommerce.app.service.impl;

import bg.softuni.Spring.ecommerce.app.model.dto.AddProductInCartDto;
import bg.softuni.Spring.ecommerce.app.model.dto.OrderDto;
import bg.softuni.Spring.ecommerce.app.model.dto.PlaceOrderDto;
import bg.softuni.Spring.ecommerce.app.model.entity.*;
import bg.softuni.Spring.ecommerce.app.model.enums.OrderStatusEnum;
import bg.softuni.Spring.ecommerce.app.repository.OrderRepository;
import bg.softuni.Spring.ecommerce.app.service.OrderService;
import bg.softuni.Spring.ecommerce.app.service.exception.ObjectNotFoundException;
import bg.softuni.Spring.ecommerce.app.service.testUtils.CouponTestDataUtil;
import bg.softuni.Spring.ecommerce.app.service.testUtils.OrderTestDataUtil;
import bg.softuni.Spring.ecommerce.app.service.testUtils.ProductTestDataUtil;
import bg.softuni.Spring.ecommerce.app.utils.DateTime;
import bg.softuni.Spring.ecommerce.app.utils.RandomUUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@SpringBootTest
class OrderServiceIntegrationTest {

    public static final String ORDER_STATUS_SHIPPED = "Shipped";
    public static final String ORDER_STATUS_DELIVERED = "Delivered";


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

    @MockBean
    private DateTime dateTime;
    @MockBean
    private RandomUUID randomUUID;

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

        Optional<OrderEntity> optionalActiveOrder = orderRepository.findByUserIdAndOrderStatus(testUserId,
                OrderStatusEnum.PENDING);

        Assertions.assertTrue(optionalActiveOrder.isPresent());
        OrderEntity activeOrder = optionalActiveOrder.get();
        Assertions.assertEquals(testProduct.getPrice(), activeOrder.getAmount());
        Assertions.assertEquals(testProduct.getPrice(), activeOrder.getTotalAmount());
        Assertions.assertEquals(1, activeOrder.getCartItems().size());
    }

    @Test
    void testApplyValidCoupon() {
        CouponEntity testCoupon = couponTestDataUtil.createValidCouponEntity();
        OrderEntity filledOrder = orderTestDataUtil.createFilledOrderInitialQuantity();

        Long amountAfterCouponApply = 900L;
        Long discountAfterCouponApply = 100L;

        Long testUserId = filledOrder.getUser().getId();
        String couponCode = testCoupon.getCode();

        orderService.applyCoupon(testUserId, couponCode);

        Optional<OrderEntity> optionalOrderById = orderRepository.findById(filledOrder.getId());
        Assertions.assertTrue(optionalOrderById.isPresent());
        OrderEntity orderAfterCouponApply = optionalOrderById.get();

        Assertions.assertNotNull(orderAfterCouponApply.getCoupon());
        Assertions.assertEquals(testCoupon.getCode(), orderAfterCouponApply.getCoupon().getCode());
        Assertions.assertEquals(testCoupon.getDiscount(), orderAfterCouponApply.getCoupon().getDiscount());
        Assertions.assertEquals(amountAfterCouponApply, orderAfterCouponApply.getAmount());
        Assertions.assertEquals(filledOrder.getTotalAmount(), orderAfterCouponApply.getTotalAmount());
        Assertions.assertEquals(discountAfterCouponApply, orderAfterCouponApply.getDiscount());
    }

    @Test
    void testApplyExpiredCouponThrowExc() {
        CouponEntity testCoupon = couponTestDataUtil.createExpiredCouponEntity();
        OrderEntity filledOrder = orderTestDataUtil.createFilledOrderInitialQuantity();

        Long testUserId = filledOrder.getUser().getId();
        String couponCode = testCoupon.getCode();

        Assertions.assertThrows(
                ObjectNotFoundException.class,
                () -> orderService.applyCoupon(testUserId, couponCode)
        );
    }

    @Test
    void testIncreaseProductQuantityWithCoupon() {
        CouponEntity testCoupon = couponTestDataUtil.createValidCouponEntity();
        OrderEntity testOrder = orderTestDataUtil.createFilledOrderInitialQuantity();
        testOrder.setCoupon(testCoupon);
        orderRepository.save(testOrder);

        Long testUserId = testOrder.getUser().getId();
        CartItemEntity testCartItem = testOrder.getCartItems().get(0);
        Long testProductId = testCartItem.getProduct().getId();

        AddProductInCartDto addProductInCartDto =
                getAddProductInCartDto(testProductId, testUserId);

        orderService.increaseProductQuantity(addProductInCartDto);

        Optional<OrderEntity> optionalOrderById = orderRepository.findById(testOrder.getId());
        Assertions.assertTrue(optionalOrderById.isPresent());

        OrderEntity savedOrder = optionalOrderById.get();
        CartItemEntity savedCartItem = savedOrder.getCartItems().get(0);

        Assertions.assertEquals(testCartItem.getQuantity() + 1, savedCartItem.getQuantity());
        Assertions.assertEquals(testOrder.getTotalAmount() * 2, savedOrder.getTotalAmount());
        Assertions.assertEquals(1800, savedOrder.getAmount());
    }

    @Test
    void testIncreaseProductQuantityWithoutCoupon() {
        OrderEntity testOrder = orderTestDataUtil.createFilledOrderInitialQuantity();

        Long testUserId = testOrder.getUser().getId();
        CartItemEntity testCartItem = testOrder.getCartItems().get(0);
        Long testProductId = testCartItem.getProduct().getId();

        AddProductInCartDto addProductInCartDto =
                getAddProductInCartDto(testProductId, testUserId);

        orderService.increaseProductQuantity(addProductInCartDto);

        Optional<OrderEntity> optionalOrderById = orderRepository.findById(testOrder.getId());
        Assertions.assertTrue(optionalOrderById.isPresent());

        OrderEntity savedOrder = optionalOrderById.get();
        CartItemEntity savedCartItem = savedOrder.getCartItems().get(0);

        Assertions.assertEquals(testCartItem.getQuantity() + 1, savedCartItem.getQuantity());
        Assertions.assertEquals(testOrder.getTotalAmount() * 2, savedOrder.getTotalAmount());
        Assertions.assertEquals(testOrder.getAmount() * 2, savedOrder.getAmount());
    }

    @Test
    void testDecreaseProductQuantityWithCoupon() {
        CouponEntity testCoupon = couponTestDataUtil.createValidCouponEntity();
        OrderEntity testOrder = orderTestDataUtil.createFilledOrderIncreasedQuantity();
        testOrder.setCoupon(testCoupon);
        orderRepository.save(testOrder);

        Long testUserId = testOrder.getUser().getId();
        CartItemEntity testCartItem = testOrder.getCartItems().get(0);
        Long testProductId = testCartItem.getProduct().getId();

        AddProductInCartDto addProductInCartDto =
                getAddProductInCartDto(testProductId, testUserId);

        orderService.decreaseProductQuantity(addProductInCartDto);

        Optional<OrderEntity> optionalOrderById = orderRepository.findById(testOrder.getId());
        Assertions.assertTrue(optionalOrderById.isPresent());
        OrderEntity savedOrder = optionalOrderById.get();
        CartItemEntity savedCartItem = savedOrder.getCartItems().get(0);

        Assertions.assertEquals(testCartItem.getQuantity() - 1, savedCartItem.getQuantity());
        Assertions.assertEquals(testOrder.getTotalAmount() / 2, savedOrder.getTotalAmount());
        Assertions.assertEquals(900, savedOrder.getAmount());
    }

    @Test
    void testDecreaseProductQuantityWithoutCoupon() {
        OrderEntity testOrder = orderTestDataUtil.createFilledOrderIncreasedQuantity();

        Long testUserId = testOrder.getUser().getId();
        CartItemEntity testCartItem = testOrder.getCartItems().get(0);
        Long testProductId = testCartItem.getProduct().getId();

        AddProductInCartDto addProductInCartDto =
                getAddProductInCartDto(testProductId, testUserId);

        orderService.decreaseProductQuantity(addProductInCartDto);

        Optional<OrderEntity> optionalOrderById = orderRepository.findById(testOrder.getId());
        CartItemEntity savedCartItem = savedOrder.getCartItems().get(0);

        Assertions.assertEquals(testCartItem.getQuantity() - 1, savedCartItem.getQuantity());
        Assertions.assertEquals(testOrder.getTotalAmount() / 2, savedOrder.getTotalAmount());
        Assertions.assertEquals(testOrder.getAmount() / 2, savedOrder.getAmount());
    }

    @Test
    void testPlaceOrder() {

        LocalDateTime mockedDate = LocalDateTime.now();
        Mockito.when(dateTime.getDate()).thenReturn(mockedDate);

        UUID mockedUUID = UUID.randomUUID();
        Mockito.when(randomUUID.createRandomUUID()).thenReturn(mockedUUID);

        OrderEntity emptyOrder = orderTestDataUtil.createEmptyOrder();

        PlaceOrderDto placeOrderDto = orderTestDataUtil
                .createPlaceOrderDto(emptyOrder.getUser().getId());

        orderService.placeOrder(placeOrderDto);

        OrderEntity savedOrder = orderRepository.findById(emptyOrder.getId()).get();
        OrderEntity newEmptyOrder = orderRepository.
                findByUserIdAndOrderStatus(emptyOrder.getUser().getId(), OrderStatusEnum.PENDING).get();

        Assertions.assertEquals(placeOrderDto.getOrderDescription(), savedOrder.getOrderDescription());
        Assertions.assertEquals(OrderStatusEnum.PLACED, savedOrder.getOrderStatus());
        Assertions.assertEquals(placeOrderDto.getAddress(), savedOrder.getAddress());
        Assertions.assertEquals(mockedUUID, savedOrder.getTrackingId());
//        Assertions.assertEquals(mockedDate.toInstant(), savedOrder.getDate().toInstant());

        Assertions.assertNotNull(newEmptyOrder);
    }

    @Test
    void testGetAllPlacedOrdersStatusPlaced() {
        OrderEntity testOrder = orderTestDataUtil.createOrderWithStatusPlaced();

        List<OrderDto> allPlacedOrders = orderService.getAllPlacedOrders();
        Assertions.assertEquals(1, allPlacedOrders.size());
        Assertions.assertEquals(testOrder.getOrderStatus(), allPlacedOrders.get(0).getOrderStatus());
    }

    @Test
    void testGetAllPlacedOrdersStatusDelivered() {
        OrderEntity testOrder = orderTestDataUtil.createOrderWithStatusDelivered();

        List<OrderDto> allPlacedOrders = orderService.getAllPlacedOrders();
        Assertions.assertEquals(1, allPlacedOrders.size());
        Assertions.assertEquals(testOrder.getOrderStatus(), allPlacedOrders.get(0).getOrderStatus());
    }

    @Test
    void testGetAllPlacedOrdersStatusShipped() {
        OrderEntity testOrder = orderTestDataUtil.createOrderWithStatusShipped();

        List<OrderDto> allPlacedOrders = orderService.getAllPlacedOrders();
        Assertions.assertEquals(1, allPlacedOrders.size());
        Assertions.assertEquals(testOrder.getOrderStatus(), allPlacedOrders.get(0).getOrderStatus());
    }

    @Test
    void testChangeOrderStatusToShipped() {
        OrderEntity testOrder = orderTestDataUtil.createOrderWithStatusPlaced();

        orderService.changeOrderStatus(testOrder.getId(), ORDER_STATUS_SHIPPED);
        OrderEntity savedOrder = orderRepository.findById(testOrder.getId()).get();

        Assertions.assertEquals(OrderStatusEnum.SHIPPED, savedOrder.getOrderStatus());
    }

    @Test
    void testChangeOrderStatusToDelivered() {
        OrderEntity testOrder = orderTestDataUtil.createOrderWithStatusPlaced();

        orderService.changeOrderStatus(testOrder.getId(), ORDER_STATUS_DELIVERED);
        OrderEntity savedOrder = orderRepository.findById(testOrder.getId()).get();

        Assertions.assertEquals(OrderStatusEnum.DELIVERED, savedOrder.getOrderStatus());
    }

    @Test
    void testChangeOrderStatusThrowExcOrderNotExist() {
        Assertions.assertThrows(
                ObjectNotFoundException.class,
                () -> orderService.changeOrderStatus(100L, "STATUS")
        );
    }

    @Test
    void testChangeOrderStatusThrowExcInvalidStatus() {
        OrderEntity testOrder = orderTestDataUtil.createOrderWithStatusPlaced();

        Assertions.assertThrows(
                ObjectNotFoundException.class,
                () -> orderService.changeOrderStatus(testOrder.getId(), "wrongStatus")
        );
    }

    @Test
    void testGetUserPlacedOrdersStatusPlaced() {
        OrderEntity testOrder = orderTestDataUtil.createOrderWithStatusPlaced();
        Long userId = testOrder.getUser().getId();

        List<OrderDto> userPlacedOrders = orderService.getUserPlacedOrders(userId);
        Assertions.assertEquals(1, userPlacedOrders.size());
        Assertions.assertEquals(OrderStatusEnum.PLACED, userPlacedOrders.get(0).getOrderStatus());
    }
    @Test
    void testGetUserPlacedOrdersStatusDelivered() {
        OrderEntity testOrder = orderTestDataUtil.createOrderWithStatusDelivered();
        Long userId = testOrder.getUser().getId();

        List<OrderDto> userPlacedOrders = orderService.getUserPlacedOrders(userId);
        Assertions.assertEquals(1, userPlacedOrders.size());
        Assertions.assertEquals(OrderStatusEnum.DELIVERED, userPlacedOrders.get(0).getOrderStatus());
    }

    @Test
    void testGetUserPlacedOrdersStatusShipped() {
        OrderEntity testOrder = orderTestDataUtil.createOrderWithStatusShipped();
        Long userId = testOrder.getUser().getId();

        List<OrderDto> userPlacedOrders = orderService.getUserPlacedOrders(userId);
        Assertions.assertEquals(1, userPlacedOrders.size());
        Assertions.assertEquals(OrderStatusEnum.SHIPPED, userPlacedOrders.get(0).getOrderStatus());
    }

    @Test
    void testGetOrderDtoById() {
        OrderEntity testOrder = orderTestDataUtil.createFilledOrderInitialQuantity();

        OrderDto orderDtoById = orderService.getOrderDtoById(testOrder.getId());
        Assertions.assertEquals(testOrder.getId(), orderDtoById.getId());
    }

    @Test
    void testGetOrderDtoByIdThrowExc() {
        Assertions.assertThrows(
                ObjectNotFoundException.class,
                () -> orderService.getOrderDtoById(101L)
        );
    }

    @Test
    void testSearchOrderByTrackingId() {
        UUID mockedUUID = UUID.randomUUID();
        Mockito.when(randomUUID.createRandomUUID()).thenReturn(mockedUUID);

        OrderEntity testOrder = orderTestDataUtil.createOrderWithTrackingNumber();
        OrderDto orderDto = orderService.searchOrderByTrackingId(mockedUUID);

        Assertions.assertEquals(testOrder.getId(), orderDto.getId());
    }

    @Test
    void testSearchOrderByTrackingIdThrowExc() {
        UUID randomUUID = UUID.randomUUID();

        Assertions.assertThrows(
                ObjectNotFoundException.class,
                () -> orderService.searchOrderByTrackingId(randomUUID)
        );
    }

    private static AddProductInCartDto getAddProductInCartDto(Long testProductId, Long testUserId) {
        return new AddProductInCartDto()
                .setProductId(testProductId)
                .setUserId(testUserId);
    }
}