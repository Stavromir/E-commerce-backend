package bg.softuni.Spring.ecommerce.app.service.impl;

import bg.softuni.Spring.ecommerce.app.model.dto.*;
import bg.softuni.Spring.ecommerce.app.model.entity.CategoryEntity;
import bg.softuni.Spring.ecommerce.app.model.entity.ProductEntity;
import bg.softuni.Spring.ecommerce.app.repository.CategoryRepository;
import bg.softuni.Spring.ecommerce.app.repository.ProductRepository;
import bg.softuni.Spring.ecommerce.app.service.FAQService;
import bg.softuni.Spring.ecommerce.app.service.OrderService;
import bg.softuni.Spring.ecommerce.app.service.ProductService;
import bg.softuni.Spring.ecommerce.app.service.ReviewService;
import bg.softuni.Spring.ecommerce.app.service.exception.ValidationException;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final OrderService orderService;
    private final ReviewService reviewService;
    private final FAQService faqService;

    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryRepository categoryRepository,
                              @Lazy OrderService orderService,
                              ReviewService reviewService,
                              FAQService faqService) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
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

        List<ProductDto> productDtos = productRepository.findAll()
                .stream()
                .map(ProductServiceImpl::mapToProductDto)
                .toList();


        return productDtos;
    }

    @Override
    public List<ProductDto> searchProductByTitle(String name) {

        List<ProductDto> products = productRepository.findAllByNameContaining(name)
                .stream()
                .map(ProductServiceImpl::mapToProductDto)
                .toList();

        return products;
    }

    @Override
    public boolean deleteProduct(Long id) {

        Optional<ProductEntity> product = productRepository.findById(id);

        if (product.isPresent()) {
            productRepository.delete(product.get());
            return true;
        }
        return false;
    }

    @Override
    public boolean existById(Long productId) {

        Optional<ProductEntity> product = productRepository.findById(productId);

        return product.isPresent();
    }

    @Override
    public ProductEntity getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ValidationException("Product not exist"));
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
    public OrderedProductsResponseDto getOrderedProductsDetailsByOrderId(Long id) {
        OrderDto orderById = orderService.getOrderById(id);
        List<CartItemDto> cartItems = orderById.getCartItems();

        OrderedProductsResponseDto responseDto = new OrderedProductsResponseDto();
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
        List<FAQDto> allFaq = faqService.getAllFaq(productId);

        return new ProductDetailDto()
                .setProductDto(productDto)
                .setFaqDtos(allFaq)
                .setReviewDtos(allReviewsByProductId);
    }

    private static ProductDto mapToProductDto(ProductEntity savedProduct) {

        return new ProductDto()
                .setId(savedProduct.getId())
                .setName(savedProduct.getName())
                .setDescription(savedProduct.getDescription())
                .setPrice(savedProduct.getPrice())
                .setByteImg(savedProduct.getImg())
                .setCategoryId(savedProduct.getCategory().getId())
                .setCategoryName(savedProduct.getCategory().getName());
    }

    private void setProductFields(ProductDto productDto, ProductEntity productEntity) throws IOException {
        CategoryEntity categoryEntity = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not exist"));

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
