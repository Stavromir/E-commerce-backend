package bg.softuni.Spring.ecommerce.app.web.customer;

import bg.softuni.Spring.ecommerce.app.model.dto.AddProductInCardDto;
import bg.softuni.Spring.ecommerce.app.model.dto.CartItemDto;
import bg.softuni.Spring.ecommerce.app.service.CartItemService;
import bg.softuni.Spring.ecommerce.app.service.ProductService;
import bg.softuni.Spring.ecommerce.app.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customer")
public class CartController {


    private final CartItemService cartItemService;
    private final ProductService productService;
    private final UserService userService;

    public CartController(CartItemService cartItemService, ProductService productService, UserService userService) {
        this.cartItemService = cartItemService;
        this.productService = productService;
        this.userService = userService;
    }


    @PostMapping("/cart")
    public ResponseEntity<?> addProductToCart(@RequestBody AddProductInCardDto addProductInCardDto) {

        if (cartItemService.isCartItemPresent(addProductInCardDto)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

        if (userService.existById(addProductInCardDto.getUserId()) &&
                productService.existById(addProductInCardDto.getProductId())) {
            CartItemDto cartItemDto = cartItemService.addProductToCart(addProductInCardDto);

            return ResponseEntity.status(HttpStatus.CREATED).body(cartItemDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or product not found");
        }
    }


}
