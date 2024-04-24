package bg.softuni.Spring.ecommerce.app.service.jwt;

import bg.softuni.Spring.ecommerce.app.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

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



}