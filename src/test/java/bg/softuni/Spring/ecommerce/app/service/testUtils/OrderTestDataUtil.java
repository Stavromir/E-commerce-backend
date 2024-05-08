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

    public OrderEntity createFilledOrder() {
        ProductEntity testProduct = productTestDataUtil.createProduct();

        OrderEntity emptyOrder = createEmptyOrder();

        CartItemEntity testCertItem = new CartItemEntity()
                .setUser(emptyOrder.getUser())
                .setOrder(emptyOrder)
                .setPrice(testProduct.getPrice())
                .setQuantity(1L)
                .setProduct(testProduct);

        CartItemEntity savedCartItem = cartItemRepository.save(testCertItem);

        List<CartItemEntity> cartItemEntities = List.of(savedCartItem);

        OrderEntity filledOrder = emptyOrder
                .setAmount(testProduct.getPrice())
                .setTotalAmount(testProduct.getPrice())
                .setCartItems(cartItemEntities);

        return orderRepository.save(filledOrder);
    }

    public void clearAllTestData() {
        orderRepository.deleteAll();
        cartItemRepository.deleteAll();
        userTestDataUtil.clearAllTestData();
        productTestDataUtil.clearAllTestData();
        couponTestDataUtil.clearAllTestData();
    }


}
