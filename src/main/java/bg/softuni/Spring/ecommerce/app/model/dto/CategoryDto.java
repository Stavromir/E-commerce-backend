package bg.softuni.Spring.ecommerce.app.model.dto;

import jakarta.validation.constraints.Size;

public class CategoryDto {

    private Long id;
    private String name;
    private String description;



    public Long getId() {
        return id;
    }

    public CategoryDto setId(Long id) {
        this.id = id;
        return this;
    }

    @Size(min = 3, max = 56)
    public String getName() {
        return name;
    }

    public CategoryDto setName(String name) {
        this.name = name;
        return this;
    }

    @Size(min = 3, max = 234)
    public String getDescription() {
        return description;
    }

    public CategoryDto setDescription(String description) {
        this.description = description;
        return this;
    }
}
