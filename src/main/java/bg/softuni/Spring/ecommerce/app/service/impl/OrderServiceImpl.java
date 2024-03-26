package bg.softuni.Spring.ecommerce.app.service.impl;

import bg.softuni.Spring.ecommerce.app.model.entity.OrderEntity;
import bg.softuni.Spring.ecommerce.app.model.entity.UserEntity;
import bg.softuni.Spring.ecommerce.app.model.enums.OrderStatusEnum;
import bg.softuni.Spring.ecommerce.app.repository.OrderRepository;
import bg.softuni.Spring.ecommerce.app.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void createEmptyOrder(UserEntity user) {

        OrderEntity emptyOrder = new OrderEntity()
                .setAmount(0L)
                .setTotalAmount(0L)
                .setDiscount(0L)
                .setUser(user)
                .setOrderStatus(OrderStatusEnum.PENDING);

        orderRepository.save(emptyOrder);
    }

    @Override
    public OrderEntity getActiveOrder(Long userId, OrderStatusEnum orderStatus) {
        OrderEntity activeOrder = orderRepository.findByUserIdAndOrderStatus(userId, orderStatus)
                .orElseThrow(() -> new IllegalArgumentException("Order not exist"));
        return activeOrder;
    }
}
