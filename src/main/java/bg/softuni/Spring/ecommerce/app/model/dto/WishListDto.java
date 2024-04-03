package bg.softuni.Spring.ecommerce.app.model.dto;

public class WishListDto {

    private Long id;
    private Long userId;
    private Long productId;
    private String productName;
    private String productDescription;
    private Long price;
    private byte[] returnedImg;

    public Long getId() {
        return id;
    }

    public WishListDto setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public WishListDto setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Long getProductId() {
        return productId;
    }

    public WishListDto setProductId(Long productId) {
        this.productId = productId;
        return this;
    }

    public String getProductName() {
        return productName;
    }

    public WishListDto setProductName(String productName) {
        this.productName = productName;
        return this;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public WishListDto setProductDescription(String productDescription) {
        this.productDescription = productDescription;
        return this;
    }

    public Long getPrice() {
        return price;
    }

    public WishListDto setPrice(Long price) {
        this.price = price;
        return this;
    }

    public byte[] getReturnedImg() {
        return returnedImg;
    }

    public WishListDto setReturnedImg(byte[] returnedImg) {
        this.returnedImg = returnedImg;
        return this;
    }
}
