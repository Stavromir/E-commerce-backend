package bg.softuni.Spring.ecommerce.app.web.customer;

import bg.softuni.Spring.ecommerce.app.model.dto.AddProductInCartDto;
import bg.softuni.Spring.ecommerce.app.model.dto.PlaceOrderDto;
import bg.softuni.Spring.ecommerce.app.model.entity.CartItemEntity;
import bg.softuni.Spring.ecommerce.app.model.entity.CouponEntity;
import bg.softuni.Spring.ecommerce.app.model.entity.OrderEntity;
import bg.softuni.Spring.ecommerce.app.model.entity.ProductEntity;
import bg.softuni.Spring.ecommerce.app.service.testUtils.CouponTestDataUtil;
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
    @Autowired
    private CouponTestDataUtil couponTestDataUtil;

    @BeforeEach
    void setUp() {
        productTestDataUtil.clearAllTestData();
        orderTestDataUtil.clearAllTestData();
        couponTestDataUtil.clearAllTestData();
    }

    @AfterEach
    void tearDown() {
        productTestDataUtil.clearAllTestData();
        orderTestDataUtil.clearAllTestData();
        couponTestDataUtil.clearAllTestData();
    }

    @Test
    void testAddProductToCart() throws Exception {
        OrderEntity emptyOrder = orderTestDataUtil.createEmptyOrder();
        Long testUserId = emptyOrder.getUser().getId();

        ProductEntity testProduct = productTestDataUtil.createProduct();

        AddProductInCartDto addProductInCartDto =
                getAddProductInCartDto(testProduct.getId(), testUserId);

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

    @Test
    void testGetCartByUserId() throws Exception {
        OrderEntity testOrder = orderTestDataUtil.createFilledOrderInitialQuantity();
        Long userId = testOrder.getUser().getId();
        String jwtToken = getJwtToken();

        mockMvc.perform(
                MockMvcRequestBuilders.get(BASE_URL + "/carts/{userId}", userId)
                        .characterEncoding("utf-8")
                        .header(HttpHeaders.AUTHORIZATION, jwtToken)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.cartItems").isArray());
    }

    @Test
    void testApplyCoupon() throws Exception {
        CouponEntity testCoupon = couponTestDataUtil.createValidCouponEntity();
        String code = testCoupon.getCode();
        OrderEntity filledOrder = orderTestDataUtil.createFilledOrderInitialQuantity();
        Long userId = filledOrder.getUser().getId();

        String jwtToken = getJwtToken();

        mockMvc.perform(
                MockMvcRequestBuilders.get(BASE_URL + "/coupons/{userId}/{code}", userId, code)
                        .characterEncoding("utf-8")
                        .header(HttpHeaders.AUTHORIZATION, jwtToken)
        )
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.cartItems").isArray());
    }

    @Test
    void testIncreaseQuantity() throws Exception {
        OrderEntity testOrder = orderTestDataUtil.createFilledOrderInitialQuantity();

        Long testUserId = testOrder.getUser().getId();
        CartItemEntity testCartItem = testOrder.getCartItems().get(0);
        Long testProductId = testCartItem.getProduct().getId();

        AddProductInCartDto addProductInCartDto =
                getAddProductInCartDto(testProductId, testUserId);

        String content = gson.toJson(addProductInCartDto);
        String jwtToken = getJwtToken();

        mockMvc.perform(
                MockMvcRequestBuilders.post(BASE_URL + "/carts/addition")
                        .characterEncoding("urf-8")
                        .header(HttpHeaders.AUTHORIZATION, jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        )
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNumber());
    }

    @Test
    void testDecreaseQuantity() throws Exception {
        OrderEntity testOrder = orderTestDataUtil.createFilledOrderIncreasedQuantity();

        Long testUserId = testOrder.getUser().getId();
        CartItemEntity testCartItem = testOrder.getCartItems().get(0);
        Long testProductId = testCartItem.getProduct().getId();

        AddProductInCartDto addProductInCartDto =
                getAddProductInCartDto(testProductId, testUserId);

        String content = gson.toJson(addProductInCartDto);
        String jwtToken = getJwtToken();

        mockMvc.perform(
                        MockMvcRequestBuilders.post(BASE_URL + "/carts/subtraction")
                                .characterEncoding("urf-8")
                                .header(HttpHeaders.AUTHORIZATION, jwtToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                )
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNumber());
    }

    @Test
    void testPlaceOrder() throws Exception {
        OrderEntity emptyOrder = orderTestDataUtil.createEmptyOrder();
        PlaceOrderDto placeOrderDto = orderTestDataUtil
                .createPlaceOrderDto(emptyOrder.getUser().getId());

        String jwtToken = getJwtToken();
        String content = gson.toJson(placeOrderDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post(BASE_URL + "/orders")
                        .characterEncoding("utf-8")
                        .header(HttpHeaders.AUTHORIZATION, jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        )
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNumber());
    }

    private static AddProductInCartDto getAddProductInCartDto(Long testProductId, Long testUserId) {
        return new AddProductInCartDto()
                .setProductId(testProductId)
                .setUserId(testUserId);
    }

    private String getJwtToken() throws Exception {
        return jwtTestDataUtil.getJwtToken(mockMvc);
    }
}