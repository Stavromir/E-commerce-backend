package bg.softuni.Spring.ecommerce.app.service;

import bg.softuni.Spring.ecommerce.app.model.dto.CouponDto;
import bg.softuni.Spring.ecommerce.app.model.entity.CouponEntity;

import java.util.List;

public interface CouponService {

    public CouponDto createCoupon(CouponDto couponDto);

    List<CouponDto> getAllCoupons();

    CouponEntity findByCode(String code);

    boolean isExpired(String code);
}
