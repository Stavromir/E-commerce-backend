package bg.softuni.Spring.ecommerce.app.model.dto;

public class AddProductInCartDto {

    private Long userId;
    private Long productId;

    public AddProductInCartDto() {
    }

    public Long getUserId() {
        return userId;
    }

    public AddProductInCartDto setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Long getProductId() {
        return productId;
    }

    public AddProductInCartDto setProductId(Long productId) {
        this.productId = productId;
        return this;
    }
}
