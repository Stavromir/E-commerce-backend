package bg.softuni.Spring.ecommerce.app.web.admin;

import bg.softuni.Spring.ecommerce.app.service.testUtils.CouponTestDataUtil;
import bg.softuni.Spring.ecommerce.app.service.testUtils.JwtTestDataUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class AdminCouponControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CouponTestDataUtil couponTestDataUtil;
    @Autowired
    private JwtTestDataUtil jwtTestDataUtil;


    @Test
    void testCreateCoupon() {

    }

    @Test
    void testGetAllCoupons() {

    }
}