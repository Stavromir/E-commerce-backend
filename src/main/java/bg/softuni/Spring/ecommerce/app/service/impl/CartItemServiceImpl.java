package bg.softuni.Spring.ecommerce.app.service.impl;

import bg.softuni.Spring.ecommerce.app.model.dto.AddProductInCardDto;
import bg.softuni.Spring.ecommerce.app.model.dto.CartItemDto;
import bg.softuni.Spring.ecommerce.app.model.entity.CartItemEntity;
import bg.softuni.Spring.ecommerce.app.model.entity.OrderEntity;
import bg.softuni.Spring.ecommerce.app.repository.CartItemRepository;
import bg.softuni.Spring.ecommerce.app.service.CartItemService;
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
    public boolean isCartItemPresentInOrder(AddProductInCardDto addProductInCardDto, Long orderId) {

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

}
