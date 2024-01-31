package az.ingress.lesson4.repository;

import az.ingress.lesson4.domain.FruitEntity;
import az.ingress.lesson4.domain.State;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FruitRepository extends JpaRepository<FruitEntity, Long> {
    Slice<FruitEntity> findAllByStatus(State status, Pageable pageable);
}
