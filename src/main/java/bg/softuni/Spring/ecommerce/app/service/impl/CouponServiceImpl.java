package bg.softuni.Spring.ecommerce.app.service.impl;

import bg.softuni.Spring.ecommerce.app.model.dto.CouponDto;
import bg.softuni.Spring.ecommerce.app.model.entity.CouponEntity;
import bg.softuni.Spring.ecommerce.app.repository.CouponRepository;
import bg.softuni.Spring.ecommerce.app.service.CouponService;
import bg.softuni.Spring.ecommerce.app.service.exception.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;

    public CouponServiceImpl(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }


    @Override
    public CouponEntity createCoupon(CouponDto couponDto) {

        if (couponRepository.existsByCode(couponDto.getCode())) {
            throw new ObjectNotFoundException("Coupon code already exist");
        }

        CouponEntity coupon = new CouponEntity()
                .setName(couponDto.getName())
                .setCode(couponDto.getCode())
                .setDiscount(couponDto.getDiscount())
                .setExpirationDate(couponDto.getExpirationDate());

        return couponRepository.save(coupon);
    }

    @Override
    public List<CouponDto> getAllCoupons() {
        return couponRepository.findAll()
                .stream()
                .map(this::getCouponDto)
                .collect(Collectors.toList());
    }

    @Override
    public CouponEntity findByCode(String code) {
        return couponRepository.findByCode(code)
                .orElseThrow(() -> new ObjectNotFoundException("Coupon not found"));
    }

    @Override
    public boolean isExpired(String code) {
        CouponEntity coupon = findByCode(code);
        Date currentDate = new Date();
        Date expirationDate = coupon.getExpirationDate();

        return currentDate.after(expirationDate);
    }

    @Override
    public CouponDto getCouponDto(CouponEntity coupon) {
        return new CouponDto()
                .setId(coupon.getId())
                .setName(coupon.getName())
                .setCode(coupon.getCode())
                .setExpirationDate(coupon.getExpirationDate())
                .setDiscount(coupon.getDiscount());
    }
}
