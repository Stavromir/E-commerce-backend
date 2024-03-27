package bg.softuni.Spring.ecommerce.app.service;

import bg.softuni.Spring.ecommerce.app.model.dto.AddProductInCardDto;
import bg.softuni.Spring.ecommerce.app.model.dto.OrderDto;
import bg.softuni.Spring.ecommerce.app.model.entity.OrderEntity;
import bg.softuni.Spring.ecommerce.app.model.entity.UserEntity;
import bg.softuni.Spring.ecommerce.app.model.enums.OrderStatusEnum;

import java.util.Optional;

public interface OrderService {

    void createEmptyOrder(UserEntity user);

    Long addProductToCart (AddProductInCardDto addProductInCardDto);

    boolean isCartItemPresent(AddProductInCardDto addProductInCardDto);

    OrderDto getCartByUserId(Long userId);

}
