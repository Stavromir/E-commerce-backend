package bg.softuni.Spring.ecommerce.app.service;

import bg.softuni.Spring.ecommerce.app.model.entity.OrderEntity;
import bg.softuni.Spring.ecommerce.app.model.entity.UserEntity;

public interface OrderService {

    OrderEntity createEmptyOrder(UserEntity user);
}
