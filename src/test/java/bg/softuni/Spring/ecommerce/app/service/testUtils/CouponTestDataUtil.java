package bg.softuni.Spring.ecommerce.app.service.testUtils;

import bg.softuni.Spring.ecommerce.app.model.dto.CategoryDto;
import bg.softuni.Spring.ecommerce.app.model.dto.CouponDto;
import bg.softuni.Spring.ecommerce.app.model.entity.CouponEntity;
import bg.softuni.Spring.ecommerce.app.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CouponTestDataUtil {

    public static final String COUPON_NAME = "couponName";
    public static final String COUPON_CODE = "couponCode";
    public static final Long COUPON_DISCOUNT = 10L;
    public static final Date COUPON_EXPIRATION_PAST_DATE = new Date(System.currentTimeMillis() - 86400000);
    public static final Date COUPON_EXPIRATION_FEATURE_DATE = new Date(System.currentTimeMillis() + 86400000);

    @Autowired
    private CouponRepository couponRepository;

    public CouponEntity createValidCouponEntity() {
        CouponEntity testCoupon = new CouponEntity()
                .setCode(COUPON_CODE)
                .setDiscount(COUPON_DISCOUNT)
                .setName(COUPON_NAME)
                .setExpirationDate(COUPON_EXPIRATION_FEATURE_DATE);

        return couponRepository.save(testCoupon);
    }

    public CouponEntity createExpiredCouponEntity() {
        CouponEntity testCoupon = new CouponEntity()
                .setCode(COUPON_CODE)
                .setDiscount(COUPON_DISCOUNT)
                .setName(COUPON_NAME)
                .setExpirationDate(COUPON_EXPIRATION_PAST_DATE);

        return couponRepository.save(testCoupon);
    }

    public CouponDto createCouponDto() {
        return new CouponDto()
                .setName(COUPON_NAME)
                .setDiscount(COUPON_DISCOUNT)
                .setCode(COUPON_CODE)
                .setExpirationDate(COUPON_EXPIRATION_FEATURE_DATE);
    }

    public void clearAllTestData() {
        couponRepository.deleteAll();
    }
}
