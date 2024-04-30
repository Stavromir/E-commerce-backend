package bg.softuni.Spring.ecommerce.app.service.impl;

import bg.softuni.Spring.ecommerce.app.model.dto.AddProductInCartDto;
import bg.softuni.Spring.ecommerce.app.model.dto.CartItemDto;
import bg.softuni.Spring.ecommerce.app.model.entity.CartItemEntity;
import bg.softuni.Spring.ecommerce.app.model.entity.OrderEntity;
import bg.softuni.Spring.ecommerce.app.model.entity.ProductEntity;
import bg.softuni.Spring.ecommerce.app.model.entity.UserEntity;
import bg.softuni.Spring.ecommerce.app.repository.CartItemRepository;
import bg.softuni.Spring.ecommerce.app.service.CartItemService;
import bg.softuni.Spring.ecommerce.app.service.exception.ObjectNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CartItemServiceImpl implements CartItemService {

    public static final Long INITIAL_CART_ITEM_QUANTITY = 1L;


    private final CartItemRepository cartItemRepository;


    public CartItemServiceImpl(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public CartItemDto mapToCartItemDto(CartItemEntity updatedCart) {
        return new CartItemDto()
                .setId(updatedCart.getId())
                .setPrice(updatedCart.getPrice())
                .setProductId(updatedCart.getProduct().getId())
                .setQuantity(updatedCart.getQuantity())
                .setUserId(updatedCart.getUser().getId())
                .setProductName(updatedCart.getProduct().getName())
                .setReturnedImg(updatedCart.getProduct().getImg());
    }

    @Override
    public CartItemEntity findByProductIdAndOrderIdAndUserID(AddProductInCartDto addProductInCardDto, Long orderId) {
        return cartItemRepository.findByUserIdAndProductIdAndOrderId(
                addProductInCardDto.getUserId(),
                addProductInCardDto.getProductId(),
                orderId)
                .orElseThrow(() -> new ObjectNotFoundException("Cart item not exist"));
    }

    @Override
    public boolean isCartItemPresentInOrder(Long userId, Long productId, Long orderId) {

        return cartItemRepository.findByUserIdAndProductIdAndOrderId(
                userId,
                productId,
                orderId)
                .isPresent();
    }

    @Override
    public CartItemEntity saveCartEntity(CartItemEntity cartItemEntity) {
        return cartItemRepository.save(cartItemEntity);
    }

    @Override
    public CartItemEntity createCartItem(ProductEntity product, UserEntity user, OrderEntity order) {

        if (isCartItemPresentInOrder(user.getId(), product.getId(), order.getId())) {
            throw new IllegalArgumentException("Product is already in cart!");
        }

        return  new CartItemEntity()
                .setProduct(product)
                .setPrice(product.getPrice())
                .setQuantity(INITIAL_CART_ITEM_QUANTITY)
                .setUser(user)
                .setOrder(order);
    }

}
