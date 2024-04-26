package bg.softuni.Spring.ecommerce.app.service.impl;

import bg.softuni.Spring.ecommerce.app.model.dto.CategoryDto;
import bg.softuni.Spring.ecommerce.app.model.entity.CategoryEntity;
import bg.softuni.Spring.ecommerce.app.repository.CategoryRepository;
import bg.softuni.Spring.ecommerce.app.service.CategoryService;
import bg.softuni.Spring.ecommerce.app.service.exception.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    public static final Long CATEGORY_ONE_ID = 1L;
    public static final String CATEGORY_ONE_NAME = "categoryOne";
    public static final String CATEGORY_ONE_DESCRIPTION = "descriptionOne";

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

        when(categoryRepository.existsByName(CATEGORY_ONE_NAME))
                .thenReturn(true);

        assertThrows(
                IllegalArgumentException.class,
                () -> categoryServiceToTest.createCategory(categoryDto)
        );
        verify(categoryRepository, times(1)).existsByName(CATEGORY_ONE_NAME);
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

    @Test
    void testGetCategoryDro() {
        CategoryEntity categoryEntity = createCategoryEntity();

        CategoryDto categoryDto = categoryServiceToTest.getCategoryDto(categoryEntity);

        assertNotNull(categoryDto);
        assertEquals(categoryEntity.getName(), categoryDto.getName());
        assertEquals(categoryEntity.getDescription(), categoryDto.getDescription());
        assertEquals(categoryEntity.getId(), categoryDto.getId());
    }

    @Test
    void testFindById () {
        CategoryEntity testCategoryEntity = createCategoryEntity();

        when(categoryRepository.findById(anyLong()))
                .thenReturn(Optional.of(testCategoryEntity));

        CategoryEntity foundCategory = categoryServiceToTest.findById(anyLong());

        assertNotNull(foundCategory);
        assertEquals(testCategoryEntity.getDescription(), foundCategory.getDescription());
        assertEquals(testCategoryEntity.getName(), foundCategory.getName());
    }

    @Test
    void testNotFoundById() {

        assertThrows(
                ObjectNotFoundException.class,
                () -> categoryServiceToTest.findById(CATEGORY_ONE_ID)
        );
    }


    private CategoryDto createCategoryDto () {
        return new CategoryDto()
                .setId(CATEGORY_ONE_ID)
                .setName(CATEGORY_ONE_NAME)
                .setDescription(CATEGORY_ONE_DESCRIPTION);
    }

    private CategoryEntity createCategoryEntity() {
        CategoryEntity category = new CategoryEntity()
                .setName(CATEGORY_ONE_NAME)
                .setDescription(CATEGORY_ONE_DESCRIPTION);
        category.setId(CATEGORY_ONE_ID);
        return category;
    }

}