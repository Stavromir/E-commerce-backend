package bg.softuni.Spring.ecommerce.app.service;

import bg.softuni.Spring.ecommerce.app.model.dto.OrderedProductsResponseDto;
import bg.softuni.Spring.ecommerce.app.model.dto.ProductDetailDto;
import bg.softuni.Spring.ecommerce.app.model.dto.ProductDto;
import bg.softuni.Spring.ecommerce.app.model.entity.ProductEntity;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    ProductDto addProduct(ProductDto productDto) throws IOException;

    List<ProductDto> getAllProducts ();

    List<ProductDto> searchProductByTitle(String name);

    boolean deleteProduct(Long id);

    boolean existById(Long productId);

    ProductEntity getProductById(Long productId);

    ProductDto getProductDtoById(Long productId);

    Long updateProduct(ProductDto productDto) throws IOException;
    OrderedProductsResponseDto getOrderedProductsDetailsByOrderId(Long id);

    ProductDetailDto getProductDetailsById(Long productId);

}
