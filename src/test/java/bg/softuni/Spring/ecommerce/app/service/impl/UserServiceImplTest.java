package bg.softuni.Spring.ecommerce.app.service.impl;

import bg.softuni.Spring.ecommerce.app.model.dto.SignupRequestDto;
import bg.softuni.Spring.ecommerce.app.model.entity.UserEntity;
import bg.softuni.Spring.ecommerce.app.repository.UserRepository;
import bg.softuni.Spring.ecommerce.app.service.UserService;
import bg.softuni.Spring.ecommerce.app.service.testUtils.UserTestDataUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserTestDataUtil userTestDataUtil;

    @Test
    void testCreateUser() {
        SignupRequestDto testSignupRequestDto = userTestDataUtil
                .createSignupRequestDto();

        Long userId = userService.createUser(testSignupRequestDto);

        Optional<UserEntity> testUserEntity = userRepository.findById(userId);

        assertTrue(testUserEntity.isPresent());
        UserEntity userEntity = testUserEntity.get();
        assertEquals(testSignupRequestDto.getEmail(), userEntity.getEmail());
        assertEquals(testSignupRequestDto.getName(), userEntity.getName());
        assertTrue(Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8()
                .matches(testSignupRequestDto.getPassword(),userEntity.getPassword()));
    }
}