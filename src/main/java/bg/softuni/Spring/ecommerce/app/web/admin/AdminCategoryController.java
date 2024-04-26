package bg.softuni.Spring.ecommerce.app.web.admin;

import bg.softuni.Spring.ecommerce.app.model.dto.CategoryDto;
import bg.softuni.Spring.ecommerce.app.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminCategoryController {


    private final CategoryService categoryService;

    public AdminCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @PostMapping("/categories")
    public ResponseEntity<Long> createCategory(
            @RequestBody CategoryDto categoryDto) {

        Long categoryId = categoryService.createCategory(categoryDto).getId();

        return ResponseEntity.status(HttpStatus.CREATED).body(categoryId);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDto>> getAllCategories () {

        List<CategoryDto> allCategories = categoryService.getAllCategories();
        return ResponseEntity.ok(allCategories);
    }
}
