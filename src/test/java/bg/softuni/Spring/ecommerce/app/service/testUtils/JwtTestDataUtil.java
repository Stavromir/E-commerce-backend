package bg.softuni.Spring.ecommerce.app.service.testUtils;

import bg.softuni.Spring.ecommerce.app.model.dto.UserAuthenticationRequestDto;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@Component
public class JwtTestDataUtil {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private Gson gson;
    @Autowired
    private UserTestDataUtil userTestDataUtil;

    private String jwtToken;

    private JwtTestDataUtil() {
    }

    public String getJwtToken() throws Exception {
        if (jwtToken == null) {
            UserAuthenticationRequestDto userAuthRequestDto =
                    userTestDataUtil.getUserAuthRequestDto();
            String content = gson.toJson(userAuthRequestDto);

            MockHttpServletResponse response = mockMvc.perform(
                            MockMvcRequestBuilders.post("/authenticate")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .characterEncoding("utf-8")
                                    .content(content)
                    ).andReturn()
                    .getResponse();

            jwtToken = response.getHeader("Authorization");

        }
        return jwtToken;
    }
}
