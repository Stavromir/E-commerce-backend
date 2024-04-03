package bg.softuni.Spring.ecommerce.app.service.impl;

import bg.softuni.Spring.ecommerce.app.model.dto.*;
import bg.softuni.Spring.ecommerce.app.model.entity.ProductEntity;
import bg.softuni.Spring.ecommerce.app.model.entity.ReviewEntity;
import bg.softuni.Spring.ecommerce.app.model.entity.UserEntity;
import bg.softuni.Spring.ecommerce.app.repository.ReviewRepository;
import bg.softuni.Spring.ecommerce.app.service.OrderService;
import bg.softuni.Spring.ecommerce.app.service.ProductService;
import bg.softuni.Spring.ecommerce.app.service.ReviewService;
import bg.softuni.Spring.ecommerce.app.service.UserService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final OrderService orderService;
    private final ProductService productService;
    private final UserService userService;
    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(OrderService orderService,
                             ProductService productService,
                             UserService userService,
                             ReviewRepository reviewRepository) {
        this.orderService = orderService;
        this.productService = productService;
        this.userService = userService;
        this.reviewRepository = reviewRepository;
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
                    .setId(cartItem.getProductId())
                    .setName(cartItem.getProductName())
                    .setPrice(cartItem.getPrice())
                    .setQuantity(cartItem.getQuantity())
                    .setByteImg(cartItem.getReturnedImg());

            productDtos.add(productDto);
        }

        responseDto.setProductDtos(productDtos);
        return responseDto;
    }

    @Override
    public Long postReview(ReviewDto reviewDto) throws IOException {
        ProductEntity product = productService.getProductById(reviewDto.getProductId());
        UserEntity user = userService.getUserById(reviewDto.getUserId());

        ReviewEntity reviewEntity = new ReviewEntity();
        reviewEntity
                .setDescription(reviewDto.getDescription())
                .setProduct(product)
                .setUser(user)
                .setRating(reviewDto.getRating())
                .setImg(reviewDto.getImg().getBytes());


        return reviewRepository.save(reviewEntity).getId();
    }
}
