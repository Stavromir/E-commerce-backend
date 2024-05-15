package bg.softuni.Spring.ecommerce.app.web.admin;

import bg.softuni.Spring.ecommerce.app.model.entity.OrderEntity;
import bg.softuni.Spring.ecommerce.app.service.testUtils.JwtTestDataUtil;
import bg.softuni.Spring.ecommerce.app.service.testUtils.OrderTestDataUtil;
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
class AdminOrderControllerTest {

    @Autowired
    private OrderTestDataUtil orderTestDataUtil;
    @Autowired
    private JwtTestDataUtil jwtTestDataUtil;
    @Autowired
    private Gson gson;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        orderTestDataUtil.clearAllTestData();
    }

    @AfterEach
    void tearDown() {
        orderTestDataUtil.clearAllTestData();
    }

    @Test
    void testGetAllPlacedOrders() throws Exception {
        orderTestDataUtil.createFilledOrderInitialQuantity();

        String jwtToken = getJwtToken();

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/admin/orders")
                        .characterEncoding("utf-8")
                        .header(HttpHeaders.AUTHORIZATION, jwtToken)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }

    @Test
    void testChangeOrderStatus() throws Exception {
        String jwtToken = getJwtToken();
        OrderEntity testOrder = orderTestDataUtil.createOrderWithStatusPlaced();
        Long orderId = testOrder.getId();
        String newStatus = "Shipped";

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/admin/orders/{orderId}/{status}", orderId, newStatus)
                        .characterEncoding("utf-8")
                        .header(HttpHeaders.AUTHORIZATION, jwtToken)
        )
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNumber());
    }

    @Test
    void testGetAnalytics() throws Exception {
        orderTestDataUtil.createOrderWithStatusPlaced();
        String jwtToken = getJwtToken();

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/admin/analytics")
                        .characterEncoding("utf-8")
                        .header(HttpHeaders.AUTHORIZATION, jwtToken)
        )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    private String getJwtToken() throws Exception {
        return jwtTestDataUtil.getJwtToken(mockMvc);
    }
}