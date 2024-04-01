package bg.softuni.Spring.ecommerce.app.service;

import bg.softuni.Spring.ecommerce.app.model.dto.AddProductInCardDto;
import bg.softuni.Spring.ecommerce.app.model.dto.OrderDto;
import bg.softuni.Spring.ecommerce.app.model.dto.PlaceOrderDto;
import bg.softuni.Spring.ecommerce.app.model.entity.OrderEntity;
import bg.softuni.Spring.ecommerce.app.model.entity.UserEntity;
import bg.softuni.Spring.ecommerce.app.model.enums.OrderStatusEnum;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    void createEmptyOrder(UserEntity user);

    Long addProductToCart (AddProductInCardDto addProductInCardDto);

    boolean isCartItemPresent(AddProductInCardDto addProductInCardDto);

    OrderDto getCartByUserId(Long userId);

    OrderDto applyCoupon(Long userId, String code);

    Long increaseProductQuantity(AddProductInCardDto addProductInCardDto);

    Long decreaseProductQuantity(AddProductInCardDto addProductInCardDto);

    Long placeOrder(PlaceOrderDto placeOrderDto);

    List<OrderDto> getAllPlacedOrders();

    Long changeOrderStatus(Long orderId, String status);



}
