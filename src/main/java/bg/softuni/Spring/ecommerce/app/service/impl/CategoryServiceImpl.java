package bg.softuni.Spring.ecommerce.app.service.impl;

import bg.softuni.Spring.ecommerce.app.model.dto.CategoryDto;
import bg.softuni.Spring.ecommerce.app.model.entity.CategoryEntity;
import bg.softuni.Spring.ecommerce.app.repository.CategoryRepository;
import bg.softuni.Spring.ecommerce.app.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        CategoryEntity categoryEntity = new CategoryEntity()
                .setName(categoryDto.getName())
                .setDescription(categoryDto.getDescription());

        CategoryEntity category = categoryRepository.save(categoryEntity);

        return new CategoryDto()
                .setId(category.getId())
                .setName(category.getName())
                .setDescription(category.getDescription());
    }

    @Override
    public List<CategoryDto> getAllCategories() {

        return categoryRepository.findAll()
                .stream()
                .map(categoryEntity -> {
                    return new CategoryDto()
                            .setId(categoryEntity.getId())
                            .setName(categoryEntity.getName())
                            .setDescription(categoryEntity.getDescription());
                }).toList();
    }
}
