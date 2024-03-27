package bg.softuni.Spring.ecommerce.app.model.dto;

import bg.softuni.Spring.ecommerce.app.model.entity.OrderEntity;
import bg.softuni.Spring.ecommerce.app.model.entity.ProductEntity;
import bg.softuni.Spring.ecommerce.app.model.entity.UserEntity;

public class CartItemDto {

    private Long id;
    private Long price;
    private Long quantity;
    private Long productId;
    private String productName;
    private Long userId;
    private Long orderId;
    private byte[] returnedImg;

    public CartItemDto() {
    }

    public Long getId() {
        return id;
    }

    public CartItemDto setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getPrice() {
        return price;
    }

    public CartItemDto setPrice(Long price) {
        this.price = price;
        return this;
    }

    public Long getQuantity() {
        return quantity;
    }

    public CartItemDto setQuantity(Long quantity) {
        this.quantity = quantity;
        return this;
    }

    public Long getProductId() {
        return productId;
    }

    public CartItemDto setProductId(Long productId) {
        this.productId = productId;
        return this;
    }

    public String getProductName() {
        return productName;
    }

    public CartItemDto setProductName(String productName) {
        this.productName = productName;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public CartItemDto setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Long getOrderId() {
        return orderId;
    }

    public CartItemDto setOrderId(Long orderId) {
        this.orderId = orderId;
        return this;
    }

    public byte[] getReturnedImg() {
        return returnedImg;
    }

    public CartItemDto setReturnedImg(byte[] returnedImg) {
        this.returnedImg = returnedImg;
        return this;
    }
}
