package bg.softuni.Spring.ecommerce.app.service;

import bg.softuni.Spring.ecommerce.app.model.dto.SignupRequest;
import bg.softuni.Spring.ecommerce.app.model.dto.UserDto;

public interface UserService {

    UserDto createUser(SignupRequest signupRequest);

    boolean hasUserWithEmail(String email);

    boolean existById(Long userId);

}
