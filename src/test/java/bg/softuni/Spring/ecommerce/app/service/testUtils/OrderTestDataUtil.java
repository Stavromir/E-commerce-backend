package bg.softuni.Spring.ecommerce.app.service.testUtils;

import bg.softuni.Spring.ecommerce.app.model.entity.CartItemEntity;
import bg.softuni.Spring.ecommerce.app.model.entity.OrderEntity;
import bg.softuni.Spring.ecommerce.app.model.entity.ProductEntity;
import bg.softuni.Spring.ecommerce.app.model.entity.UserEntity;
import bg.softuni.Spring.ecommerce.app.model.enums.OrderStatusEnum;
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
    private UserTestDataUtil userTestDataUtil;
    @Autowired
    private ProductTestDataUtil productTestDataUtil;

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

        List<CartItemEntity> cartItemEntities = List.of(new CartItemEntity()
                .setUser(emptyOrder.getUser())
                .setOrder(emptyOrder)
                .setPrice(testProduct.getPrice())
                .setQuantity(1L)
                .setProduct(testProduct));

        OrderEntity filledOrder = emptyOrder
                .setAmount(testProduct.getPrice())
                .setTotalAmount(testProduct.getPrice())
                .setCartItems(cartItemEntities);

        return orderRepository.save(filledOrder);
    }

    public void clearAllTestData() {
        orderRepository.deleteAll();
        userTestDataUtil.clearAllTestData();
        productTestDataUtil.clearAllTestData();
    }


}
