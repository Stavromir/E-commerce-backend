package bg.softuni.Spring.ecommerce.app.service.impl;

import bg.softuni.Spring.ecommerce.app.model.dto.SignupRequest;
import bg.softuni.Spring.ecommerce.app.model.dto.UserDto;
import bg.softuni.Spring.ecommerce.app.model.entity.UserEntity;
import bg.softuni.Spring.ecommerce.app.model.enums.UserRoleEnum;
import bg.softuni.Spring.ecommerce.app.repository.UserRepository;
import bg.softuni.Spring.ecommerce.app.service.AuthService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final String adminName;
    private final String adminEmail;
    private final String adminPassword;
    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository,
                           @Value("${admin.name}") String adminName,
                           @Value("${admin.email}") String adminEmail,
                           @Value("${admin.password}") String adminPassword) {
        this.userRepository = userRepository;
        this.adminName = adminName;
        this.adminEmail = adminEmail;
        this.adminPassword = adminPassword;
    }

    @Override
    public UserDto createUser(SignupRequest signupRequest) {

        UserEntity user = new UserEntity()
                .setEmail(signupRequest.getEmail())
                .setName(signupRequest.getName())
                .setPassword(Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8().encode(signupRequest.getPassword()))
//                .setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()))
                .setRole(UserRoleEnum.CUSTOMER);

        UserEntity save = userRepository.save(user);

        UserDto userDto = new UserDto()
                .setId(save.getId());

        return userDto;
    }

    @Override
    public boolean hasUserWithEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }


    @PostConstruct
    public void createAdminAccount() {

        if (userRepository.findUserEntityByRole(UserRoleEnum.ADMIN).isEmpty()) {
            UserEntity adminUser = new UserEntity()
                    .setEmail(adminEmail)
                    .setName(adminName)
                    .setRole(UserRoleEnum.ADMIN)
                    .setPassword(Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8().encode(adminPassword));
            userRepository.save(adminUser);
        }
    }
}
