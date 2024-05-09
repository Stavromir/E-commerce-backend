package bg.softuni.Spring.ecommerce.app.service.impl;

import bg.softuni.Spring.ecommerce.app.model.dto.*;
import bg.softuni.Spring.ecommerce.app.model.entity.CategoryEntity;
import bg.softuni.Spring.ecommerce.app.model.entity.ProductEntity;
import bg.softuni.Spring.ecommerce.app.repository.ProductRepository;
import bg.softuni.Spring.ecommerce.app.service.*;
import bg.softuni.Spring.ecommerce.app.service.exception.ObjectNotFoundException;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final OrderService orderService;
    private final ReviewService reviewService;
    private final FAQService faqService;

    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryService categoryService,
                              @Lazy OrderService orderService,
                              ReviewService reviewService,
                              FAQService faqService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.orderService = orderService;
        this.reviewService = reviewService;
        this.faqService = faqService;
    }

    @Override
    public ProductDto addProduct(ProductDto productDto) throws IOException {
        ProductEntity productEntity = new ProductEntity();

        setProductFields(productDto, productEntity);

        ProductEntity savedProduct = productRepository.save(productEntity);

        return mapToProductDto(savedProduct);
    }

    @Override
    public List<ProductDto> getAllProducts() {

        return productRepository.findAll()
                .stream()
                .map(ProductServiceImpl::mapToProductDto)
                .toList();
    }

    @Override
    public List<ProductDto> findProductsByTitle(String name) {

        return productRepository.findAllByNameContaining(name)
                .stream()
                .map(ProductServiceImpl::mapToProductDto)
                .toList();
    }

    @Override
    public void deleteProduct(Long id) {

        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Product does not exist"));

        productRepository.delete(product);
    }


    @Override
    public ProductEntity getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ObjectNotFoundException("Product not exist"));
    }

    @Override
    public ProductDto getProductDtoById(Long productId) {
        ProductEntity product = getProductById(productId);
        return mapToProductDto(product);
    }

    @Override
    public Long updateProduct(ProductDto productDto) throws IOException {
        ProductEntity product = getProductById(productDto.getId());

        setProductFields(productDto, product);

        return productRepository.save(product).getId();
    }

    @Override
    public OrderProductsDto getOrderProductsDetailsByOrderId(Long id) {
        OrderDto orderById = orderService.getOrderDtoById(id);
        List<CartItemDto> cartItems = orderById.getCartItems();

        OrderProductsDto responseDto = new OrderProductsDto();
        responseDto.setOrderAmount(orderById.getAmount());
        List<ProductDto> productDtos = new ArrayList<>();

        for (CartItemDto cartItem : cartItems) {
            ProductDto productDto = new ProductDto();
            productDto
                    .setId(cartItem.getProductId())
                    .setName(cartItem.getProductName())
                    .setPrice(cartItem.getPrice())
                    .setQuantity(cartItem.getQuantity())
                    .setByteImg(cartItem.getReturnedImg());

            productDtos.add(productDto);
        }

        responseDto.setProductDtos(productDtos);
        return responseDto;
    }

    @Override
    public ProductDetailDto getProductDetailsById(Long productId) {
        ProductEntity product = getProductById(productId);
        ProductDto productDto = mapToProductDto(product);

        List<ReviewDto> allReviewsByProductId = reviewService.getAllReviewsByProductId(productId);
        List<FAQDto> allFaq = faqService.getAllFaqByProductId(productId);

        return new ProductDetailDto()
                .setProductDto(productDto)
                .setFaqDtos(allFaq)
                .setReviewDtos(allReviewsByProductId);
    }

    private static ProductDto mapToProductDto(ProductEntity savedProduct) {

        return new ProductDto()
                .setId(savedProduct.getId())
                .setName(savedProduct.getName())
                .setPrice(savedProduct.getPrice())
                .setDescription(savedProduct.getDescription())
                .setByteImg(savedProduct.getImg())
                .setCategoryId(savedProduct.getCategory().getId())
                .setCategoryName(savedProduct.getCategory().getName());
    }

    private void setProductFields(ProductDto productDto, ProductEntity productEntity) throws IOException {
        CategoryEntity categoryEntity = categoryService.findById(productDto.getCategoryId());

        productEntity
                .setName(productDto.getName())
                .setDescription(productDto.getDescription())
                .setPrice(productDto.getPrice())
                .setCategory(categoryEntity);

        if (productDto.getImg() != null) {
            productEntity
                    .setImg(productDto.getImg().getBytes());
        }
    }
}
