package bg.softuni.Spring.ecommerce.app.web;

import bg.softuni.Spring.ecommerce.app.model.dto.OrderDto;
import bg.softuni.Spring.ecommerce.app.service.OrderService;
import bg.softuni.Spring.ecommerce.app.service.exception.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class TrackingController {

    private final OrderService orderService;

    public TrackingController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/order/{trackingId}")
    public ResponseEntity<?> findOrderByTrackingId(@PathVariable("trackingId") UUID trackingId) {
        try {
            OrderDto orderDto = orderService.searchOrderByTrackingId(trackingId);
            return ResponseEntity.ok(orderDto);
        } catch (ObjectNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
}
