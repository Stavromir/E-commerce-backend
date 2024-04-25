package bg.softuni.Spring.ecommerce.app.web.customer;

import bg.softuni.Spring.ecommerce.app.model.dto.AddProductInCartDto;
import bg.softuni.Spring.ecommerce.app.model.dto.OrderDto;
import bg.softuni.Spring.ecommerce.app.model.dto.PlaceOrderDto;
import bg.softuni.Spring.ecommerce.app.service.OrderService;
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


    @PostMapping("/carts")
    public ResponseEntity<Long> addProductToCart(@RequestBody AddProductInCartDto addProductInCardDto) {

        Long cartItemId = orderService.addProductToCart(addProductInCardDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemId);

    }

    @GetMapping("/carts/{userId}")
    public ResponseEntity<OrderDto> getCartByUserId(@PathVariable Long userId) {

        OrderDto cartByUserId = orderService.getCartByUserId(userId);
        return ResponseEntity.ok(cartByUserId);
    }

    @GetMapping("/coupons/{userId}/{code}")
    public ResponseEntity<OrderDto> applyCoupon(@PathVariable("userId") Long userId,
                                         @PathVariable("code") String code
    ) {
        OrderDto orderDto = orderService.applyCoupon(userId, code);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(orderDto);
    }

    @PostMapping("/carts/addition")
    public ResponseEntity<Long> increaseQuantity(@RequestBody AddProductInCartDto addProductInCardDto) {

        Long orderId = orderService.increaseProductQuantity(addProductInCardDto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(orderId);
    }

    @PostMapping("/carts/subtraction")
    public ResponseEntity<Long> decreaseQuantity(@RequestBody AddProductInCartDto addProductInCardDto) {

        Long orderId = orderService.decreaseProductQuantity(addProductInCardDto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(orderId);
    }

    @PostMapping("/orders")
    public ResponseEntity<Long> placeOrder(@RequestBody PlaceOrderDto placeOrderDto) {

        Long orderId = orderService.placeOrder(placeOrderDto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(orderId);
    }

    @GetMapping("/orders/{userId}")
    public ResponseEntity<List<OrderDto>> getOrdersByUserId(@PathVariable Long userId) {
        List<OrderDto> userPlacedOrders = orderService.getUserPlacedOrders(userId);
        return ResponseEntity.ok(userPlacedOrders);
    }
}
