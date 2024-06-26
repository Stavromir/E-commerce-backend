package bg.softuni.Spring.ecommerce.app.service.impl;

import bg.softuni.Spring.ecommerce.app.model.dto.OrderProductsDto;
import bg.softuni.Spring.ecommerce.app.model.dto.ProductDetailDto;
import bg.softuni.Spring.ecommerce.app.model.dto.ProductDto;
import bg.softuni.Spring.ecommerce.app.model.entity.*;
import bg.softuni.Spring.ecommerce.app.repository.ProductRepository;
import bg.softuni.Spring.ecommerce.app.service.ProductService;
import bg.softuni.Spring.ecommerce.app.service.exception.ObjectNotFoundException;
import bg.softuni.Spring.ecommerce.app.service.testUtils.FAQTestDataUtil;
import bg.softuni.Spring.ecommerce.app.service.testUtils.OrderTestDataUtil;
import bg.softuni.Spring.ecommerce.app.service.testUtils.ProductTestDataUtil;
import bg.softuni.Spring.ecommerce.app.service.testUtils.ReviewTestDataUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
    @Autowired
    private FAQTestDataUtil faqTestDataUtil;
    @Autowired
    private ReviewTestDataUtil reviewTestDataUtil;


    @BeforeEach
    void setUp() {
        faqTestDataUtil.clearAllTestData();
        reviewTestDataUtil.clearAllTestData();
        productTestDataUtil.clearAllTestData();
        orderTestDataUtil.clearAllTestData();
    }

    @AfterEach
    void tearDown() {
        faqTestDataUtil.clearAllTestData();
        reviewTestDataUtil.clearAllTestData();
        productTestDataUtil.clearAllTestData();
        orderTestDataUtil.clearAllTestData();
    }


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

    @Test
    void testGetProductById() {
        ProductEntity testProduct = productTestDataUtil.createProduct();

        ProductEntity returnedProduct = productService.getProductById(testProduct.getId());

        assertNotNull(returnedProduct);
        assertEquals(testProduct.getName(), returnedProduct.getName());
    }

    @Test
    void testGetProductByIdThrowExc() {
        assertThrows(
                ObjectNotFoundException.class,
                () -> productService.getProductById(101L)
        );
    }

    @Test
    void testGetProductDtoById() {
        ProductEntity testProduct = productTestDataUtil.createProduct();

        ProductDto productDtoById = productService.getProductDtoById(testProduct.getId());

        assertNotNull(productDtoById);
        assertEquals(testProduct.getId(), productDtoById.getId());
        assertEquals(testProduct.getName(), productDtoById.getName());
        assertEquals(testProduct.getPrice(), productDtoById.getPrice());
        assertEquals(testProduct.getDescription(), productDtoById.getDescription());
        assertEquals(testProduct.getCategory().getName(), productDtoById.getCategoryName());
        assertEquals(testProduct.getCategory().getId(), productDtoById.getCategoryId());
    }

    @Test
    void testUpdateProduct() throws IOException {
        ProductEntity testProduct = productTestDataUtil.createProduct();

        ProductDto updatedProductDto = productTestDataUtil.createUpdatedProductDto(testProduct.getId());
        productService.updateProduct(updatedProductDto);

        Optional<ProductEntity> optionalProduct = productRepository
                .findById(testProduct.getId());

        assertTrue(optionalProduct.isPresent());
        ProductEntity updatedProduct = optionalProduct.get();
        assertEquals(updatedProductDto.getName(), updatedProduct.getName());
        assertEquals(updatedProductDto.getDescription(), updatedProduct.getDescription());
    }

    @Test
    void testGetOrderProductsDetailsByOrderId() {
        OrderEntity testOrder = orderTestDataUtil.createFilledOrderInitialQuantity();
        CartItemEntity cartItem = testOrder.getCartItems().get(0);
        ProductEntity testProduct = cartItem.getProduct();

        OrderProductsDto orderProductsDto = productService
                .getOrderProductsDetailsByOrderId(testOrder.getId());

        List<ProductDto> productDtos = orderProductsDto.getProductDtos();

        assertEquals(testOrder.getAmount(), orderProductsDto.getOrderAmount());
        assertEquals(1, productDtos.size());
        assertEquals(testProduct.getId(), productDtos.get(0).getId());
        assertEquals(testProduct.getName(), productDtos.get(0).getName());
        assertEquals(testProduct.getPrice(), productDtos.get(0).getPrice());
        assertEquals(cartItem.getQuantity(), productDtos.get(0).getQuantity());
        assertArrayEquals(testProduct.getImg(), productDtos.get(0).getByteImg());
    }

    @Test
    void testGetProductDetailsById() {

        ProductEntity testProduct = productTestDataUtil.createProduct();
        ReviewEntity testReview = reviewTestDataUtil.createReviewEntity();
        FAQEntity faqEntity = faqTestDataUtil.createFaqEntity();


        ProductDetailDto productDetailsById = productService
                .getProductDetailsById(testProduct.getId());

        assertEquals(testProduct.getId(), productDetailsById.getProductDto().getId());
        assertEquals(1, productDetailsById.getReviewDtos().size());
        assertEquals(1, productDetailsById.getFaqDtos().size());
        assertEquals(testReview.getId(), productDetailsById.getReviewDtos().get(0).getId());
        assertEquals(faqEntity.getId(), productDetailsById.getFaqDtos().get(0).getId());
    }
}