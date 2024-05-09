package bg.softuni.Spring.ecommerce.app.service.impl;

import bg.softuni.Spring.ecommerce.app.model.dto.ProductDto;
import bg.softuni.Spring.ecommerce.app.model.entity.ProductEntity;
import bg.softuni.Spring.ecommerce.app.repository.ProductRepository;
import bg.softuni.Spring.ecommerce.app.service.ProductService;
import bg.softuni.Spring.ecommerce.app.service.exception.ObjectNotFoundException;
import bg.softuni.Spring.ecommerce.app.service.testUtils.OrderTestDataUtil;
import bg.softuni.Spring.ecommerce.app.service.testUtils.ProductTestDataUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceImplTest {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductTestDataUtil productTestDataUtil;
    @Autowired
    private OrderTestDataUtil orderTestDataUtil;


    @Test
    void testAddProduct() throws IOException {
        ProductDto testProductDto = productTestDataUtil.createProductDto();

        ProductDto returnedProductDto = productService.addProduct(testProductDto);
        Long productId = returnedProductDto.getId();

        Optional<ProductEntity> optionalProduct = productRepository.findById(productId);

        assertTrue(optionalProduct.isPresent());
        ProductEntity product = optionalProduct.get();

        assertEquals(testProductDto.getName(), product.getName());
        assertEquals(testProductDto.getDescription(), product.getDescription());
        assertEquals(testProductDto.getPrice(), product.getPrice());
        assertEquals(testProductDto.getCategoryId(), product.getCategory().getId());
    }

    @Test
    void testGetAllProducts() {
        ProductEntity testProduct = productTestDataUtil.createProduct();

        List<ProductDto> allProducts = productService.getAllProducts();

        assertEquals(1, allProducts.size());
        assertEquals(testProduct.getId(), allProducts.get(0).getId());
    }

    @Test
    void testFindProductsByTitle() {
        ProductEntity testProduct = productTestDataUtil.createProduct();

        List<ProductDto> productsByTitle = productService
                .findProductsByTitle(testProduct.getName());

        assertEquals(1, productsByTitle.size());
        assertEquals(testProduct.getId(), productsByTitle.get(0).getId());
    }

    @Test
    void testDeleteProduct() {
        ProductEntity testProduct = productTestDataUtil.createProduct();
        Long productId = testProduct.getId();

        productService.deleteProduct(testProduct.getId());

        Optional<ProductEntity> product = productRepository.findById(productId);
        assertTrue(product.isEmpty());
    }

    @Test
    void deleteProductThrowExc() {
        assertThrows(
                ObjectNotFoundException.class,
                () -> productService.deleteProduct(101L)
        );
    }
}