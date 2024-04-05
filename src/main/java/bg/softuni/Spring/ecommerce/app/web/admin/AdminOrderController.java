package bg.softuni.Spring.ecommerce.app.web.admin;

import bg.softuni.Spring.ecommerce.app.model.dto.AnalyticsResponseDto;
import bg.softuni.Spring.ecommerce.app.model.dto.OrderDto;
import bg.softuni.Spring.ecommerce.app.service.OrderService;
import bg.softuni.Spring.ecommerce.app.service.exception.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminOrderController {

    private final OrderService orderService;

    public AdminOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/placedOrders")
    public ResponseEntity<List<OrderDto>> getAllPlacedOrders(){

        List<OrderDto> allPlacedOrders = orderService.getAllPlacedOrders();
        return ResponseEntity.status(HttpStatus.OK).body(allPlacedOrders);
    }

    @GetMapping("/changeStatus/{orderId}/{status}")
    public ResponseEntity<?> changeOrderStatus(@PathVariable("orderId") Long orderId,
                                               @PathVariable("status") String status){

            Long changedOrderId = orderService.changeOrderStatus(orderId, status);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(changedOrderId);
    }

    @GetMapping("/order/analytics")
    public ResponseEntity<AnalyticsResponseDto> getAnalytics(){
        return ResponseEntity.ok(orderService.getAnalytics());
    }
}
