package bg.softuni.Spring.ecommerce.app.service.testUtils;

import bg.softuni.Spring.ecommerce.app.model.entity.CategoryEntity;
import bg.softuni.Spring.ecommerce.app.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryTestDataUtil {

    public static final String CATEGORY_NAME = "testCategory";
    public static final String CATEGORY_DESCRIPTION = "testDescription";

    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryEntity createCategory () {
        CategoryEntity category = new CategoryEntity()
                .setName(CATEGORY_NAME)
                .setDescription(CATEGORY_DESCRIPTION);

        return categoryRepository.save(category);
    }

    public void cleanAllTestData() {
        categoryRepository.deleteAll();
    }
}
