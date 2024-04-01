package bg.softuni.Spring.ecommerce.app.service.impl;

import bg.softuni.Spring.ecommerce.app.model.dto.AddProductInCardDto;
import bg.softuni.Spring.ecommerce.app.model.dto.CartItemDto;
import bg.softuni.Spring.ecommerce.app.model.dto.OrderDto;
import bg.softuni.Spring.ecommerce.app.model.dto.PlaceOrderDto;
import bg.softuni.Spring.ecommerce.app.model.entity.*;
import bg.softuni.Spring.ecommerce.app.model.enums.OrderStatusEnum;
import bg.softuni.Spring.ecommerce.app.repository.OrderRepository;
import bg.softuni.Spring.ecommerce.app.service.*;
import bg.softuni.Spring.ecommerce.app.service.exception.ValidationException;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
        OrderDto orderDto = mapToOrderDto(activeOrder);

        return orderDto;
    }


    @Override
    public OrderDto applyCoupon(Long userId, String code) {
        OrderEntity activeOrder = getOrder(userId);

        if (couponService.isExpired(code)) {
            throw new ValidationException("Coupon has expired");
        }

        CouponEntity coupon = couponService.findByCode(code);
        activeOrder.setCoupon(coupon);

        double discountAmount = (coupon.getDiscount() / 100.0) * activeOrder.getTotalAmount();
        double netAmount = activeOrder.getTotalAmount() - discountAmount;
        activeOrder.setAmount((long) netAmount);
        activeOrder.setDiscount((long) discountAmount);

        orderRepository.save(activeOrder);

        OrderDto orderDto = getCartByUserId(userId);

        return orderDto;
    }

    @Override
    public Long increaseProductQuantity(AddProductInCardDto addProductInCardDto) {
        OrderEntity activeOrder = getOrder(addProductInCardDto.getUserId());

        if (isCartItemPresent(addProductInCardDto)) {

            CartItemEntity cartItem = cartItemService
                    .findByProductIdAndOrderIdAndUserID(addProductInCardDto, activeOrder.getId()).get();

            cartItem.setQuantity(cartItem.getQuantity() + 1);
            activeOrder.setTotalAmount(activeOrder.getTotalAmount() + cartItem.getPrice());

            if (activeOrder.getCoupon() != null) {
                applyCoupon(addProductInCardDto.getUserId(), activeOrder.getCoupon().getCode());
            } else {
                activeOrder.setAmount(activeOrder.getAmount() + cartItem.getPrice());
            }

            orderRepository.save(activeOrder);
            return activeOrder.getId();
        } else {
            throw new ValidationException("Cart / Product not found");
        }
    }

    @Override
    public Long decreaseProductQuantity(AddProductInCardDto addProductInCardDto) {
        OrderEntity activeOrder = getOrder(addProductInCardDto.getUserId());

        if (isCartItemPresent(addProductInCardDto)) {

            CartItemEntity cartItem = cartItemService
                    .findByProductIdAndOrderIdAndUserID(addProductInCardDto, activeOrder.getId()).get();

            cartItem.setQuantity(cartItem.getQuantity() - 1);
            activeOrder.setTotalAmount(activeOrder.getTotalAmount() - cartItem.getPrice());

            if (activeOrder.getCoupon() != null) {
                applyCoupon(addProductInCardDto.getUserId(), activeOrder.getCoupon().getCode());
            } else {
                activeOrder.setAmount(activeOrder.getAmount() - cartItem.getPrice());
            }

            orderRepository.save(activeOrder);
            return activeOrder.getId();
        } else {
            throw new ValidationException("Cart / Product not found");
        }
    }

    @Override
    public Long placeOrder(PlaceOrderDto placeOrderDto) {
        OrderEntity activeOrder = getOrder(placeOrderDto.getUserId());

        if (userService.existById(placeOrderDto.getUserId())) {

            activeOrder.setOrderDescription(placeOrderDto.getOrderDescription());
            activeOrder.setOrderStatus(OrderStatusEnum.PLACED);
            activeOrder.setAddress(placeOrderDto.getAddress());
            activeOrder.setDate(new Date());
            activeOrder.setTrackingId(UUID.randomUUID());

            orderRepository.save(activeOrder);

            createEmptyOrder(userService.getUserById(placeOrderDto.getUserId()));
            return activeOrder.getId();
        } else {
            throw new ValidationException("User not exist");
        }
    }

    @Override
    public List<OrderDto> getAllPlacedOrders() {
        List<OrderEntity> allByOrderStatusIn = orderRepository.findAllByOrderStatusIn(
                List.of(OrderStatusEnum.PLACED,
                        OrderStatusEnum.DELIVERED,
                        OrderStatusEnum.SHIPPED));

        return allByOrderStatusIn
                .stream()
                .map(this::mapToOrderDto)
                .collect(Collectors.toList());
    }

    @Override
    public Long changeOrderStatus(Long orderId, String status) {
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ValidationException("Invalid order"));

        if (status.equalsIgnoreCase("Shipped")) {
            order.setOrderStatus(OrderStatusEnum.SHIPPED);
        } else if (status.equalsIgnoreCase("Delivered")) {
            order.setOrderStatus(OrderStatusEnum.DELIVERED);
        } else {
            throw new ValidationException("Invalid order status");
        }

        return order.getId();
    }

    private OrderEntity getOrder(Long userId) {
        return orderRepository
                .findByUserIdAndOrderStatus(userId, OrderStatusEnum.PENDING)
                .orElseThrow(() -> new IllegalArgumentException("Order not exist"));
    }

    private OrderDto mapToOrderDto(OrderEntity activeOrder) {

        List<CartItemDto> cartItemDtos = activeOrder.getCartItems()
                .stream()
                .map(cartItemService::mapToCartItemDto)
                .toList();

        OrderDto orderDto = new OrderDto()
                .setId(activeOrder.getId())
                .setAmount(activeOrder.getAmount())
                .setOrderDescription(activeOrder.getOrderDescription())
                .setDate(activeOrder.getDate())
                .setAddress(activeOrder.getAddress())
                .setPayment(activeOrder.getPayment())
                .setTrackingId(activeOrder.getTrackingId())
                .setOrderStatus(activeOrder.getOrderStatus())
                .setDiscount(activeOrder.getDiscount())
                .setTotalAmount(activeOrder.getTotalAmount())
                .setCartItems(cartItemDtos)
                .setUserName(activeOrder.getUser().getName());

        if (activeOrder.getCoupon() != null) {
            orderDto.setCouponName(activeOrder.getCoupon().getName());
        }

        return orderDto;
    }
}
