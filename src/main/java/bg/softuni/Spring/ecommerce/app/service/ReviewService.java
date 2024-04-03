package bg.softuni.Spring.ecommerce.app.service;

import bg.softuni.Spring.ecommerce.app.model.dto.OrderedProductsResponseDto;
import bg.softuni.Spring.ecommerce.app.model.dto.ReviewDto;

import java.io.IOException;
import java.util.List;

public interface ReviewService {


    Long postReview(ReviewDto reviewDto) throws IOException;

    List<ReviewDto> getAllReviewsByProductId(Long productId);


}
