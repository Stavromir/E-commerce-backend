package bg.softuni.Spring.ecommerce.app.repository;

import bg.softuni.Spring.ecommerce.app.model.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    List<ReviewEntity> getAllByProductId(Long productId);
}
