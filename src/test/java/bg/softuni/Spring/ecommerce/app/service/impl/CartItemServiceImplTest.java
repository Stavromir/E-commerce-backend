package bg.softuni.Spring.ecommerce.app.service.impl;

import bg.softuni.Spring.ecommerce.app.model.entity.CartItemEntity;
import bg.softuni.Spring.ecommerce.app.model.entity.OrderEntity;
import bg.softuni.Spring.ecommerce.app.model.entity.ProductEntity;
import bg.softuni.Spring.ecommerce.app.model.entity.UserEntity;
import bg.softuni.Spring.ecommerce.app.repository.CartItemRepository;
import bg.softuni.Spring.ecommerce.app.service.CartItemService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CartItemServiceImplTest {

    private CartItemService cartItemServiceToTest;

    @Mock
    private CartItemRepository cartItemRepository;


    void testFindCartItemByProductIdOrderIdAndUserId() {
        CartItemEntity testCartItem = createTestCartItem();

        Mockito.when(cartItemRepository.findByUserIdAndProductIdAndOrderId(3L,1L, 2L));


    }

    private CartItemEntity createTestCartItem() {
        ProductEntity testProduct = new ProductEntity();
        testProduct.setId(1L);
        OrderEntity testOrder = new OrderEntity();
        testOrder.setId(2L);
        UserEntity testUser = new UserEntity();
        testUser.setId(3L);

        return new CartItemEntity()
                .setProduct(testProduct)
                .setOrder(testOrder)
                .setUser(testUser);
    }
}