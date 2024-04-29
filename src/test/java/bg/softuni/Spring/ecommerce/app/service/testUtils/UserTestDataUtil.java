package bg.softuni.Spring.ecommerce.app.service.testUtils;

import bg.softuni.Spring.ecommerce.app.model.entity.UserEntity;
import bg.softuni.Spring.ecommerce.app.model.enums.UserRoleEnum;
import bg.softuni.Spring.ecommerce.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Array;
import java.util.HexFormat;

@Component
public class UserTestDataUtil {

    @Autowired
    private UserRepository userRepository;


    public UserEntity createTestUser (String email) {
        UserEntity user = createUser()
                .setRole(UserRoleEnum.CUSTOMER);

    }

    public UserEntity createTestAdmin(String email) {

    }

    private UserEntity createUser() {

        byte[] img = HexFormat.of().parseHex("e04f");

        return new UserEntity()
                .setName("User")
                .setEmail("user@email.com")
                .setPassword("testPass")
                .setImg(img);
    }
}
