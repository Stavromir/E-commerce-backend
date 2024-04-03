package bg.softuni.Spring.ecommerce.app.web.customer;

import bg.softuni.Spring.ecommerce.app.model.dto.OrderedProductsResponseDto;
import bg.softuni.Spring.ecommerce.app.model.dto.ReviewDto;
import bg.softuni.Spring.ecommerce.app.service.ReviewService;
import bg.softuni.Spring.ecommerce.app.service.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/customer")
public class CustomerReviewController {

    private final ReviewService reviewService;

    public CustomerReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }


    @PostMapping("/review")
    public ResponseEntity<?> postReview (@ModelAttribute ReviewDto reviewDto) throws IOException {
        try {
            Long reviewId = reviewService.postReview(reviewDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(reviewId);
        } catch (ValidationException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
}
