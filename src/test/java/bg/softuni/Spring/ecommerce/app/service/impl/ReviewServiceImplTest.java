package bg.softuni.Spring.ecommerce.app.service.impl;

import bg.softuni.Spring.ecommerce.app.model.dto.ReviewDto;
import bg.softuni.Spring.ecommerce.app.model.entity.ReviewEntity;
import bg.softuni.Spring.ecommerce.app.repository.ReviewRepository;
import bg.softuni.Spring.ecommerce.app.service.ReviewService;
import bg.softuni.Spring.ecommerce.app.service.testUtils.ReviewTestDataUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReviewServiceImplTest {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ReviewTestDataUtil reviewTestDataUtil;
    @Autowired
    private ReviewService reviewService;

    @BeforeEach
    void setUp() {
        reviewTestDataUtil.clearAllTestData();
    }

    @AfterEach
    void tearDown() {
        reviewTestDataUtil.clearAllTestData();
    }

    @Test
    void testPostReview() throws IOException {
        ReviewDto testReviewDto = reviewTestDataUtil.createReviewDto();

        Long reviewId = reviewService.postReview(testReviewDto);

        Optional<ReviewEntity> createdReview = reviewRepository.findById(reviewId);

        assertTrue(createdReview.isPresent());
        ReviewEntity reviewEntity = createdReview.get();
        assertEquals(testReviewDto.getDescription(), reviewEntity.getDescription());
        assertEquals(testReviewDto.getRating(), reviewEntity.getRating());
        assertEquals(testReviewDto.getProductId(), reviewEntity.getProduct().getId());
        assertEquals(testReviewDto.getUserId(), reviewEntity.getUser().getId());
    }

}