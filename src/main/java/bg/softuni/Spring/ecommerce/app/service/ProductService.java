package bg.softuni.Spring.ecommerce.app.service;

import bg.softuni.Spring.ecommerce.app.model.dto.OrderProductsDto;
import bg.softuni.Spring.ecommerce.app.model.dto.ProductDetailDto;
import bg.softuni.Spring.ecommerce.app.model.dto.ProductDto;
import bg.softuni.Spring.ecommerce.app.model.entity.ProductEntity;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    ProductDto addProduct(ProductDto productDto) throws IOException;

    List<ProductDto> getAllProducts ();

    List<ProductDto> findProductsByTitle(String name);

    void deleteProduct(Long id);

    ProductEntity getProductById(Long productId);

    ProductDto getProductDtoById(Long productId);

    Long updateProduct(ProductDto productDto) throws IOException;
    OrderProductsDto getOrderProductsDetailsByOrderId(Long id);

    ProductDetailDto getProductDetailsById(Long productId);

}
