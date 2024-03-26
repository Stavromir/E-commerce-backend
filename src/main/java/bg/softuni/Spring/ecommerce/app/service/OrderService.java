package bg.softuni.Spring.ecommerce.app.service;

import bg.softuni.Spring.ecommerce.app.model.entity.UserEntity;

public interface OrderService {

    void createEmptyOrder(UserEntity user);
}
