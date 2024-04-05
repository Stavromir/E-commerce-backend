package bg.softuni.Spring.ecommerce.app.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class ProductEntity extends BaseEntity{

    private String name;
    private Long price;
    private String description;
    private byte[] img;
    private CategoryEntity category;


    public ProductEntity() {
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public ProductEntity setName(String name) {
        this.name = name;
        return this;
    }

    @Column(nullable = false)
    public Long getPrice() {
        return price;
    }

    public ProductEntity setPrice(Long price) {
        this.price = price;
        return this;
    }

    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    public String getDescription() {
        return description;
    }

    public ProductEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    @Lob
    @Column(columnDefinition = "BLOB", nullable = false)
    public byte[] getImg() {
        return img;
    }

    public ProductEntity setImg(byte[] img) {
        this.img = img;
        return this;
    }

    @ManyToOne(optional = false)
    public CategoryEntity getCategory() {
        return category;
    }

    public ProductEntity setCategory(CategoryEntity category) {
        this.category = category;
        return this;
    }
}
