package bg.softuni.Spring.ecommerce.app.service;

import bg.softuni.Spring.ecommerce.app.model.dto.CategoryDto;
import bg.softuni.Spring.ecommerce.app.model.entity.CategoryEntity;

import java.util.List;

public interface CategoryService {

    CategoryEntity createCategory(CategoryDto categoryDto);

    List<CategoryDto> getAllCategories();

    CategoryEntity findById(Long categoryId);

    CategoryDto getCategoryDto(CategoryEntity categoryEntity);
}
