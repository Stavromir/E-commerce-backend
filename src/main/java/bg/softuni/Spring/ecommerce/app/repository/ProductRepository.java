package bg.softuni.Spring.ecommerce.app.repository;

import bg.softuni.Spring.ecommerce.app.model.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}
