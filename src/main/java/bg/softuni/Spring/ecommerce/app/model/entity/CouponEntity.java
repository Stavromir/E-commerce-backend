package bg.softuni.Spring.ecommerce.app.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "coupons")
public class CouponEntity extends BaseEntity {

    private String name;
    private String code;
    private Long discount;
    private Date expirationDate;

    public CouponEntity() {
    }

    @Column(nullable = false, unique = true)
    public String getName() {
        return name;
    }

    public CouponEntity setName(String name) {
        this.name = name;
        return this;
    }

    @Column(nullable = false, unique = true)
    public String getCode() {
        return code;
    }

    public CouponEntity setCode(String code) {
        this.code = code;
        return this;
    }

    @Column(nullable = false)
    public Long getDiscount() {
        return discount;
    }

    public CouponEntity setDiscount(Long discount) {
        this.discount = discount;
        return this;
    }

    @Column(nullable = false)
    public Date getExpirationDate() {
        return expirationDate;
    }

    public CouponEntity setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
        return this;
    }
}
