package ru.detection.anomaly.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.detection.anomaly.model.entity.Centroid;

public interface CentroidRepository extends JpaRepository<Centroid, Long> {
}
