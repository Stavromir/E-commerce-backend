package bg.softuni.Spring.ecommerce.app.web;


import bg.softuni.Spring.ecommerce.app.model.entity.OrderEntity;
import bg.softuni.Spring.ecommerce.app.service.testUtils.OrderTestDataUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
class TrackingControllerTest {
    private static final String BASE_URL = "/orders/{trackingId}";
    private static final String ENCODING = "utf-8";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private OrderTestDataUtil orderTestDataUtil;

    @BeforeEach
    void setUp() {
        orderTestDataUtil.clearAllTestData();
    }

    @AfterEach
    void tearDown() {
        orderTestDataUtil.clearAllTestData();
    }

    @Test
    void testFindOrderByTrackingId() throws Exception {
        OrderEntity testOrder = orderTestDataUtil.createOrderWithTrackingNumber();
        UUID trackingId = testOrder.getTrackingId();

        mockMvc.perform(
                MockMvcRequestBuilders.get(BASE_URL, trackingId)
                        .characterEncoding(ENCODING)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isMap())
                .andExpect(MockMvcResultMatchers.jsonPath("$.cartItems").isArray());
    }
}