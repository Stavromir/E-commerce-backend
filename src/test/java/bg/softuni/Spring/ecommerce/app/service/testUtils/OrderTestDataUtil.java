package bg.softuni.Spring.ecommerce.app.service.testUtils;

import bg.softuni.Spring.ecommerce.app.model.dto.PlaceOrderDto;
import bg.softuni.Spring.ecommerce.app.model.entity.*;
import bg.softuni.Spring.ecommerce.app.model.enums.OrderStatusEnum;
import bg.softuni.Spring.ecommerce.app.repository.CartItemRepository;
import bg.softuni.Spring.ecommerce.app.repository.OrderRepository;
import bg.softuni.Spring.ecommerce.app.utils.RandomUUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderTestDataUtil {

    public static final Long INITIAL_PRODUCT_QUANTITY = 1L;
    public static final Long INCREASED_PRODUCT_QUANTITY = 2L;
    public static final String ORDER_ADDRESS = "testAddress";
    public static final String ORDER_DESCRIPTION = "testDescription";

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private UserTestDataUtil userTestDataUtil;
    @Autowired
    private ProductTestDataUtil productTestDataUtil;
    @Autowired
    private RandomUUID randomUUID;

    public OrderEntity createEmptyOrder() {
        UserEntity testUser = userTestDataUtil.getTestUserInstance();

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

    public OrderEntity createOrderWithStatusPlaced() {
        OrderEntity order = createEmptyOrder()
                .setOrderStatus(OrderStatusEnum.PLACED);

        return orderRepository.save(order);
    }

    public OrderEntity createOrderWithStatusDelivered() {
        OrderEntity order = createEmptyOrder()
                .setOrderStatus(OrderStatusEnum.DELIVERED);

        return orderRepository.save(order);
    }

    public OrderEntity createOrderWithStatusShipped() {
        OrderEntity order = createEmptyOrder()
                .setOrderStatus(OrderStatusEnum.SHIPPED);

        return orderRepository.save(order);
    }

    public OrderEntity createOrderWithTrackingNumber() {
        OrderEntity order = createEmptyOrder()
                .setTrackingId(randomUUID.createRandomUUID());

        return orderRepository.save(order);
    }

    public PlaceOrderDto createPlaceOrderDto(Long userId) {
        return new PlaceOrderDto()
                .setUserId(userId)
                .setAddress(ORDER_ADDRESS)
                .setOrderDescription(ORDER_DESCRIPTION);
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
        cartItemRepository.deleteAll();
        orderRepository.deleteAll();
    }
}
