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
    public boolean isCartItemPresentInOrder(AddProductInCartDto addProductInCardDto, Long orderId) {

        return cartItemRepository.findByUserIdAndProductIdAndOrderId(
                addProductInCardDto.getUserId(),
                addProductInCardDto.getProductId(),
                orderId)
                .isPresent();
    }

    @Override
    public CartItemEntity saveCartEntity(CartItemEntity cartItemEntity) {
        return cartItemRepository.save(cartItemEntity);
    }

    @Override
    public CartItemEntity createCartItem(ProductEntity product, Long price, Long quantity,
                                         UserEntity user, OrderEntity order) {
        return  new CartItemEntity()
                .setProduct(product)
                .setPrice(product.getPrice())
                .setQuantity(1L)
                .setUser(user)
                .setOrder(order);
    }

}
