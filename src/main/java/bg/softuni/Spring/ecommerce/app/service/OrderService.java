package bg.softuni.Spring.ecommerce.app.service;

import bg.softuni.Spring.ecommerce.app.model.dto.AddProductInCartDto;
import bg.softuni.Spring.ecommerce.app.model.dto.AnalyticsResponseDto;
import bg.softuni.Spring.ecommerce.app.model.dto.OrderDto;
import bg.softuni.Spring.ecommerce.app.model.dto.PlaceOrderDto;
import bg.softuni.Spring.ecommerce.app.model.entity.UserEntity;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    void createEmptyOrder(UserEntity user);

    Long addCartItemToActiveOrder(AddProductInCartDto addProductInCardDto);

    OrderDto getCartByUserId(Long userId);

    OrderDto applyCoupon(Long userId, String code);

    Long increaseProductQuantity(AddProductInCartDto addProductInCardDto);

    Long decreaseProductQuantity(AddProductInCartDto addProductInCardDto);

    Long placeOrder(PlaceOrderDto placeOrderDto);

    List<OrderDto> getAllPlacedOrders();

    Long changeOrderStatus(Long orderId, String status);

    List<OrderDto> getUserPlacedOrders(Long userId);

    OrderDto getOrderDtoById(Long id);

    OrderDto searchOrderByTrackingId(UUID trackingId);

    AnalyticsResponseDto getAnalytics();



}
