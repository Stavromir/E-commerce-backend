package bg.softuni.Spring.ecommerce.app.service.testUtils;

import bg.softuni.Spring.ecommerce.app.model.entity.*;
import bg.softuni.Spring.ecommerce.app.model.enums.OrderStatusEnum;
import bg.softuni.Spring.ecommerce.app.repository.CartItemRepository;
import bg.softuni.Spring.ecommerce.app.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderTestDataUtil {

    public static final String USER_EMAIL = "user@email.com";
    public static final Long INITIAL_PRODUCT_QUANTITY = 1L;
    public static final Long INCREASED_PRODUCT_QUANTITY = 2L;

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserTestDataUtil userTestDataUtil;
    @Autowired
    private ProductTestDataUtil productTestDataUtil;
    @Autowired
    private CouponTestDataUtil couponTestDataUtil;

    public OrderEntity createEmptyOrder() {
        UserEntity testUser = userTestDataUtil.createTestUser(USER_EMAIL);

        OrderEntity emptyOrder = new OrderEntity()
                .setAmount(0L)
                .setTotalAmount(0L)
                .setDiscount(0L)
                .setUser(testUser)
                .setOrderStatus(OrderStatusEnum.PENDING);

        return orderRepository.save(emptyOrder);
    }

    public OrderEntity createFilledOrderInitialQuantity() {
        return createFilledOrder(INITIAL_PRODUCT_QUANTITY);
    }

    public OrderEntity createFilledOrderIncreasedQuantity() {
        return createFilledOrder(INCREASED_PRODUCT_QUANTITY);
    }

    public OrderEntity createFilledOrderStatusPlaced() {
        OrderEntity order = createEmptyOrder()
                .setOrderStatus(OrderStatusEnum.PLACED);

        return orderRepository.save(order);
    }

    public OrderEntity createFilledOrderStatusDelivered() {
        OrderEntity order = createEmptyOrder()
                .setOrderStatus(OrderStatusEnum.DELIVERED);

        return orderRepository.save(order);
    }

    public OrderEntity createFilledOrderStatusShipped() {
        OrderEntity order = createEmptyOrder()
                .setOrderStatus(OrderStatusEnum.SHIPPED);

        return orderRepository.save(order);
    }

    private OrderEntity createFilledOrder(Long productQuantity) {
        ProductEntity testProduct = productTestDataUtil.createProduct();

        OrderEntity emptyOrder = createEmptyOrder();

        CartItemEntity testCertItem = getCartItemEntity(emptyOrder,
                testProduct, productQuantity);

        CartItemEntity savedCartItem = cartItemRepository.save(testCertItem);

        List<CartItemEntity> cartItemEntities = List.of(savedCartItem);

        OrderEntity filledOrder = emptyOrder
                .setAmount(testProduct.getPrice() * productQuantity)
                .setTotalAmount(testProduct.getPrice() * productQuantity)
                .setCartItems(cartItemEntities);

        return orderRepository.save(filledOrder);
    }

    private static CartItemEntity getCartItemEntity(OrderEntity emptyOrder,
                                                    ProductEntity testProduct,
                                                    Long productQuantity) {
        return new CartItemEntity()
                .setUser(emptyOrder.getUser())
                .setOrder(emptyOrder)
                .setPrice(testProduct.getPrice())
                .setQuantity(productQuantity)
                .setProduct(testProduct);
    }

    public void clearAllTestData() {
        orderRepository.deleteAll();
        cartItemRepository.deleteAll();
        userTestDataUtil.clearAllTestData();
        productTestDataUtil.clearAllTestData();
        couponTestDataUtil.clearAllTestData();
    }


}
