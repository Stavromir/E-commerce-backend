package bg.softuni.Spring.ecommerce.app.web.customer;

import bg.softuni.Spring.ecommerce.app.model.dto.AddProductInCardDto;
import bg.softuni.Spring.ecommerce.app.model.dto.OrderDto;
import bg.softuni.Spring.ecommerce.app.service.OrderService;
import bg.softuni.Spring.ecommerce.app.service.ProductService;
import bg.softuni.Spring.ecommerce.app.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
public class OrderController {


    private final OrderService orderService;
    private final ProductService productService;
    private final UserService userService;

    public OrderController(OrderService orderService,
                           ProductService productService,
                           UserService userService) {
        this.orderService = orderService;
        this.productService = productService;
        this.userService = userService;
    }


    @PostMapping("/cart")
    public ResponseEntity<?> addProductToCart(@RequestBody AddProductInCardDto addProductInCardDto) {

        if (orderService.isCartItemPresent(addProductInCardDto)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

        if (userService.existById(addProductInCardDto.getUserId()) &&
                productService.existById(addProductInCardDto.getProductId())) {

            Long cartItemId = orderService.addProductToCart(addProductInCardDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(cartItemId);

        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or product not found");
        }
    }

    @GetMapping("/cart/{userId}")
    public ResponseEntity<OrderDto> getCartByUserId(@RequestParam("userId") Long userId) {

        OrderDto cartByUserId = orderService.getCartByUserId(userId);
        return ResponseEntity.ok(cartByUserId);
    }


}
