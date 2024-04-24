package bg.softuni.Spring.ecommerce.app.service.jwt;

import bg.softuni.Spring.ecommerce.app.model.entity.UserEntity;
import bg.softuni.Spring.ecommerce.app.model.enums.UserRoleEnum;
import bg.softuni.Spring.ecommerce.app.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    private UserDetailsServiceImpl serviceToTest;

    @Mock
    private UserRepository userRepository;


    @BeforeEach
    void setUp() {
        this.serviceToTest = new UserDetailsServiceImpl(userRepository);
    }

    @Test
    void testUserNotFound() {

        assertThrows(
                UsernameNotFoundException.class,
                () -> serviceToTest.loadUserByUsername("pesho@abv.bg")
        );
    }

    @Test
    void testUserFound() {

        //Arrange
        UserEntity testUserEntity = createUserEntity();

        Mockito.when(userRepository.findByEmail("ivan@abv.bg"))
                .thenReturn(Optional.of(testUserEntity));

        //Act
        UserDetails userDetails = serviceToTest
                .loadUserByUsername(testUserEntity.getEmail());

        //Assert
        assertNotNull(userDetails);

        assertEquals(testUserEntity.getEmail(), userDetails.getUsername(),
                "Email is not mapped to username");

        assertEquals(testUserEntity.getPassword(), userDetails.getPassword(),
                "Password is not mapped correctly");

    }

    private static UserEntity createUserEntity() {

        return new UserEntity()
                .setEmail("ivan@abv.bg")
                .setPassword("testPassword")
                .setName("Ivan Ivanov")
                .setRole(UserRoleEnum.ADMIN);
    }


}