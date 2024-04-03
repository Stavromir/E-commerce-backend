package bg.softuni.Spring.ecommerce.app.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "wish_lists")
public class WishListEntity extends BaseEntity {

    private ProductEntity product;
    private UserEntity user;

    public WishListEntity() {
    }

    @ManyToOne
    public ProductEntity getProduct() {
        return product;
    }

    public WishListEntity setProduct(ProductEntity product) {
        this.product = product;
        return this;
    }

    @ManyToOne
    public UserEntity getUser() {
        return user;
    }

    public WishListEntity setUser(UserEntity user) {
        this.user = user;
        return this;
    }
}
