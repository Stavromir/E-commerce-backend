package bg.softuni.Spring.ecommerce.app.service.impl;

import bg.softuni.Spring.ecommerce.app.model.dto.AddProductInCardDto;
import bg.softuni.Spring.ecommerce.app.model.dto.CartItemDto;
import bg.softuni.Spring.ecommerce.app.model.dto.OrderDto;
import bg.softuni.Spring.ecommerce.app.model.entity.*;
import bg.softuni.Spring.ecommerce.app.model.enums.OrderStatusEnum;
import bg.softuni.Spring.ecommerce.app.repository.OrderRepository;
import bg.softuni.Spring.ecommerce.app.service.*;
import bg.softuni.Spring.ecommerce.app.service.exception.ValidationException;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartItemService cartItemService;
    private final ProductService productService;
    private final UserService userService;
    private final CouponService couponService;

    public OrderServiceImpl(OrderRepository orderRepository,
                            CartItemService cartItemService,
                            ProductService productService,
                            @Lazy UserService userService, CouponService couponService) {
        this.orderRepository = orderRepository;
        this.cartItemService = cartItemService;
        this.productService = productService;
        this.userService = userService;
        this.couponService = couponService;
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

        OrderEntity activeOrder = getOrder(addProductInCardDto.getUserId());

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
        OrderEntity activeOrder = getOrder(addProductInCardDto.getUserId());
        return cartItemService.isCartItemPresentInOrder(addProductInCardDto, activeOrder.getId());
    }

    @Override
    public OrderDto getCartByUserId(Long userId) {
        OrderEntity activeOrder = getOrder(userId);

        List<CartItemDto> cartItemDtos = activeOrder.getCartItems()
                .stream()
                .map(cartItemService::mapToCartItemDto)
                .toList();

        OrderDto orderDto = mapToOrderDto(activeOrder, cartItemDtos);

        return orderDto;
    }


    @Override
    public OrderDto applyCoupon(Long userId, String code) {
        OrderEntity activeOrder = getOrder(userId);

        if (couponService.isExpired(code)) {
            throw new ValidationException("Coupon has expired");
        }

        CouponEntity coupon = couponService.findByCode(code);

        double discountAmount = (coupon.getDiscount() / 100.0) * activeOrder.getTotalAmount();
        double netAmount = activeOrder.getTotalAmount() - discountAmount;
        activeOrder.setAmount((long) netAmount);
        activeOrder.setDiscount((long) discountAmount);

        orderRepository.save(activeOrder);

        OrderDto orderDto = getCartByUserId(userId);

        return orderDto;
    }

    private OrderEntity getOrder(Long userId) {
        return orderRepository
                .findByUserIdAndOrderStatus(userId, OrderStatusEnum.PENDING)
                .orElseThrow(() -> new IllegalArgumentException("Order not exist"));
    }

    private static OrderDto mapToOrderDto(OrderEntity activeOrder, List<CartItemDto> cartItemDtos) {
        OrderDto orderDto = new OrderDto()
                .setAmount(activeOrder.getAmount())
                .setId(activeOrder.getId())
                .setOrderStatus(activeOrder.getOrderStatus())
                .setDiscount(activeOrder.getDiscount())
                .setTotalAmount(activeOrder.getTotalAmount())
                .setCartItems(cartItemDtos);

        if (activeOrder.getCoupon() != null) {
            orderDto.setCouponName(activeOrder.getCoupon().getName());
        }

        return orderDto;
    }
}
