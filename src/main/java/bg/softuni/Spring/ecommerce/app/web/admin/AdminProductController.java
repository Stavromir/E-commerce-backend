package bg.softuni.Spring.ecommerce.app.web.admin;

import bg.softuni.Spring.ecommerce.app.model.dto.FAQDto;
import bg.softuni.Spring.ecommerce.app.model.dto.ProductDto;
import bg.softuni.Spring.ecommerce.app.service.FAQService;
import bg.softuni.Spring.ecommerce.app.service.ProductService;
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
    @PostMapping("/products")
    public ResponseEntity<Long> addProduct (@ModelAttribute ProductDto productDto) throws IOException {
        ProductDto returnedProductDto = productService.addProduct(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(returnedProductDto.getId());
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

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("productId") Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/faqs")
    public ResponseEntity<Long> createFAQ(@RequestBody FAQDto faqDto) {

            Long faqId = faqService.createFAQ(faqDto).getId();
            return ResponseEntity.status(HttpStatus.CREATED).body(faqId);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("productId") Long productId) {

            ProductDto productDtoById = productService.getProductDtoById(productId);
            return ResponseEntity.ok(productDtoById);
    }

    @PutMapping("/products")
    public ResponseEntity<Long> updateProduct(@ModelAttribute ProductDto productDto) throws IOException {

            Long productId = productService.updateProduct(productDto);
            return ResponseEntity.status(HttpStatus.OK).body(productId);
    }
}
