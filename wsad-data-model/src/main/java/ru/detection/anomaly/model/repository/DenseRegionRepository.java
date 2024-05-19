package ru.detection.anomaly.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.detection.anomaly.model.entity.DenseRegion;

public interface DenseRegionRepository extends JpaRepository<DenseRegion, Long> {
}
