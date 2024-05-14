package bg.softuni.Spring.ecommerce.app.web.admin;

import bg.softuni.Spring.ecommerce.app.model.dto.CategoryDto;
import bg.softuni.Spring.ecommerce.app.service.testUtils.CategoryTestDataUtil;
import bg.softuni.Spring.ecommerce.app.service.testUtils.JwtTestDataUtil;
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
class AdminCategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CategoryTestDataUtil categoryTestDataUtil;
    @Autowired
    private Gson gson;
    @Autowired
    private JwtTestDataUtil jwtTestDataUtil;

    @BeforeEach
    void setUp() {
        categoryTestDataUtil.cleanAllTestData();
    }

    @AfterEach
    void tearDown() {
        categoryTestDataUtil.cleanAllTestData();
    }

    @Test
    void testCreateCategory() throws Exception {

        String jwtToken = jwtTestDataUtil.getJwtToken(mockMvc);

        CategoryDto categorySeedDto = categoryTestDataUtil.getCategorySeedDto();
        String content = gson.toJson(categorySeedDto);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/admin/categories")
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("utf-8")
                                .header(HttpHeaders.AUTHORIZATION, jwtToken)
                                .content(content)
                ).andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNumber());
    }

    @Test
    void testGetAllCategories() throws Exception {
        String jwtToken = jwtTestDataUtil.getJwtToken(mockMvc);
        categoryTestDataUtil.createCategory();

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/admin/categories")
                                .characterEncoding("utf-8")
                                .header(HttpHeaders.AUTHORIZATION, jwtToken)
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }
}