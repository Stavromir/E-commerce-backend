package bg.softuni.Spring.ecommerce.app.service;

import bg.softuni.Spring.ecommerce.app.model.dto.AddProductInCartDto;
import bg.softuni.Spring.ecommerce.app.model.dto.CartItemDto;
import bg.softuni.Spring.ecommerce.app.model.entity.CartItemEntity;

public interface CartItemService {

    boolean isCartItemPresentInOrder(AddProductInCartDto addProductInCardDto, Long orderId);

    CartItemEntity saveCartEntity(CartItemEntity cartItemEntity);

    CartItemDto mapToCartItemDto(CartItemEntity updatedCart);

    CartItemEntity findByProductIdAndOrderIdAndUserID(AddProductInCartDto addProductInCardDto, Long orderId);

}
