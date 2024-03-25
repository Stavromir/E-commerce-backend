package bg.softuni.Spring.ecommerce.app.web;

import bg.softuni.Spring.ecommerce.app.model.dto.ProductDto;
import bg.softuni.Spring.ecommerce.app.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    // @ModelAttribute не ми допада
    @PostMapping("/product")
    public ResponseEntity<ProductDto> addProduct (@ModelAttribute ProductDto productDto) throws IOException {
        ProductDto productDto1 = productService.addProduct(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(productDto1);
    }


    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getProducts () {
        List<ProductDto> allProducts = productService.getAllProducts();
        return ResponseEntity.ok(allProducts);
    }
}
