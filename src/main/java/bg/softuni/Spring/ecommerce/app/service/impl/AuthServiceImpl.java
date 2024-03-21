package bg.softuni.Spring.ecommerce.app.service.impl;

import bg.softuni.Spring.ecommerce.app.model.dto.SignupRequest;
import bg.softuni.Spring.ecommerce.app.model.dto.UserDto;
import bg.softuni.Spring.ecommerce.app.model.entity.UserEntity;
import bg.softuni.Spring.ecommerce.app.model.enums.UserRoleEnum;
import bg.softuni.Spring.ecommerce.app.repository.UserRepository;
import bg.softuni.Spring.ecommerce.app.service.AuthService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto createUser(SignupRequest signupRequest) {

        UserEntity user = new UserEntity()
                .setEmail(signupRequest.getEmail())
                .setName(signupRequest.getName())
                .setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()))
                .setRole(UserRoleEnum.CUSTOMER);

        UserEntity save = userRepository.save(user);

        UserDto userDto = new UserDto()
                .setId(save.getId());

        return userDto;
    }
}
