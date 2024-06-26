package bg.softuni.Spring.ecommerce.app.service.impl;

import bg.softuni.Spring.ecommerce.app.model.dto.SignupRequestDto;
import bg.softuni.Spring.ecommerce.app.model.dto.UserDto;
import bg.softuni.Spring.ecommerce.app.model.entity.UserEntity;
import bg.softuni.Spring.ecommerce.app.model.enums.UserRoleEnum;
import bg.softuni.Spring.ecommerce.app.repository.UserRepository;
import bg.softuni.Spring.ecommerce.app.service.UserService;
import bg.softuni.Spring.ecommerce.app.service.OrderService;
import bg.softuni.Spring.ecommerce.app.service.exception.ObjectNotFoundException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final String adminName;
    private final String adminEmail;
    private final String adminPassword;
    private final UserRepository userRepository;
    private final OrderService orderService;

    public UserServiceImpl(UserRepository userRepository,
                           @Value("${admin.name}") String adminName,
                           @Value("${admin.email}") String adminEmail,
                           @Value("${admin.password}") String adminPassword,
                           OrderService orderService) {
        this.userRepository = userRepository;
        this.adminName = adminName;
        this.adminEmail = adminEmail;
        this.adminPassword = adminPassword;
        this.orderService = orderService;
    }

    @Override
    public Long createUser(SignupRequestDto signupRequest) {

        isUserExist(signupRequest);

        UserEntity user = new UserEntity()
                .setEmail(signupRequest.getEmail())
                .setName(signupRequest.getName())
                .setPassword(Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8().encode(signupRequest.getPassword()))
                .setRole(UserRoleEnum.CUSTOMER);

        UserEntity savedUser = userRepository.save(user);

        orderService.createEmptyOrder(user);

        return savedUser.getId();
    }

    @Override
    public UserEntity getUserById(Long userId) {

        return userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("User not exist"));
    }

    @Override
    public UserEntity findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ObjectNotFoundException("User not exist"));
    }


    @PostConstruct
    private void createAdminAccount() {

        if (userRepository.findUserEntityByRole(UserRoleEnum.ADMIN).isEmpty()) {
            UserEntity adminUser = new UserEntity()
                    .setEmail(adminEmail)
                    .setName(adminName)
                    .setRole(UserRoleEnum.ADMIN)
                    .setPassword(Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8().encode(adminPassword));
            userRepository.save(adminUser);
        }
    }

    private void isUserExist(SignupRequestDto signupRequest) {
        Optional<UserEntity> optionalUserEntity = userRepository
                .findByEmail(signupRequest.getEmail());

        if (optionalUserEntity.isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }
    }


}
