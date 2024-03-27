package bg.softuni.Spring.ecommerce.app.service;

import bg.softuni.Spring.ecommerce.app.model.dto.ProductDto;
import bg.softuni.Spring.ecommerce.app.model.entity.ProductEntity;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    ProductDto addProduct(ProductDto productDto) throws IOException;

    List<ProductDto> getAllProducts ();

    List<ProductDto> searchProductByTitle(String name);

    boolean deleteProduct(Long id);

    boolean existById(Long productId);

    ProductEntity getProductById(Long productId);

}
