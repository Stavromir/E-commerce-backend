package bg.softuni.Spring.ecommerce.app.web.customer;

import bg.softuni.Spring.ecommerce.app.model.dto.OrderProductsDto;
import bg.softuni.Spring.ecommerce.app.model.dto.ProductDetailDto;
import bg.softuni.Spring.ecommerce.app.model.dto.ProductDto;
import bg.softuni.Spring.ecommerce.app.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerProductController {


    private final ProductService productService;

    public CustomerProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getProducts () {
        List<ProductDto> allProducts = productService.getAllProducts();
        return ResponseEntity.ok(allProducts);
    }

    @GetMapping("/products/title/{name}")
    public ResponseEntity<List<ProductDto>> searchProductByTitle(@PathVariable String name) {
        List<ProductDto> allProductsByName = productService.findProductsByTitle(name);
        return ResponseEntity.ok(allProductsByName);
    }

    @GetMapping("/products/order/{orderId}")
    public ResponseEntity<OrderProductsDto> getOrderedProductsDetails(@PathVariable("orderId") Long orderId) {
        return ResponseEntity
                .ok(productService.getOrderProductsDetailsByOrderId(orderId));
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductDetailDto> getProductDetailById(@PathVariable("productId") Long productId){

            ProductDetailDto productDetailsById = productService.getProductDetailsById(productId);
            return ResponseEntity.ok(productDetailsById);
    }
}
