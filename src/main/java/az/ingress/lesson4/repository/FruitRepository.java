package az.ingress.lesson4.repository;

import az.ingress.lesson4.domain.FruitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FruitRepository extends JpaRepository<FruitEntity, Long> {
}
