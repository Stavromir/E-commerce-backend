package bg.softuni.Spring.ecommerce.app.service.impl;

import bg.softuni.Spring.ecommerce.app.model.dto.SignupRequestDto;
import bg.softuni.Spring.ecommerce.app.model.entity.UserEntity;
import bg.softuni.Spring.ecommerce.app.repository.UserRepository;
import bg.softuni.Spring.ecommerce.app.service.UserService;
import bg.softuni.Spring.ecommerce.app.service.exception.ObjectNotFoundException;
import bg.softuni.Spring.ecommerce.app.service.testUtils.UserTestDataUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import java.util.Optional;

import static bg.softuni.Spring.ecommerce.app.service.testUtils.UserTestInfo.*;
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
                .createSignupRequestDto(USER_EMAIL_2);

        Long userId = userService.createUser(testSignupRequestDto);

        Optional<UserEntity> testUserEntity = userRepository.findById(userId);

        assertTrue(testUserEntity.isPresent());
        UserEntity userEntity = testUserEntity.get();
        assertEquals(testSignupRequestDto.getEmail(), userEntity.getEmail());
        assertEquals(testSignupRequestDto.getName(), userEntity.getName());
        assertTrue(Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8()
                .matches(testSignupRequestDto.getPassword(),userEntity.getPassword()));
    }

    @Test
    void testCreateUserThrowExc() {
        SignupRequestDto testSignupRequestDto = userTestDataUtil
                .createSignupRequestDto(USER_EMAIL_1);

        userTestDataUtil.getTestUserInstance();

        assertThrows(
                IllegalArgumentException.class,
                () -> userService.createUser(testSignupRequestDto)
        );
    }

    @Test
    void testGetUserById() {
        UserEntity testUser = userTestDataUtil.getTestUserInstance();

        UserEntity returnedUserEntity = userService.getUserById(testUser.getId());

        assertNotNull(returnedUserEntity);
        assertEquals(testUser.getName(), returnedUserEntity.getName());
        assertEquals(testUser.getEmail(), returnedUserEntity.getEmail());
    }

    @Test
    void testGetUserByIdThrowExc() {
        assertThrows(
                ObjectNotFoundException.class,
                () -> userService.getUserById(101L)
        );
    }

    @Test
    void testFindUserByEmail() {
        UserEntity testUser = userTestDataUtil.getTestUserInstance();

        UserEntity returnedUserEntity = userService.findUserByEmail(testUser.getEmail());

        assertNotNull(returnedUserEntity);
        assertEquals(testUser.getName(), returnedUserEntity.getName());
        assertEquals(testUser.getId(), returnedUserEntity.getId());
    }

    @Test
    void testFindUserByEmailThrowExc() {
        assertThrows(
                ObjectNotFoundException.class,
                () -> userService.findUserByEmail("test@email.com")
        );
    }
}