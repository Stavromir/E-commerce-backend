package bg.softuni.Spring.ecommerce.app.web;

import bg.softuni.Spring.ecommerce.app.model.dto.CategoryDto;
import bg.softuni.Spring.ecommerce.app.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class CategoryController {


    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @PostMapping("/category")
    public ResponseEntity<CategoryDto> createCategory(
            @RequestBody CategoryDto categoryDto) {

        CategoryDto category = categoryService.createCategory(categoryDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAllCategories () {

        List<CategoryDto> allCategories = categoryService.getAllCategories();
        return ResponseEntity.ok(allCategories);
    }
}
