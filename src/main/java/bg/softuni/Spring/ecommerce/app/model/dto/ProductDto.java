package bg.softuni.Spring.ecommerce.app.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.web.multipart.MultipartFile;

public class ProductDto {

    private Long id;
    private String name;
    private Long price;
    private String description;
    private byte[] byteImg;
    private Long categoryId;
    private String categoryName;
    private MultipartFile img;
    private Long quantity;

    public ProductDto() {
    }

    public Long getId() {
        return id;
    }

    public ProductDto setId(Long id) {
        this.id = id;
        return this;
    }

    @NotBlank
    public String getName() {
        return name;
    }

    public ProductDto setName(String name) {
        this.name = name;
        return this;
    }

    @Positive
    public Long getPrice() {
        return price;
    }

    public ProductDto setPrice(Long price) {
        this.price = price;
        return this;
    }

    @NotBlank
    public String getDescription() {
        return description;
    }

    public ProductDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public byte[] getByteImg() {
        return byteImg;
    }

    public ProductDto setByteImg(byte[] byteImg) {
        this.byteImg = byteImg;
        return this;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public ProductDto setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public ProductDto setCategoryName(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }

    public MultipartFile getImg() {
        return img;
    }

    public ProductDto setImg(MultipartFile img) {
        this.img = img;
        return this;
    }

    public Long getQuantity() {
        return quantity;
    }

    public ProductDto setQuantity(Long quantity) {
        this.quantity = quantity;
        return this;
    }
}
