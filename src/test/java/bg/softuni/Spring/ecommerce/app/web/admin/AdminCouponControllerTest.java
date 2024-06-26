package bg.softuni.Spring.ecommerce.app.web.admin;

import bg.softuni.Spring.ecommerce.app.model.dto.CouponDto;
import bg.softuni.Spring.ecommerce.app.service.testUtils.CouponTestDataUtil;
import bg.softuni.Spring.ecommerce.app.service.testUtils.JwtTestDataUtil;
import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc
class AdminCouponControllerTest {

    private static final String BASE_URL = "/api/admin";

    @Autowired
    private CouponTestDataUtil couponTestDataUtil;
    @Autowired
    private JwtTestDataUtil jwtTestDataUtil;
    @Autowired
    private Gson gson;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        couponTestDataUtil.clearAllTestData();
    }

    @AfterEach
    void tearDown() {
        couponTestDataUtil.clearAllTestData();
    }

    @Test
    void testCreateCoupon() throws Exception {

        String jwtToken = getJwtToken();
        CouponDto couponDto = couponTestDataUtil.createCouponDto();

        String content = gson.toJson(couponDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post(BASE_URL + "/coupons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .header(HttpHeaders.AUTHORIZATION, jwtToken)
                        .content(content)
        )
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNumber());
    }

    @Test
    void testGetAllCoupons() throws Exception {
        String jwtToken = getJwtToken();

        couponTestDataUtil.createValidCouponEntity();

        mockMvc.perform(
                MockMvcRequestBuilders.get(BASE_URL + "/coupons")
                        .characterEncoding("utf-8")
                        .header(HttpHeaders.AUTHORIZATION, jwtToken)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());

    }

    private String getJwtToken() throws Exception {
        return jwtTestDataUtil.getJwtToken(mockMvc);
    }
}