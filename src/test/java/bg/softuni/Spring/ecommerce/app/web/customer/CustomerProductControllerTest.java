package bg.softuni.Spring.ecommerce.app.web.customer;

import bg.softuni.Spring.ecommerce.app.model.entity.FAQEntity;
import bg.softuni.Spring.ecommerce.app.model.entity.OrderEntity;
import bg.softuni.Spring.ecommerce.app.model.entity.ProductEntity;
import bg.softuni.Spring.ecommerce.app.model.entity.ReviewEntity;
import bg.softuni.Spring.ecommerce.app.service.testUtils.*;
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
class CustomerProductControllerTest {

    private static final String BASE_URL = "/api/customer";
    private static final String ENCODING = "utf-8";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JwtTestDataUtil jwtTestDataUtil;
    @Autowired
    private Gson gson;
    @Autowired
    private ProductTestDataUtil productTestDataUtil;
    @Autowired
    private OrderTestDataUtil orderTestDataUtil;
    @Autowired
    private FAQTestDataUtil faqTestDataUtil;
    @Autowired
    private ReviewTestDataUtil reviewTestDataUtil;

    @BeforeEach
    void setUp() {
        faqTestDataUtil.clearAllTestData();
        reviewTestDataUtil.clearAllTestData();
        productTestDataUtil.clearAllTestData();
        orderTestDataUtil.clearAllTestData();
    }

    @AfterEach
    void tearDown() {
        faqTestDataUtil.clearAllTestData();
        reviewTestDataUtil.clearAllTestData();
        productTestDataUtil.clearAllTestData();
        orderTestDataUtil.clearAllTestData();
    }

    @Test
    void testGetProducts() throws Exception {
        productTestDataUtil.createProduct();
        String jwtToken = getJwtToken();

        mockMvc.perform(
                MockMvcRequestBuilders.get(BASE_URL + "/products")
                        .characterEncoding(ENCODING)
                        .header(HttpHeaders.AUTHORIZATION, jwtToken)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }

    @Test
    void testSearchProductByTitle() throws Exception {
        ProductEntity testProduct = productTestDataUtil.createProduct();
        String name = testProduct.getName();
        String jwtToken = getJwtToken();

        mockMvc.perform(
                MockMvcRequestBuilders.get(BASE_URL + "/products/title/{name}", name)
                        .characterEncoding(ENCODING)
                        .header(HttpHeaders.AUTHORIZATION, jwtToken)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }

    @Test
    void testGetOrderedProductsDetails() throws Exception {
        OrderEntity testOrder = orderTestDataUtil.createFilledOrderInitialQuantity();
        Long orderId = testOrder.getId();
        String jwtToken = getJwtToken();

        mockMvc.perform(
                        MockMvcRequestBuilders.get(BASE_URL + "/products/order/{orderId}", orderId)
                                .characterEncoding(ENCODING)
                                .header(HttpHeaders.AUTHORIZATION, jwtToken)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.productDtos").isArray());
    }

    @Test
    void testGetProductDetailById() throws Exception {
        ProductEntity testProduct = productTestDataUtil.createProduct();
        Long productId = testProduct.getId();
        reviewTestDataUtil.createReviewEntity();
        faqTestDataUtil.createFaqEntity();

        String jwtToken = getJwtToken();

        mockMvc.perform(
                MockMvcRequestBuilders.get(BASE_URL + "/products/{productId}", productId)
                        .characterEncoding(ENCODING)
                        .header(HttpHeaders.AUTHORIZATION, jwtToken)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.productDto").isMap())
                .andExpect(MockMvcResultMatchers.jsonPath("$.reviewDtos").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.faqDtos").isArray());
    }

    private String getJwtToken() throws Exception {
        return jwtTestDataUtil.getJwtToken(mockMvc);
    }

}