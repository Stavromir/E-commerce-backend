package bg.softuni.Spring.ecommerce.app.web.admin;

import bg.softuni.Spring.ecommerce.app.model.dto.ProductDto;
import bg.softuni.Spring.ecommerce.app.model.entity.ProductEntity;
import bg.softuni.Spring.ecommerce.app.service.testUtils.JwtTestDataUtil;
import bg.softuni.Spring.ecommerce.app.service.testUtils.ProductTestDataUtil;
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
class AdminProductControllerTest {

    private static final String BASE_URL = "/api/admin";

    @Autowired
    private JwtTestDataUtil jwtTestDataUtil;
    @Autowired
    private Gson gson;
    @Autowired
    private ProductTestDataUtil productTestDataUtil;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        productTestDataUtil.clearAllTestData();
    }

    @AfterEach
    void tearDown() {
        productTestDataUtil.clearAllTestData();
    }

    @Test
    void testAddProduct() throws Exception {
        String jwtToken = getJwtToken();
        ProductDto productSeedDto = productTestDataUtil.createProductDto();

        mockMvc.perform(
                MockMvcRequestBuilders.post(BASE_URL + "/products")
                        .characterEncoding("utf-8")
                        .header(HttpHeaders.AUTHORIZATION, jwtToken)
                        .param("name", productSeedDto.getName())
                        .param("description", productSeedDto.getDescription())
                        .param("price", productSeedDto.getPrice().toString())
                        .param("categoryId", productSeedDto.getCategoryId().toString())

        )
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNumber());
    }

    @Test
    void testGetProducts() throws Exception {
        String jwtToken = getJwtToken();
        productTestDataUtil.createProduct();

        mockMvc.perform(
                MockMvcRequestBuilders.get(BASE_URL + "/products")
                        .characterEncoding("utf-8")
                        .header(HttpHeaders.AUTHORIZATION, jwtToken)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }

    @Test
    void testSearchProductByTitle() throws Exception {
        String jwtToken = getJwtToken();
        ProductEntity testProductEntity = productTestDataUtil.createProduct();
        String name = testProductEntity.getName();

        mockMvc.perform(
                MockMvcRequestBuilders.get(BASE_URL + "/products/title/{name}", name)
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