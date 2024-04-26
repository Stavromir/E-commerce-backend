package bg.softuni.Spring.ecommerce.app.service.impl;

import bg.softuni.Spring.ecommerce.app.model.dto.CouponDto;
import bg.softuni.Spring.ecommerce.app.model.entity.CouponEntity;
import bg.softuni.Spring.ecommerce.app.repository.CouponRepository;
import bg.softuni.Spring.ecommerce.app.service.CouponService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CouponServiceImplTest {

    public static final String COUPON_NAME = "CouponOne";
    public static final String COUPON_CODE = "CodeOne";
    public static final Long COUPON_DISCOUNT = 10L;
    public static final Date COUPON_EXPIRATION_DATE = new Date();

    private CouponService couponServiceToTest;

    @Mock
    private CouponRepository couponRepository;


    @BeforeEach
    void SetUp () {
        this.couponServiceToTest = new CouponServiceImpl(couponRepository);
    }

    @Test
    void testCreateCoupon() {
        CouponDto testCouponDto = createCouponDto();
        CouponEntity returnedCouponEntity = createCouponEntity();

        when(couponRepository.save(any(CouponEntity.class)))
                .thenReturn(returnedCouponEntity);

        CouponEntity coupon = couponServiceToTest.createCoupon(testCouponDto);

        assertNotNull(coupon);
        assertEquals(testCouponDto.getName(), coupon.getName());
        assertEquals(testCouponDto.getCode(), coupon.getCode());
        assertEquals(testCouponDto.getDiscount(), coupon.getDiscount());
        assertEquals(testCouponDto.getExpirationDate(), coupon.getExpirationDate());
    }

    private CouponEntity createCouponEntity() {
        return new CouponEntity()
                .setName(COUPON_NAME)
                .setCode(COUPON_CODE)
                .setDiscount(COUPON_DISCOUNT)
                .setExpirationDate(COUPON_EXPIRATION_DATE);
    }

    private CouponDto createCouponDto() {
        return new CouponDto()
                .setName(COUPON_NAME)
                .setCode(COUPON_CODE)
                .setDiscount(COUPON_DISCOUNT)
                .setExpirationDate(COUPON_EXPIRATION_DATE);
    }

    @Test
    void testCreateExistingCoupon() {

    }


}