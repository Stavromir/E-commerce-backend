package bg.softuni.Spring.ecommerce.app.web.customer;

import bg.softuni.Spring.ecommerce.app.model.dto.ReviewDto;
import bg.softuni.Spring.ecommerce.app.service.testUtils.JwtTestDataUtil;
import bg.softuni.Spring.ecommerce.app.service.testUtils.ReviewTestDataUtil;
import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerReviewControllerTest {

    private static final String BASE_URL = "/api/customer";
    private static final String ENCODING = "utf-8";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JwtTestDataUtil jwtTestDataUtil;
    @Autowired
    private ReviewTestDataUtil reviewTestDataUtil;
    @Autowired
    private Gson gson;

    @BeforeEach
    void setUp() {
        reviewTestDataUtil.clearAllTestData();
    }

    @AfterEach
    void tearDown() {
        reviewTestDataUtil.clearAllTestData();
    }

    @Test
    void testPostReview() throws Exception {
        ReviewDto testReviewDto = reviewTestDataUtil.createReviewDto();
        String jwtToken = getJwtToken();

        mockMvc.perform(
                MockMvcRequestBuilders.post(BASE_URL + "/reviews")
                        .characterEncoding(ENCODING)
                        .header(HttpHeaders.AUTHORIZATION, jwtToken)
                        .param("productId", testReviewDto.getProductId().toString())
                        .param("userId", testReviewDto.getUserId().toString())
                        .param("description", testReviewDto.getDescription())
                        .param("rating", testReviewDto.getRating().toString())
        )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNumber());
    }

    private String getJwtToken() throws Exception {
        return jwtTestDataUtil.getJwtToken(mockMvc);
    }

}