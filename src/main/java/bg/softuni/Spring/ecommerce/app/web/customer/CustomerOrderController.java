package bg.softuni.Spring.ecommerce.app.web.customer;

import bg.softuni.Spring.ecommerce.app.model.dto.AddProductInCardDto;
import bg.softuni.Spring.ecommerce.app.model.dto.OrderDto;
import bg.softuni.Spring.ecommerce.app.model.dto.PlaceOrderDto;
import bg.softuni.Spring.ecommerce.app.service.OrderService;
import bg.softuni.Spring.ecommerce.app.service.ProductService;
import bg.softuni.Spring.ecommerce.app.service.UserService;
import bg.softuni.Spring.ecommerce.app.service.exception.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerOrderController {


    private final OrderService orderService;

    public CustomerOrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @PostMapping("/cart")
    public ResponseEntity<Long> addProductToCart(@RequestBody AddProductInCardDto addProductInCardDto) {

        Long cartItemId = orderService.addProductToCart(addProductInCardDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemId);

    }

    @GetMapping("/cart/{userId}")
    public ResponseEntity<OrderDto> getCartByUserId(@PathVariable Long userId) {

        OrderDto cartByUserId = orderService.getCartByUserId(userId);
        return ResponseEntity.ok(cartByUserId);
    }

    @GetMapping("/coupon/{userId}/{code}")
    public ResponseEntity<?> applyCoupon(@PathVariable("userId") Long userId,
                                         @PathVariable("code") String code
    ) {
        OrderDto orderDto = orderService.applyCoupon(userId, code);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(orderDto);
    }

    @PostMapping("/addition")
    public ResponseEntity<Long> increaseQuantity(@RequestBody AddProductInCardDto addProductInCardDto) {

        Long orderId = orderService.increaseProductQuantity(addProductInCardDto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(orderId);
    }

    @PostMapping("/subtraction")
    public ResponseEntity<?> decreaseQuantity(@RequestBody AddProductInCardDto addProductInCardDto) {

        Long orderId = orderService.decreaseProductQuantity(addProductInCardDto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(orderId);
    }

    @PostMapping("/placeOrder")
    public ResponseEntity<?> placeOrder(@RequestBody PlaceOrderDto placeOrderDto) {

        Long orderId = orderService.placeOrder(placeOrderDto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(orderId);
    }

    @GetMapping("/myOrders/{userId}")
    public ResponseEntity<List<OrderDto>> getPlacedOrders(@PathVariable Long userId) {
        List<OrderDto> userPlacedOrders = orderService.getUserPlacedOrders(userId);
        return ResponseEntity.ok(userPlacedOrders);
    }
}
