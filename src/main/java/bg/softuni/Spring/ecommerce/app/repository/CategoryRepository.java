package bg.softuni.Spring.ecommerce.app.repository;

import bg.softuni.Spring.ecommerce.app.model.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    boolean existsByName(String name);
}
