package bg.softuni.Spring.ecommerce.app.service;

import bg.softuni.Spring.ecommerce.app.model.dto.AddProductInCartDto;
import bg.softuni.Spring.ecommerce.app.model.dto.CartItemDto;
import bg.softuni.Spring.ecommerce.app.model.entity.CartItemEntity;
import bg.softuni.Spring.ecommerce.app.model.entity.OrderEntity;
import bg.softuni.Spring.ecommerce.app.model.entity.ProductEntity;
import bg.softuni.Spring.ecommerce.app.model.entity.UserEntity;

public interface CartItemService {

    boolean isCartItemPresentInOrder(Long userId, Long productId, Long orderId);

    CartItemEntity saveCartEntity(CartItemEntity cartItemEntity);

    CartItemEntity createCartItem(ProductEntity product, Long price, Long quantity, UserEntity user, OrderEntity order);

    CartItemDto mapToCartItemDto(CartItemEntity updatedCart);

    CartItemEntity findByProductIdAndOrderIdAndUserID(AddProductInCartDto addProductInCardDto, Long orderId);

}
