package bg.softuni.Spring.ecommerce.app.service.impl;

import bg.softuni.Spring.ecommerce.app.model.dto.AddProductInCardDto;
import bg.softuni.Spring.ecommerce.app.model.dto.CartItemDto;
import bg.softuni.Spring.ecommerce.app.model.entity.OrderEntity;
import bg.softuni.Spring.ecommerce.app.model.enums.OrderStatusEnum;
import bg.softuni.Spring.ecommerce.app.repository.CartItemRepository;
import bg.softuni.Spring.ecommerce.app.service.CartItemService;
import bg.softuni.Spring.ecommerce.app.service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class CartItemServiceImpl implements CartItemService {

    private final OrderService orderService;
    private final CartItemRepository cartItemRepository;

    public CartItemServiceImpl(OrderService orderService, CartItemRepository cartItemRepository) {
        this.orderService = orderService;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public CartItemDto addProductToCart(AddProductInCardDto addProductInCardDto) {



















        return null;
    }

    @Override
    public boolean isCartItemPresent(AddProductInCardDto addProductInCardDto) {

        OrderEntity activeOrder = orderService
                .getActiveOrder(addProductInCardDto.getUserId(), OrderStatusEnum.PENDING);

        return cartItemRepository.findByUserIdAndProductIdAndOrderId(
                addProductInCardDto.getUserId(),
                addProductInCardDto.getProductId(),
                activeOrder.getId())
                .isPresent();
    }
}
