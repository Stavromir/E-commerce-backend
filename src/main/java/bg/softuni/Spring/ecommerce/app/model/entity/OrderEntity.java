package bg.softuni.Spring.ecommerce.app.model.entity;

import bg.softuni.Spring.ecommerce.app.model.enums.OrderStatusEnum;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class OrderEntity extends BaseEntity {

    private String orderDescription;
    private Date date;
    private Long amount;
    private String address;
    private String payment;
    private OrderStatusEnum orderStatus;
    private Long totalAmount;
    private Long discount;
    private UUID trackingId;
    private UserEntity user;
    private CouponEntity coupon;
    private List<CartItemEntity> cartItems;


    public OrderEntity() {
    }

    public String getOrderDescription() {
        return orderDescription;
    }

    public OrderEntity setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public OrderEntity setDate(Date date) {
        this.date = date;
        return this;
    }

    public Long getAmount() {
        return amount;
    }

    public OrderEntity setAmount(Long amount) {
        this.amount = amount;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public OrderEntity setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getPayment() {
        return payment;
    }

    public OrderEntity setPayment(String payment) {
        this.payment = payment;
        return this;
    }

    @Enumerated
    public OrderStatusEnum getOrderStatus() {
        return orderStatus;
    }

    public OrderEntity setOrderStatus(OrderStatusEnum orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public OrderEntity setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public Long getDiscount() {
        return discount;
    }

    public OrderEntity setDiscount(Long discount) {
        this.discount = discount;
        return this;
    }

    public UUID getTrackingId() {
        return trackingId;
    }

    public OrderEntity setTrackingId(UUID trackingId) {
        this.trackingId = trackingId;
        return this;
    }

    @ManyToOne(cascade = CascadeType.MERGE)
    public UserEntity getUser() {
        return user;
    }

    public OrderEntity setUser(UserEntity user) {
        this.user = user;
        return this;
    }

    @OneToOne(cascade = CascadeType.MERGE)
    public CouponEntity getCoupon() {
        return coupon;
    }

    public OrderEntity setCoupon(CouponEntity coupon) {
        this.coupon = coupon;
        return this;
    }

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    public List<CartItemEntity> getCartItems() {
        return cartItems;
    }

    public OrderEntity setCartItems(List<CartItemEntity> cartItems) {
        this.cartItems = cartItems;
        return this;
    }
}
