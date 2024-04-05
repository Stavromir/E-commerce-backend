package bg.softuni.Spring.ecommerce.app.model.dto;

import bg.softuni.Spring.ecommerce.app.model.entity.ProductEntity;
import bg.softuni.Spring.ecommerce.app.model.entity.UserEntity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import org.springframework.web.multipart.MultipartFile;

public class ReviewDto {

    private Long id;
    private Long rating;
    private String description;
    private MultipartFile img;
    private byte[] returnedImg;
    private Long userId;
    private Long productId;
    private String username;

    public Long getId() {
        return id;
    }

    public ReviewDto setId(Long id) {
        this.id = id;
        return this;
    }

    @Positive
    public Long getRating() {
        return rating;
    }

    public ReviewDto setRating(Long rating) {
        this.rating = rating;
        return this;
    }

    @NotEmpty
    public String getDescription() {
        return description;
    }

    public ReviewDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public MultipartFile getImg() {
        return img;
    }

    public ReviewDto setImg(MultipartFile img) {
        this.img = img;
        return this;
    }

    public byte[] getReturnedImg() {
        return returnedImg;
    }

    public ReviewDto setReturnedImg(byte[] returnedImg) {
        this.returnedImg = returnedImg;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public ReviewDto setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Long getProductId() {
        return productId;
    }

    public ReviewDto setProductId(Long productId) {
        this.productId = productId;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public ReviewDto setUsername(String username) {
        this.username = username;
        return this;
    }
}
