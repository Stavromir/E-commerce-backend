package bg.softuni.Spring.ecommerce.app.model.dto;

import java.util.List;

public class OrderedProductsDto {

    private List<ProductDto> productDtos;
    private Long orderAmount;

    public List<ProductDto> getProductDtos() {
        return productDtos;
    }

    public OrderedProductsDto setProductDtos(List<ProductDto> productDtos) {
        this.productDtos = productDtos;
        return this;
    }

    public Long getOrderAmount() {
        return orderAmount;
    }

    public OrderedProductsDto setOrderAmount(Long orderAmount) {
        this.orderAmount = orderAmount;
        return this;
    }
}
