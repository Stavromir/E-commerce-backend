package bg.softuni.Spring.ecommerce.app.service.impl;

import bg.softuni.Spring.ecommerce.app.model.dto.*;
import bg.softuni.Spring.ecommerce.app.model.entity.*;
import bg.softuni.Spring.ecommerce.app.model.enums.OrderStatusEnum;
import bg.softuni.Spring.ecommerce.app.repository.OrderRepository;
import bg.softuni.Spring.ecommerce.app.service.*;
import bg.softuni.Spring.ecommerce.app.service.exception.ObjectNotFoundException;
import bg.softuni.Spring.ecommerce.app.utils.DateTime;
import bg.softuni.Spring.ecommerce.app.utils.RandomUUID;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartItemService cartItemService;
    private final ProductService productService;
    private final UserService userService;
    private final CouponService couponService;
    private final DateTime dateTime;
    private final RandomUUID randomUUID;

    public OrderServiceImpl(OrderRepository orderRepository,
                            CartItemService cartItemService,
                            ProductService productService,
                            @Lazy UserService userService,
                            CouponService couponService,
                            DateTime dateTime,
                            RandomUUID randomUUID) {
        this.orderRepository = orderRepository;
        this.cartItemService = cartItemService;
        this.productService = productService;
        this.userService = userService;
        this.couponService = couponService;
        this.dateTime = dateTime;
        this.randomUUID = randomUUID;
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
    public Long addCartItemToActiveOrder(AddProductInCartDto addProductInCardDto) {

        UserEntity user = userService.getUserById(addProductInCardDto.getUserId());

        OrderEntity activeOrder = getOrderWithStatusPending(addProductInCardDto.getUserId());

        ProductEntity product = productService.getProductById(addProductInCardDto.getProductId());

        CartItemEntity cartItem = cartItemService.createCartItem(product, user, activeOrder);

        CartItemEntity savedCartItem = cartItemService.saveCartEntity(cartItem);

        activeOrder.setTotalAmount(activeOrder.getTotalAmount() + cartItem.getPrice());
        activeOrder.setAmount(activeOrder.getAmount() + cartItem.getPrice());
        activeOrder.getCartItems().add(savedCartItem);

        orderRepository.save(activeOrder);

        return savedCartItem.getId();
    }

    @Override
    public OrderDto getPendingOrderByUserId(Long userId) {

        OrderEntity activeOrder = getOrderWithStatusPending(userId);
        return mapToOrderDto(activeOrder);
    }


    @Override
    public OrderDto applyCoupon(Long userId, String code) {
        OrderEntity activeOrder = getOrderWithStatusPending(userId);

        if (couponService.isExpired(code)) {
            throw new ObjectNotFoundException("Coupon has expired");
        }

        CouponEntity coupon = couponService.findByCode(code);
        activeOrder.setCoupon(coupon);

        double discountAmount = (coupon.getDiscount() / 100.0) * activeOrder.getTotalAmount();
        double netAmount = activeOrder.getTotalAmount() - discountAmount;
        activeOrder.setAmount((long) netAmount);
        activeOrder.setDiscount((long) discountAmount);

        orderRepository.save(activeOrder);

        OrderDto orderDto = getPendingOrderByUserId(userId);

        return orderDto;
    }

    @Override
    public Long increaseProductQuantity(AddProductInCartDto addProductInCardDto) {
        OrderEntity activeOrder = getOrderWithStatusPending(addProductInCardDto.getUserId());

        CartItemEntity cartItem = cartItemService
                .findByProductIdAndOrderIdAndUserID(addProductInCardDto, activeOrder.getId());

        cartItem.setQuantity(cartItem.getQuantity() + 1);
        cartItemService.saveCartEntity(cartItem);

        activeOrder.setTotalAmount(activeOrder.getTotalAmount() + cartItem.getPrice());

        if (activeOrder.getCoupon() != null) {
            orderRepository.save(activeOrder);
            applyCoupon(addProductInCardDto.getUserId(), activeOrder.getCoupon().getCode());
            return activeOrder.getId();
        }

        activeOrder.setAmount(activeOrder.getAmount() + cartItem.getPrice());
        orderRepository.save(activeOrder);
        return activeOrder.getId();
    }

    @Override
    public Long decreaseProductQuantity(AddProductInCartDto addProductInCardDto) {
        OrderEntity activeOrder = getOrderWithStatusPending(addProductInCardDto.getUserId());

        CartItemEntity cartItem = cartItemService
                .findByProductIdAndOrderIdAndUserID(addProductInCardDto, activeOrder.getId());

        cartItem.setQuantity(cartItem.getQuantity() - 1);
        cartItemService.saveCartEntity(cartItem);

        activeOrder.setTotalAmount(activeOrder.getTotalAmount() - cartItem.getPrice());

        if (activeOrder.getCoupon() != null) {
            orderRepository.save(activeOrder);
            applyCoupon(addProductInCardDto.getUserId(), activeOrder.getCoupon().getCode());
            return activeOrder.getId();
        }


        activeOrder.setAmount(activeOrder.getAmount() - cartItem.getPrice());
        orderRepository.save(activeOrder);
        return activeOrder.getId();
    }

    @Override
    public Long placeOrder(PlaceOrderDto placeOrderDto) {
        OrderEntity activeOrder = getOrderWithStatusPending(placeOrderDto.getUserId());
        UserEntity user = userService.getUserById(placeOrderDto.getUserId());

        activeOrder.setOrderDescription(placeOrderDto.getOrderDescription());
        activeOrder.setOrderStatus(OrderStatusEnum.PLACED);
        activeOrder.setAddress(placeOrderDto.getAddress());
        activeOrder.setDate(dateTime.getDate());
        activeOrder.setTrackingId(randomUUID.createRandomUUID());

        orderRepository.save(activeOrder);

        createEmptyOrder(user);
        return activeOrder.getId();
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
                .orElseThrow(() -> new ObjectNotFoundException("Order can not be found"));

        if (status.equalsIgnoreCase("Shipped")) {
            order.setOrderStatus(OrderStatusEnum.SHIPPED);
        } else if (status.equalsIgnoreCase("Delivered")) {
            order.setOrderStatus(OrderStatusEnum.DELIVERED);
        } else {
            throw new ObjectNotFoundException("Invalid order status");
        }

        return orderRepository.save(order).getId();
    }

    @Override
    public List<OrderDto> getUserPlacedOrders(Long userId) {
        List<OrderEntity> allPlacedUserOrders = orderRepository.findAllByUserIdAndOrderStatusIn(userId,
                List.of(OrderStatusEnum.PLACED,
                OrderStatusEnum.SHIPPED, OrderStatusEnum.DELIVERED));

        return allPlacedUserOrders
                .stream()
                .map(this::mapToOrderDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto getOrderDtoById(Long id) {
        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Order not exist"));

        return mapToOrderDto(order);
    }

    @Override
    public OrderDto searchOrderByTrackingId(UUID trackingId) {
        OrderEntity order = orderRepository.findByTrackingId(trackingId)
                .orElseThrow(() -> new ObjectNotFoundException("Order not exist"));

        return mapToOrderDto(order);
    }

    @Override
    public AnalyticsResponseDto getAnalytics() {

        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime previousMonthDate = currentDate.minusMonths(1);

        Long currentMonthOrders = (long) getTotalOrdersForMonth(currentDate.getMonthValue(), currentDate.getYear()).size();
        Long previousMonthOrders = (long) getTotalOrdersForMonth(previousMonthDate.getMonthValue(), previousMonthDate.getYear()).size();
        Long currentMonthEarnings = getTotalEarningsForMonth(currentDate.getMonthValue(), currentDate.getYear());
        Long previousMonthEarnings = getTotalEarningsForMonth(previousMonthDate.getMonthValue(), previousMonthDate.getYear());

        Long allPlacedOrders = (long) orderRepository.findAllByOrderStatus(OrderStatusEnum.PLACED).size();
        Long allShippedOrders = (long) orderRepository.findAllByOrderStatus(OrderStatusEnum.SHIPPED).size();
        Long allDeliveredOrders = (long) orderRepository.findAllByOrderStatus(OrderStatusEnum.DELIVERED).size();

        return new AnalyticsResponseDto()
                .setPlacedOrders(allPlacedOrders)
                .setShippedOrders(allShippedOrders)
                .setDeliveredOrders(allDeliveredOrders)
                .setCurrentMonthOrders(currentMonthOrders)
                .setPreviousMonthOrders(previousMonthOrders)
                .setCurrentMonthEarnings(currentMonthEarnings)
                .setPreviousMonthEarnings(previousMonthEarnings);
    }

    private Long getTotalEarningsForMonth(int month, int year) {
        return getTotalOrdersForMonth(month, year)
                .stream()
                .mapToLong(OrderEntity::getAmount)
                .sum();
    }

    private List<OrderEntity> getTotalOrdersForMonth(int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        LocalDateTime beginOfMonth = calendar.getTime().toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDateTime();


        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);

        LocalDateTime endOfMonth = calendar.getTime().toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDateTime();;

        return orderRepository
                .findAllByDateBetweenAndOrderStatus(beginOfMonth, endOfMonth, OrderStatusEnum.DELIVERED);
    }

    private OrderEntity getOrderWithStatusPending(Long userId) {
        return orderRepository
                .findByUserIdAndOrderStatus(userId, OrderStatusEnum.PENDING)
                .orElseThrow(() -> new ObjectNotFoundException("Order not exist"));
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
