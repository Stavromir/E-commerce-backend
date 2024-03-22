package bg.softuni.Spring.ecommerce.app.service;

import bg.softuni.Spring.ecommerce.app.model.dto.SignupRequest;
import bg.softuni.Spring.ecommerce.app.model.dto.UserDto;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;

public interface AuthService {

    UserDto createUser(SignupRequest signupRequest);

    boolean hasUserWithEmail(String email);

}
