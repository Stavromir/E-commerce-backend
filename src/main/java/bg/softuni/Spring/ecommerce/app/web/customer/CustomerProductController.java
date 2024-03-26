package bg.softuni.Spring.ecommerce.app.web.customer;

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

    @GetMapping("/search/{name}")
    public ResponseEntity<List<ProductDto>> searchProductByTitle(@PathVariable String name) {
        List<ProductDto> allProductsByName = productService.searchProductByTitle(name);
        return ResponseEntity.ok(allProductsByName);
    }
}