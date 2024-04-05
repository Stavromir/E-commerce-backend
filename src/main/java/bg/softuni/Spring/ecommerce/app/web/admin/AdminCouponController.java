package bg.softuni.Spring.ecommerce.app.web.admin;

import bg.softuni.Spring.ecommerce.app.model.dto.CouponDto;
import bg.softuni.Spring.ecommerce.app.service.CouponService;
import bg.softuni.Spring.ecommerce.app.service.exception.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminCouponController {


    private final CouponService couponService;

    public AdminCouponController(CouponService couponService) {
        this.couponService = couponService;
    }


    @PostMapping("/coupons")
    public ResponseEntity<?> createCoupon(@RequestBody CouponDto couponDto) {
        try {
            CouponDto coupon = couponService.createCoupon(couponDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(coupon);
        } catch (ObjectNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping("/coupons")
    public ResponseEntity<List<CouponDto>> getAllCoupons() {
        List<CouponDto> allCoupons = couponService.getAllCoupons();
        return ResponseEntity.ok(allCoupons);
    }
}
