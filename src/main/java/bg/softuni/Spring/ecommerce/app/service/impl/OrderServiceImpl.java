package bg.softuni.Spring.ecommerce.app.service.impl;

import bg.softuni.Spring.ecommerce.app.model.dto.AddProductInCardDto;
import bg.softuni.Spring.ecommerce.app.model.dto.CartItemDto;
import bg.softuni.Spring.ecommerce.app.model.dto.OrderDto;
import bg.softuni.Spring.ecommerce.app.model.entity.CartItemEntity;
import bg.softuni.Spring.ecommerce.app.model.entity.OrderEntity;
import bg.softuni.Spring.ecommerce.app.model.entity.ProductEntity;
import bg.softuni.Spring.ecommerce.app.model.entity.UserEntity;
import bg.softuni.Spring.ecommerce.app.model.enums.OrderStatusEnum;
import bg.softuni.Spring.ecommerce.app.repository.OrderRepository;
import bg.softuni.Spring.ecommerce.app.service.CartItemService;
import bg.softuni.Spring.ecommerce.app.service.OrderService;
import bg.softuni.Spring.ecommerce.app.service.ProductService;
import bg.softuni.Spring.ecommerce.app.service.UserService;
import org.hibernate.query.Order;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartItemService cartItemService;
    private final ProductService productService;
    private final UserService userService;

    public OrderServiceImpl(OrderRepository orderRepository,
                            CartItemService cartItemService,
                            ProductService productService,
                            @Lazy UserService userService) {
        this.orderRepository = orderRepository;
        this.cartItemService = cartItemService;
        this.productService = productService;
        this.userService = userService;
    }

    @Override
    public void createEmptyOrder(UserEntity user) {

        OrderEntity emptyOrder = new OrderEntity()
                .setAmount(0L)
                .setTotalAmount(0L)
                .setDiscount(0L)
                .setUser(user)
                .setOrderStatus(OrderStatusEnum.PENDING);

        orderRepository.save(emptyOrder);
    }

    @Override
    public Long addProductToCart(AddProductInCardDto addProductInCardDto) {

        OrderEntity activeOrder = getOrderEntity(addProductInCardDto.getUserId());

        ProductEntity product = productService.getProductById(addProductInCardDto.getProductId());
        UserEntity user = userService.getUserById(addProductInCardDto.getUserId());


        CartItemEntity cartItem = new CartItemEntity()
                .setProduct(product)
                .setPrice(product.getPrice())
                .setQuantity(1L)
                .setUser(user)
                .setOrder(activeOrder);

        CartItemEntity updatedCart = cartItemService.saveCartEntity(cartItem);

        activeOrder.setTotalAmount(activeOrder.getTotalAmount() + cartItem.getPrice());
        activeOrder.setAmount(activeOrder.getAmount() + cartItem.getPrice());
        activeOrder.getCartItems().add(updatedCart);

        orderRepository.save(activeOrder);

        return updatedCart.getId();
    }

    @Override
    public boolean isCartItemPresent(AddProductInCardDto addProductInCardDto) {
        OrderEntity activeOrder = getOrderEntity(addProductInCardDto.getUserId());
        return cartItemService.isCartItemPresentInOrder(addProductInCardDto, activeOrder.getId());
    }

    @Override
    public OrderDto getCartByUserId(Long userId) {
        OrderEntity activeOrder = getOrderEntity(userId);

        List<CartItemDto> cartItemDtos = activeOrder.getCartItems()
                .stream()
                .map(cartItemService::mapToCartItemDto)
                .toList();

        OrderDto orderDto = new OrderDto()
                .setAmount(activeOrder.getAmount())
                .setId(activeOrder.getId())
                .setOrderStatus(activeOrder.getOrderStatus())
                .setDiscount(activeOrder.getDiscount())
                .setTotalAmount(activeOrder.getTotalAmount())
                .setCartItems(cartItemDtos);

        return orderDto;
    }

    private OrderEntity getOrderEntity(Long userId) {
        return orderRepository
                .findByUserIdAndOrderStatus(userId, OrderStatusEnum.PENDING)
                .orElseThrow(() -> new IllegalArgumentException("Order not exist"));
    }

}
