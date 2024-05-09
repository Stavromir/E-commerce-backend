package bg.softuni.Spring.ecommerce.app.model.dto;

import java.util.List;

public class OrderProductsDto {

    private List<ProductDto> productDtos;
    private Long orderAmount;

    public List<ProductDto> getProductDtos() {
        return productDtos;
    }

    public OrderProductsDto setProductDtos(List<ProductDto> productDtos) {
        this.productDtos = productDtos;
        return this;
    }

    public Long getOrderAmount() {
        return orderAmount;
    }

    public OrderProductsDto setOrderAmount(Long orderAmount) {
        this.orderAmount = orderAmount;
        return this;
    }
}
