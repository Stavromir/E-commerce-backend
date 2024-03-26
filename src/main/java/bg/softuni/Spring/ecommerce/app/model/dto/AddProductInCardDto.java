package bg.softuni.Spring.ecommerce.app.model.dto;

public class AddProductInCardDto {

    private Long userId;
    private Long productId;

    public AddProductInCardDto() {
    }

    public Long getUserId() {
        return userId;
    }

    public AddProductInCardDto setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Long getProductId() {
        return productId;
    }

    public AddProductInCardDto setProductId(Long productId) {
        this.productId = productId;
        return this;
    }
}
