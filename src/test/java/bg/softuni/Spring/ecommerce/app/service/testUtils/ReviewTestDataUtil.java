package bg.softuni.Spring.ecommerce.app.service.testUtils;

import bg.softuni.Spring.ecommerce.app.model.entity.ProductEntity;
import bg.softuni.Spring.ecommerce.app.model.entity.ReviewEntity;
import bg.softuni.Spring.ecommerce.app.model.entity.UserEntity;
import bg.softuni.Spring.ecommerce.app.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class ReviewTestDataUtil {

    public static final String REVIEW_DESCRIPTION = "reviewDescription";
    public static final Long REVIEW_RATING = 5L;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProductTestDataUtil productTestDataUtil;

    @Autowired
    private UserTestDataUtil userTestDataUtil;

    public ReviewEntity createReviewEntity() {

        ProductEntity testProduct = productTestDataUtil.createProduct();
        UserEntity testUser = userTestDataUtil.createTestUser();

        ReviewEntity reviewEntity = new ReviewEntity()
                .setProduct(testProduct)
                .setUser(testUser)
                .setDescription(REVIEW_DESCRIPTION)
                .setRating(REVIEW_RATING);

        return reviewRepository.save(reviewEntity);
    }



    public void clearAllTestData() {
        reviewRepository.deleteAll();
    }
}
