package bg.softuni.Spring.ecommerce.app.repository;

import bg.softuni.Spring.ecommerce.app.model.entity.CouponEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<CouponEntity, Long> {

    boolean existsByCode(String code);

    Optional<CouponEntity> findByCode(String code);
}
