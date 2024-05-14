package bg.softuni.Spring.ecommerce.app.web.admin;

import bg.softuni.Spring.ecommerce.app.service.testUtils.JwtTestDataUtil;
import bg.softuni.Spring.ecommerce.app.service.testUtils.ProductTestDataUtil;
import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class AdminProductControllerTest {

    @Autowired
    private JwtTestDataUtil jwtTestDataUtil;
    @Autowired
    private Gson gson;
    @Autowired
    private ProductTestDataUtil productTestDataUtil;

    @BeforeEach
    void setUp() {
        productTestDataUtil.clearAllTestData();
    }

    @AfterEach
    void tearDown() {
        productTestDataUtil.clearAllTestData();
    }



}