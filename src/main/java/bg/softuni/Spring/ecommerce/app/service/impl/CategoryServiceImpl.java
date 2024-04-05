package bg.softuni.Spring.ecommerce.app.service.impl;

import bg.softuni.Spring.ecommerce.app.model.dto.CategoryDto;
import bg.softuni.Spring.ecommerce.app.model.entity.CategoryEntity;
import bg.softuni.Spring.ecommerce.app.repository.CategoryRepository;
import bg.softuni.Spring.ecommerce.app.service.CategoryService;
import bg.softuni.Spring.ecommerce.app.service.exception.ObjectNotFoundException;
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
    public Long createCategory(CategoryDto categoryDto) {

        if (categoryRepository.existsByName(categoryDto.getName())) {
            throw new IllegalArgumentException("Category already exist");
        }

        CategoryEntity categoryEntity = new CategoryEntity()
                .setName(categoryDto.getName())
                .setDescription(categoryDto.getDescription());

        return categoryRepository.save(categoryEntity).getId();
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

    @Override
    public CategoryEntity findById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ObjectNotFoundException("Category not exist"));
    }

}
