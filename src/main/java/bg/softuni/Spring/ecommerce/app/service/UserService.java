package bg.softuni.Spring.ecommerce.app.service;

import bg.softuni.Spring.ecommerce.app.model.dto.SignupRequestDto;
import bg.softuni.Spring.ecommerce.app.model.dto.UserDto;
import bg.softuni.Spring.ecommerce.app.model.entity.UserEntity;

public interface UserService {

    UserDto createUser(SignupRequestDto signupRequest);

    boolean hasUserWithEmail(String email);

    boolean existById(Long userId);

    UserEntity getUserById(Long userId);

}
