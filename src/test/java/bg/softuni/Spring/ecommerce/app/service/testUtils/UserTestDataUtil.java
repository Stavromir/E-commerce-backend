package bg.softuni.Spring.ecommerce.app.service.testUtils;

import bg.softuni.Spring.ecommerce.app.model.dto.SignupRequestDto;
import bg.softuni.Spring.ecommerce.app.model.dto.UserAuthenticationRequestDto;
import bg.softuni.Spring.ecommerce.app.model.entity.UserEntity;
import bg.softuni.Spring.ecommerce.app.model.enums.UserRoleEnum;
import bg.softuni.Spring.ecommerce.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HexFormat;

import static bg.softuni.Spring.ecommerce.app.service.testUtils.UserTestInfo.*;

@Component
public class UserTestDataUtil {

    @Value("${admin.email}")
    private String adminEmail;
    @Value("${admin.password}")
    private String adminPass;

    @Autowired
    private UserRepository userRepository;

    private UserEntity userEntityInstance;

    private UserTestDataUtil() {
    }

    public UserEntity getTestUserInstance() {
        if (userEntityInstance == null) {
            userEntityInstance = createUser(USER_EMAIL_1, UserRoleEnum.CUSTOMER);
        }
        return userEntityInstance;
    }

    public UserAuthenticationRequestDto getUserAuthRequestDto() {
        return new UserAuthenticationRequestDto()
                .setUsername(adminEmail)
                .setPassword(adminPass);
    }

    public SignupRequestDto createSignupRequestDto(String email) {
        return new SignupRequestDto()
                .setName(USERNAME)
                .setEmail(email)
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
}
