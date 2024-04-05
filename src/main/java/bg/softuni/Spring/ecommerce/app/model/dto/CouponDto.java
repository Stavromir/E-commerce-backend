package bg.softuni.Spring.ecommerce.app.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.Date;

public class CouponDto {

    private Long id;
    private String name;
    private String code;
    private Long discount;
    private Date expirationDate;

    public CouponDto() {
    }

    public Long getId() {
        return id;
    }

    public CouponDto setId(Long id) {
        this.id = id;
        return this;
    }

    @Size(min = 4)
    public String getName() {
        return name;
    }

    public CouponDto setName(String name) {
        this.name = name;
        return this;
    }

    @Size(min = 4)
    public String getCode() {
        return code;
    }

    public CouponDto setCode(String code) {
        this.code = code;
        return this;
    }

    @Positive
    public Long getDiscount() {
        return discount;
    }

    public CouponDto setDiscount(Long discount) {
        this.discount = discount;
        return this;
    }

    @NotNull
    public Date getExpirationDate() {
        return expirationDate;
    }

    public CouponDto setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
        return this;
    }
}
