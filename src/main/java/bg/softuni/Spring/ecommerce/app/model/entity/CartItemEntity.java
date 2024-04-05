package bg.softuni.Spring.ecommerce.app.model.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "cart_items")
public class CartItemEntity extends BaseEntity {

    private Long price;
    private Long quantity;
    private ProductEntity product;
    private UserEntity user;
    private OrderEntity order;

    public CartItemEntity() {
    }

    @Column(nullable = false)
    public Long getPrice() {
        return price;
    }

    public CartItemEntity setPrice(Long price) {
        this.price = price;
        return this;
    }

    @Column(nullable = false)
    public Long getQuantity() {
        return quantity;
    }

    public CartItemEntity setQuantity(Long quantity) {
        this.quantity = quantity;
        return this;
    }

    @ManyToOne(optional = false)
    public ProductEntity getProduct() {
        return product;
    }

    public CartItemEntity setProduct(ProductEntity product) {
        this.product = product;
        return this;
    }

    @ManyToOne(optional = false)
    public UserEntity getUser() {
        return user;
    }

    public CartItemEntity setUser(UserEntity user) {
        this.user = user;
        return this;
    }

    @ManyToOne
    public OrderEntity getOrder() {
        return order;
    }

    public CartItemEntity setOrder(OrderEntity order) {
        this.order = order;
        return this;
    }
}
