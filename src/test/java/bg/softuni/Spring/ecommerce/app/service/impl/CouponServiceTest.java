package bg.softuni.Spring.ecommerce.app.service.impl;

import bg.softuni.Spring.ecommerce.app.model.dto.CouponDto;
import bg.softuni.Spring.ecommerce.app.model.entity.CouponEntity;
import bg.softuni.Spring.ecommerce.app.repository.CouponRepository;
import bg.softuni.Spring.ecommerce.app.service.CouponService;
import bg.softuni.Spring.ecommerce.app.service.exception.ObjectNotFoundException;
import bg.softuni.Spring.ecommerce.app.utils.DateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CouponServiceTest {

    public static final Long COUPON_ID = 1L;
    public static final String COUPON_NAME = "CouponOne";
    public static final String COUPON_CODE = "CodeOne";
    public static final Long COUPON_DISCOUNT = 10L;
    public static final LocalDateTime COUPON_EXPIRATION_NOW_DATE = LocalDateTime.now();
    public static final LocalDateTime COUPON_EXPIRATION_PAST_DATE = COUPON_EXPIRATION_NOW_DATE.minusDays(1);
    public static final LocalDateTime COUPON_EXPIRATION_FEATURE_DATE = COUPON_EXPIRATION_NOW_DATE.plusDays(1);

    private CouponService couponServiceToTest;

    @Mock
    private CouponRepository couponRepository;

    @Mock
    private DateTime dateTime;


    @BeforeEach
    void SetUp() {
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

    @Test
    void testCreateExistingCoupon() {

        CouponDto testCouponDto = createCouponDto();

        when(couponRepository.existsByCode(anyString()))
                .thenReturn(true);

        assertThrows(
                ObjectNotFoundException.class,
                () -> couponServiceToTest.createCoupon(testCouponDto)
        );
    }

    @Test
    void testGetCouponDto() {
        CouponEntity testCoupon = createCouponEntity();

        CouponDto couponDto = couponServiceToTest.getCouponDto(testCoupon);

        assertNotNull(couponDto);
        assertEquals(testCoupon.getId(), couponDto.getId());
        assertEquals(testCoupon.getName(), couponDto.getName());
        assertEquals(testCoupon.getCode(), couponDto.getCode());
        assertEquals(testCoupon.getDiscount(), couponDto.getDiscount());
        assertEquals(testCoupon.getExpirationDate(), couponDto.getExpirationDate());
    }

    @Test
    void testFindByCode() {
        when(dateTime.getDate()).thenReturn(COUPON_EXPIRATION_NOW_DATE);

        CouponEntity testCouponEntity = createCouponEntity();

        when(couponRepository.findByCode(anyString()))
                .thenReturn(Optional.of(testCouponEntity));

        CouponEntity couponEntity = couponServiceToTest.findByCode(anyString());

        assertNotNull(couponEntity);
    }

    @Test
    void testNotFoundByCode() {
        assertThrows(
                ObjectNotFoundException.class,
                () -> couponServiceToTest.findByCode(anyString())
        );
    }

    @Test
    void testIsExpired() {
        when(dateTime.getDate()).thenReturn(COUPON_EXPIRATION_PAST_DATE);

        CouponEntity testCouponEntity = createCouponEntity();

        when(couponRepository.findByCode(anyString()))
                .thenReturn(Optional.of(testCouponEntity));

        assertTrue(couponServiceToTest.isExpired(anyString()));
    }

    @Test
    void testIsNotExpired() {
        when(dateTime.getDate()).thenReturn(COUPON_EXPIRATION_FEATURE_DATE);

        CouponEntity testCouponEntity = createCouponEntity();

        when(couponRepository.findByCode(anyString()))
                .thenReturn(Optional.of(testCouponEntity));

        assertFalse(couponServiceToTest.isExpired(anyString()));
    }

    private CouponEntity createCouponEntity() {
        CouponEntity coupon = new CouponEntity()
                .setName(COUPON_NAME)
                .setCode(COUPON_CODE)
                .setDiscount(COUPON_DISCOUNT)
                .setExpirationDate(dateTime.getDate());
        coupon.setId(COUPON_ID);
        return coupon;
    }

    private CouponDto createCouponDto() {
        return new CouponDto()
                .setName(COUPON_NAME)
                .setCode(COUPON_CODE)
                .setDiscount(COUPON_DISCOUNT)
                .setExpirationDate(dateTime.getDate());
    }


}