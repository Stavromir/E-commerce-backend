package bg.softuni.Spring.ecommerce.app.model.dto;

import java.util.List;

public class ProductDetailDto {

    private ProductDto productDto;
    private List<ReviewDto> reviewDtos;
    private List<FAQDto> faqDtos;

    public ProductDto getProductDto() {
        return productDto;
    }

    public ProductDetailDto setProductDto(ProductDto productDto) {
        this.productDto = productDto;
        return this;
    }

    public List<ReviewDto> getReviewDtos() {
        return reviewDtos;
    }

    public ProductDetailDto setReviewDtos(List<ReviewDto> reviewDtos) {
        this.reviewDtos = reviewDtos;
        return this;
    }

    public List<FAQDto> getFaqDtos() {
        return faqDtos;
    }

    public ProductDetailDto setFaqDtos(List<FAQDto> faqDtos) {
        this.faqDtos = faqDtos;
        return this;
    }
}
