package bg.softuni.Spring.ecommerce.app.service.impl;

import bg.softuni.Spring.ecommerce.app.model.dto.CategoryDto;
import bg.softuni.Spring.ecommerce.app.model.entity.CategoryEntity;
import bg.softuni.Spring.ecommerce.app.repository.CategoryRepository;
import bg.softuni.Spring.ecommerce.app.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    public static final String CATEGORY_NAME = "testCategory";
    public static final String CATEGORY_DESCRIPTION = "testDESCRIPTION";

    private CategoryService categoryServiceToTest;

    @Mock
    private CategoryRepository categoryRepository;

    @BeforeEach
    void SetUp() {
        this.categoryServiceToTest = new CategoryServiceImpl(categoryRepository);
    }

    @Test
    void testCreateExistingCategory() {
        CategoryDto categoryDto = createCategoryDto();

        when(categoryRepository.existsByName(CATEGORY_NAME))
                .thenReturn(true);

        assertThrows(
                IllegalArgumentException.class,
                () -> categoryServiceToTest.createCategory(categoryDto)
        );
        verify(categoryRepository, times(1)).existsByName(CATEGORY_NAME);
    }

    @Test
    void testCreateCategory() {
        CategoryDto passedCategoryDto = createCategoryDto();
        CategoryEntity categoryEntity = createCategoryEntity();

        when(categoryRepository.save(any(CategoryEntity.class)))
                .thenReturn(categoryEntity);

        CategoryEntity createdCategory = categoryServiceToTest
                .createCategory(passedCategoryDto);

        assertNotNull(createdCategory);
        assertEquals(passedCategoryDto.getName(), createdCategory.getName());
        assertEquals(passedCategoryDto.getDescription(), createdCategory.getDescription());
        verify(categoryRepository, times(1)).save(any(CategoryEntity.class));
    }


    private CategoryDto createCategoryDto () {
        return new CategoryDto()
                .setName(CATEGORY_NAME)
                .setDescription(CATEGORY_DESCRIPTION);
    }

    private CategoryEntity createCategoryEntity() {
        return new CategoryEntity()
                .setName(CATEGORY_NAME)
                .setDescription(CATEGORY_DESCRIPTION);
    }

}