package bg.softuni.Spring.ecommerce.app.web;

import bg.softuni.Spring.ecommerce.app.model.dto.SignupRequestDto;
import bg.softuni.Spring.ecommerce.app.service.testUtils.UserTestDataUtil;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static bg.softuni.Spring.ecommerce.app.service.testUtils.UserTestInfo.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserAuthControllerTest {

    private static final String BASE_URL = "/sign-up";
    private static final String ENCODING = "utf-8";

    @Autowired
    private UserTestDataUtil userTestDataUtil;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private Gson gson;

    @Test
    void testSingUpUser() throws Exception {
        SignupRequestDto testSignUpRequestDto =
                userTestDataUtil.createSignupRequestDto(USER_EMAIL_3);

        String content = gson.toJson(testSignUpRequestDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post(BASE_URL)
                        .characterEncoding(ENCODING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNumber());
    }
}