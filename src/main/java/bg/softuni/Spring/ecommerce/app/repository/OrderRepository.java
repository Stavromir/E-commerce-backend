package bg.softuni.Spring.ecommerce.app.repository;

import bg.softuni.Spring.ecommerce.app.model.entity.OrderEntity;
import bg.softuni.Spring.ecommerce.app.model.enums.OrderStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    Optional<OrderEntity> findByUserIdAndOrderStatus(Long user_id, OrderStatusEnum orderStatus);

    List<OrderEntity> findAllByOrderStatusIn(List<OrderStatusEnum> orderStatus);

    List<OrderEntity> findAllByOrderStatus(OrderStatusEnum orderStatus);

    List<OrderEntity> findAllByUserIdAndOrderStatusIn(Long userId, List<OrderStatusEnum> orderStatus);

    Optional<OrderEntity> findByTrackingId(UUID trackingId);

    List<OrderEntity> findAllByDateBetweenAndOrderStatus(LocalDateTime begin, LocalDateTime end, OrderStatusEnum orderStatus);
}
