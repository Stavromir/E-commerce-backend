package bg.softuni.Spring.ecommerce.app.service;

import bg.softuni.Spring.ecommerce.app.model.dto.AddProductInCardDto;
import bg.softuni.Spring.ecommerce.app.model.dto.CartItemDto;
import bg.softuni.Spring.ecommerce.app.model.entity.CartItemEntity;

import java.util.Optional;

public interface CartItemService {

    boolean isCartItemPresentInOrder(AddProductInCardDto addProductInCardDto, Long orderId);

    CartItemEntity saveCartEntity(CartItemEntity cartItemEntity);

    CartItemDto mapToCartItemDto(CartItemEntity updatedCart);

    CartItemEntity findByProductIdAndOrderIdAndUserID(AddProductInCardDto addProductInCardDto, Long orderId);

}
