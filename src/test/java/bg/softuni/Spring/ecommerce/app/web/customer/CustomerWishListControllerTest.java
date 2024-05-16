package bg.softuni.Spring.ecommerce.app.web.customer;

import bg.softuni.Spring.ecommerce.app.model.dto.WishListDto;
import bg.softuni.Spring.ecommerce.app.model.entity.WishListEntity;
import bg.softuni.Spring.ecommerce.app.service.testUtils.JwtTestDataUtil;
import bg.softuni.Spring.ecommerce.app.service.testUtils.WishListTestDataUtil;
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
class CustomerWishListControllerTest {

    private static final String BASE_URL = "/api/customer";
    private static final String ENCODING = "utf-8";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JwtTestDataUtil jwtTestDataUtil;
    @Autowired
    private WishListTestDataUtil wishListTestDataUtil;
    @Autowired
    private Gson gson;

    @BeforeEach
    void setUp() {
        wishListTestDataUtil.clearAllTestData();
    }

    @AfterEach
    void tearDown() {
        wishListTestDataUtil.clearAllTestData();
    }

    @Test
    void testAddProductToWishList() throws Exception {
        WishListDto wishListDto = wishListTestDataUtil.createWishListDto();
        String jwtToken = getJwtToken();
        String content = gson.toJson(wishListDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post(BASE_URL + "/wishlists")
                        .characterEncoding(ENCODING)
                        .header(HttpHeaders.AUTHORIZATION, jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNumber());
    }

    @Test
    void testGetAllProductsInWishList() throws Exception {
        WishListEntity testWishList = wishListTestDataUtil.createWishListEntity();
        Long userId = testWishList.getUser().getId();

        String jwtToken = getJwtToken();

        mockMvc.perform(
                MockMvcRequestBuilders.get(BASE_URL + "/wishlists/{userId}", userId)
                        .characterEncoding(ENCODING)
                        .header(HttpHeaders.AUTHORIZATION, jwtToken)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }

    private String getJwtToken() throws Exception {
        return jwtTestDataUtil.getJwtToken(mockMvc);
    }

}