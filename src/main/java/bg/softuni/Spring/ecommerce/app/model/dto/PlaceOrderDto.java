package bg.softuni.Spring.ecommerce.app.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PlaceOrderDto {

    private Long userId;
    private String address;
    private String orderDescription;

    public Long getUserId() {
        return userId;
    }

    public PlaceOrderDto setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    @NotNull
    @NotBlank
    public String getAddress() {
        return address;
    }

    public PlaceOrderDto setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getOrderDescription() {
        return orderDescription;
    }

    public PlaceOrderDto setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
        return this;
    }
}
