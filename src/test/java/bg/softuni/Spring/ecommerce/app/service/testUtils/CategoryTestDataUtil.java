package bg.softuni.Spring.ecommerce.app.service.testUtils;

import bg.softuni.Spring.ecommerce.app.model.entity.CategoryEntity;
import bg.softuni.Spring.ecommerce.app.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryTestDataUtil {

    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryEntity createCategory () {
        CategoryEntity category = new CategoryEntity()
                .setName("category")
                .setDescription("categoryDescription");

        return categoryRepository.save(category);
    }
}
