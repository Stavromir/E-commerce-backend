package bg.softuni.Spring.ecommerce.app.repository;

import bg.softuni.Spring.ecommerce.app.model.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findAllByNameContaining(String name);
}
