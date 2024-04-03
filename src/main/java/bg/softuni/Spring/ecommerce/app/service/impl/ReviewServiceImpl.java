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
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ProductService productService;
    private final UserService userService;
    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(@Lazy ProductService productService,
                             @Lazy UserService userService,
                             ReviewRepository reviewRepository) {
        this.productService = productService;
        this.userService = userService;
        this.reviewRepository = reviewRepository;
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

    @Override
    public List<ReviewDto> getAllReviewsByProductId(Long productId) {
        return reviewRepository.getAllByProductId(productId)
                .stream()
                .map(ReviewServiceImpl::mapToReviewDto)
                .collect(Collectors.toList());
    }

    private static ReviewDto mapToReviewDto(ReviewEntity reviewEntity) {
        return new ReviewDto()
                .setId(reviewEntity.getId())
                .setDescription(reviewEntity.getDescription())
                .setRating(reviewEntity.getRating())
                .setReturnedImg(reviewEntity.getImg())
                .setUsername(reviewEntity.getUser().getName());
    }
}
