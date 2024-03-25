package bg.softuni.Spring.ecommerce.app.service;

import bg.softuni.Spring.ecommerce.app.model.dto.ProductDto;

import java.io.IOException;

public interface ProductService {

    ProductDto addProduct(ProductDto productDto) throws IOException;
}
