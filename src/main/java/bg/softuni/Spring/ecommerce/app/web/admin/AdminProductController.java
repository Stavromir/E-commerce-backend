package bg.softuni.Spring.ecommerce.app.web.admin;

import bg.softuni.Spring.ecommerce.app.model.dto.FAQDto;
import bg.softuni.Spring.ecommerce.app.model.dto.ProductDto;
import bg.softuni.Spring.ecommerce.app.service.FAQService;
import bg.softuni.Spring.ecommerce.app.service.ProductService;
import bg.softuni.Spring.ecommerce.app.service.exception.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminProductController {

    private final ProductService productService;
    private final FAQService faqService;

    public AdminProductController(ProductService productService,
                                  FAQService faqService) {
        this.productService = productService;
        this.faqService = faqService;
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

    @GetMapping("/search/{name}")
    public ResponseEntity<List<ProductDto>> searchProductByTitle(@PathVariable String name) {
        List<ProductDto> allProductsByName = productService.searchProductByTitle(name);
        return ResponseEntity.ok(allProductsByName);
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("productId") Long productId) {

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/faq")
    public ResponseEntity<?> createFAQ(@RequestBody FAQDto faqDto) {

            Long faqId = faqService.createFAQ(faqDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(faqId);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable("productId") Long productId) {

            ProductDto productDtoById = productService.getProductDtoById(productId);
            return ResponseEntity.ok(productDtoById);
    }

    @PutMapping("/product/update")
    public ResponseEntity<?> updateProduct(@ModelAttribute ProductDto productDto) throws IOException {

            Long productId = productService.updateProduct(productDto);
            return ResponseEntity.status(HttpStatus.OK).body(productId);
    }
}
