package bg.softuni.Spring.ecommerce.app.model.dto;

import bg.softuni.Spring.ecommerce.app.model.entity.CartItemEntity;
import bg.softuni.Spring.ecommerce.app.model.entity.UserEntity;
import bg.softuni.Spring.ecommerce.app.model.enums.OrderStatusEnum;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class OrderDto {

    private Long id;
    private String orderDescription;
    private LocalDateTime date;
    private Long amount;
    private String address;
    private String payment;
    private OrderStatusEnum orderStatus;
    private Long totalAmount;
    private Long discount;
    private UUID trackingId;
    private String userName;
    private String couponName;
    private List<CartItemDto> cartItems;

    public OrderDto() {
    }

    public Long getId() {
        return id;
    }

    public OrderDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getOrderDescription() {
        return orderDescription;
    }

    public OrderDto setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
        return this;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public OrderDto setDate(LocalDateTime date) {
        this.date = date;
        return this;
    }

    public Long getAmount() {
        return amount;
    }

    public OrderDto setAmount(Long amount) {
        this.amount = amount;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public OrderDto setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getPayment() {
        return payment;
    }

    public OrderDto setPayment(String payment) {
        this.payment = payment;
        return this;
    }

    public OrderStatusEnum getOrderStatus() {
        return orderStatus;
    }

    public OrderDto setOrderStatus(OrderStatusEnum orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public OrderDto setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public Long getDiscount() {
        return discount;
    }

    public OrderDto setDiscount(Long discount) {
        this.discount = discount;
        return this;
    }

    public UUID getTrackingId() {
        return trackingId;
    }

    public OrderDto setTrackingId(UUID trackingId) {
        this.trackingId = trackingId;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public OrderDto setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getCouponName() {
        return couponName;
    }

    public OrderDto setCouponName(String couponName) {
        this.couponName = couponName;
        return this;
    }

    public List<CartItemDto> getCartItems() {
        return cartItems;
    }

    public OrderDto setCartItems(List<CartItemDto> cartItems) {
        this.cartItems = cartItems;
        return this;
    }
}
