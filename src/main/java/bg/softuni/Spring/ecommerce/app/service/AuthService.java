package bg.softuni.Spring.ecommerce.app.service;

import bg.softuni.Spring.ecommerce.app.model.dto.SignupRequest;
import bg.softuni.Spring.ecommerce.app.model.dto.UserDto;

public interface AuthService {

    UserDto createUser(SignupRequest signupRequest);
}
