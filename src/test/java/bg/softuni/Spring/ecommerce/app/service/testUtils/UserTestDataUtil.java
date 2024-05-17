package bg.softuni.Spring.ecommerce.app.service.testUtils;

import bg.softuni.Spring.ecommerce.app.model.dto.SignupRequestDto;
import bg.softuni.Spring.ecommerce.app.model.dto.UserAuthenticationRequestDto;
import bg.softuni.Spring.ecommerce.app.model.entity.UserEntity;
import bg.softuni.Spring.ecommerce.app.model.enums.UserRoleEnum;
import bg.softuni.Spring.ecommerce.app.repository.OrderRepository;
import bg.softuni.Spring.ecommerce.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HexFormat;

@Component
public class UserTestDataUtil {

    @Value("${admin.email}")
    private String adminEmail;
    @Value("${admin.password}")
    private String adminPass;


    private static final String IMG_HEX = "e04f";
    private static final String USERNAME = "testUser";
    private static final String TEST_USER_EMAIL = "test@email.com";
    private static final String NEW_TEST_USER_EMAIL = "newTest@email.com";
    private static final String USER_PASSWORD = "userTestPass";

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;

    private UserEntity userEntity;

    private UserTestDataUtil() {
    }

    public UserEntity createTestUser() {
        if (userEntity == null) {
            userEntity = createUser(TEST_USER_EMAIL, UserRoleEnum.CUSTOMER);
        }
        return userEntity;
    }

    public UserAuthenticationRequestDto getUserAuthRequestDto() {
        return new UserAuthenticationRequestDto()
                .setUsername(adminEmail)
                .setPassword(adminPass);
    }

    public SignupRequestDto createSignupRequestDto() {
        return new SignupRequestDto()
                .setName(USERNAME)
                .setEmail(NEW_TEST_USER_EMAIL)
                .setPassword(USER_PASSWORD);
    }

    private UserEntity createUser(String email, UserRoleEnum userRoleEnum) {

        byte[] img = HexFormat.of().parseHex(IMG_HEX);

        UserEntity user = new UserEntity()
                .setName(USERNAME)
                .setEmail(email)
                .setPassword(USER_PASSWORD)
                .setRole(userRoleEnum)
                .setImg(img);

        return userRepository.save(user);
    }

    public void clearAllTestData() {
        userEntity = null;
        orderRepository.deleteAll();
        userRepository.deleteAll();
    }
}
