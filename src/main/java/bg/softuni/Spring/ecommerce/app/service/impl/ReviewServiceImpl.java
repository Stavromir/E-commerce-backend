package bg.softuni.Spring.ecommerce.app.service.impl;

import bg.softuni.Spring.ecommerce.app.model.dto.CartItemDto;
import bg.softuni.Spring.ecommerce.app.model.dto.OrderDto;
import bg.softuni.Spring.ecommerce.app.model.dto.OrderedProductsResponseDto;
import bg.softuni.Spring.ecommerce.app.model.dto.ProductDto;
import bg.softuni.Spring.ecommerce.app.service.OrderService;
import bg.softuni.Spring.ecommerce.app.service.ReviewService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final OrderService orderService;

    public ReviewServiceImpl(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public OrderedProductsResponseDto getOrderedProductsDetailsByOrderId(Long id) {

        OrderDto orderById = orderService.getOrderById(id);
        List<CartItemDto> cartItems = orderById.getCartItems();

        OrderedProductsResponseDto responseDto = new OrderedProductsResponseDto();
        responseDto.setOrderAmount(orderById.getAmount());
        List<ProductDto> productDtos = new ArrayList<>();

        for (CartItemDto cartItem : cartItems) {
            ProductDto productDto = new ProductDto();
            productDto
                    .setId(cartItem.getId())
                    .setName(cartItem.getProductName())
                    .setPrice(cartItem.getPrice())
                    .setQuantity(cartItem.getQuantity())
                    .setByteImg(cartItem.getReturnedImg());

            productDtos.add(productDto);
        }

        responseDto.setProductDtos(productDtos);
        return responseDto;
    }
}
