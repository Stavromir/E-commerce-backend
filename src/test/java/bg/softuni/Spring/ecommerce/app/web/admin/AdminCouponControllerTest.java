package bg.softuni.Spring.ecommerce.app.web.admin;

import bg.softuni.Spring.ecommerce.app.model.dto.CouponDto;
import bg.softuni.Spring.ecommerce.app.service.testUtils.CouponTestDataUtil;
import bg.softuni.Spring.ecommerce.app.service.testUtils.JwtTestDataUtil;
import com.google.gson.Gson;
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

    @Autowired
    private CouponTestDataUtil couponTestDataUtil;
    @Autowired
    private JwtTestDataUtil jwtTestDataUtil;
    @Autowired
    private Gson gson;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testCreateCoupon() throws Exception {

        String jwtToken = jwtTestDataUtil.getJwtToken(mockMvc);
        CouponDto couponDto = couponTestDataUtil.createCouponDto();

        String content = gson.toJson(couponDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/admin/coupons")
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
    void testGetAllCoupons() {

    }
}