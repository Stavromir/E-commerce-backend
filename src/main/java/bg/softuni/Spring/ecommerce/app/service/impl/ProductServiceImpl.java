package bg.softuni.Spring.ecommerce.app.service.impl;

import bg.softuni.Spring.ecommerce.app.model.dto.ProductDto;
import bg.softuni.Spring.ecommerce.app.model.entity.CategoryEntity;
import bg.softuni.Spring.ecommerce.app.model.entity.ProductEntity;
import bg.softuni.Spring.ecommerce.app.repository.CategoryRepository;
import bg.softuni.Spring.ecommerce.app.repository.ProductRepository;
import bg.softuni.Spring.ecommerce.app.service.ProductService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ProductDto addProduct(ProductDto productDto) throws IOException {

        CategoryEntity categoryEntity = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not exist"));

        ProductEntity productEntity = new ProductEntity()
                .setName(productDto.getName())
                .setDescription(productDto.getDescription())
                .setPrice(productDto.getPrice())
                .setImg(productDto.getImg().getBytes())
                .setCategory(categoryEntity);

        ProductEntity savedProduct = productRepository.save(productEntity);

        return getProductDto(savedProduct);

    }

    @Override
    public List<ProductDto> getAllProducts() {

        List<ProductDto> productDtos = productRepository.findAll()
                .stream()
                .map(ProductServiceImpl::getProductDto)
                .toList();


        return productDtos;
    }

    private static ProductDto getProductDto(ProductEntity savedProduct) {

        return new ProductDto()
                .setId(savedProduct.getId())
                .setName(savedProduct.getName())
                .setDescription(savedProduct.getDescription())
                .setPrice(savedProduct.getPrice())
                .setByteImg(savedProduct.getImg())
                .setCategoryId(savedProduct.getCategory().getId());
    }
}
