package bg.softuni.Spring.ecommerce.app.model.dto;

import org.springframework.web.multipart.MultipartFile;

public class ProductDto {

    private Long id;
    private String name;
    private Long price;
    private String description;
    private byte[] byteImg;
    private Long categoryId;
    private MultipartFile img;

    public ProductDto() {
    }

    public Long getId() {
        return id;
    }

    public ProductDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProductDto setName(String name) {
        this.name = name;
        return this;
    }

    public Long getPrice() {
        return price;
    }

    public ProductDto setPrice(Long price) {
        this.price = price;
        return this;
    }

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

    public MultipartFile getImg() {
        return img;
    }

    public ProductDto setImg(MultipartFile img) {
        this.img = img;
        return this;
    }
}
