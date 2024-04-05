package bg.softuni.Spring.ecommerce.app.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "reviews")
public class ReviewEntity extends BaseEntity{

    private Long rating;
    private String description;
    private byte[] img;
    private UserEntity user;
    private ProductEntity product;

    public ReviewEntity() {
    }

    @Column(nullable = false)
    public Long getRating() {
        return rating;
    }

    public ReviewEntity setRating(Long rating) {
        this.rating = rating;
        return this;
    }

    @Column(columnDefinition = "TEXT", nullable = false)
    public String getDescription() {
        return description;
    }

    public ReviewEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    @Lob
    @Column(columnDefinition = "BLOB")
    public byte[] getImg() {
        return img;
    }

    public ReviewEntity setImg(byte[] img) {
        this.img = img;
        return this;
    }

    @ManyToOne(optional = false)
    public UserEntity getUser() {
        return user;
    }

    public ReviewEntity setUser(UserEntity user) {
        this.user = user;
        return this;
    }

    @ManyToOne(optional = false)
    public ProductEntity getProduct() {
        return product;
    }

    public ReviewEntity setProduct(ProductEntity product) {
        this.product = product;
        return this;
    }
}
