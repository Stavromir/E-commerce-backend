package bg.softuni.Spring.ecommerce.app.service.impl;

import bg.softuni.Spring.ecommerce.app.model.dto.AddProductInCartDto;
import bg.softuni.Spring.ecommerce.app.model.entity.CartItemEntity;
import bg.softuni.Spring.ecommerce.app.model.entity.OrderEntity;
import bg.softuni.Spring.ecommerce.app.model.entity.ProductEntity;
import bg.softuni.Spring.ecommerce.app.model.entity.UserEntity;
import bg.softuni.Spring.ecommerce.app.repository.CartItemRepository;
import bg.softuni.Spring.ecommerce.app.service.CartItemService;
import bg.softuni.Spring.ecommerce.app.service.exception.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CartItemServiceImplTest {

    public static final Long PRODUCT_ID = 1L;
    public static final Long ORDER_ID = 2L;
    public static final Long USER_ID = 3L;


    private CartItemService cartItemServiceToTest;

    @Mock
    private CartItemRepository cartItemRepository;

    @BeforeEach
    void setUp() {
        this.cartItemServiceToTest = new CartItemServiceImpl(cartItemRepository);
    }


    @Test
    void testFindCartItemByProductIdOrderIdAndUserId() {
        //Arrange
        CartItemEntity testCartItem = createTestCartItem();

        Mockito.when(cartItemRepository.findByUserIdAndProductIdAndOrderId(USER_ID, PRODUCT_ID, ORDER_ID))
                .thenReturn(Optional.of(testCartItem));
        AddProductInCartDto productInCartDto = createProductInCardDto();

        //Act
        CartItemEntity cartItem = cartItemServiceToTest
                .findByProductIdAndOrderIdAndUserID(productInCartDto, testCartItem.getOrder().getId());

        //Assert
        assertNotNull(cartItem);

        assertEquals(testCartItem.getProduct().getId(), cartItem.getProduct().getId());
        assertEquals(testCartItem.getOrder().getId(), cartItem.getOrder().getId());
        assertEquals(testCartItem.getUser().getId(), cartItem.getUser().getId());
    }

    @Test
    void testNotFoundCartItemByProductIdOrderIdAndUserId() {
        AddProductInCartDto productInCartDto = createProductInCardDto();

        assertThrows(
                ObjectNotFoundException.class,
                () -> cartItemServiceToTest.findByProductIdAndOrderIdAndUserID(productInCartDto, ORDER_ID)
        );
    }

    @Test
    void testCartItemIsPresentInOrder() {
        AddProductInCartDto productInCartDto = createProductInCardDto();

        CartItemEntity testCartItem = createTestCartItem();

        Mockito.when(cartItemRepository.findByUserIdAndProductIdAndOrderId(USER_ID, PRODUCT_ID, ORDER_ID))
                .thenReturn(Optional.of(testCartItem));

        assertTrue(cartItemServiceToTest.isCartItemPresentInOrder(productInCartDto, testCartItem.getOrder().getId()));
    }

    private AddProductInCartDto createProductInCardDto() {
        return new AddProductInCartDto()
                .setUserId(USER_ID)
                .setProductId(PRODUCT_ID);
    }

    private CartItemEntity createTestCartItem() {
        ProductEntity testProduct = new ProductEntity();
        testProduct.setId(PRODUCT_ID);
        OrderEntity testOrder = new OrderEntity();
        testOrder.setId(ORDER_ID);
        UserEntity testUser = new UserEntity();
        testUser.setId(USER_ID);

        return new CartItemEntity()
                .setProduct(testProduct)
                .setOrder(testOrder)
                .setUser(testUser);
    }
}