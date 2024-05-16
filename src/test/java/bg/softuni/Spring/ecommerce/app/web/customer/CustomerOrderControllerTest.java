package bg.softuni.Spring.ecommerce.app.web.customer;

import bg.softuni.Spring.ecommerce.app.model.dto.AddProductInCartDto;
import bg.softuni.Spring.ecommerce.app.model.entity.OrderEntity;
import bg.softuni.Spring.ecommerce.app.model.entity.ProductEntity;
import bg.softuni.Spring.ecommerce.app.service.testUtils.JwtTestDataUtil;
import bg.softuni.Spring.ecommerce.app.service.testUtils.OrderTestDataUtil;
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
class CustomerOrderControllerTest {

    private static final String BASE_URL = "/api/customer";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JwtTestDataUtil jwtTestDataUtil;
    @Autowired
    private Gson gson;
    @Autowired
    private OrderTestDataUtil orderTestDataUtil;
    @Autowired
    private ProductTestDataUtil productTestDataUtil;

    @BeforeEach
    void setUp() {
        orderTestDataUtil.clearAllTestData();
    }

    @AfterEach
    void tearDown() {
        orderTestDataUtil.clearAllTestData();
    }

    @Test
    void testAddProductToCart() throws Exception {
        OrderEntity emptyOrder = orderTestDataUtil.createEmptyOrder();
        Long testUserId = emptyOrder.getUser().getId();

        ProductEntity testProduct = productTestDataUtil.createProduct();

        AddProductInCartDto addProductInCartDto = new AddProductInCartDto()
                .setUserId(testUserId)
                .setProductId(testProduct.getId());

        String jwtToken = getJwtToken();
        String content = gson.toJson(addProductInCartDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post(BASE_URL + "/carts")
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, jwtToken)
                        .content(content)
        )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNumber());
    }

    private String getJwtToken() throws Exception {
        return jwtTestDataUtil.getJwtToken(mockMvc);
    }



}