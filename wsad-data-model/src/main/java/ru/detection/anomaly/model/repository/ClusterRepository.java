package ru.detection.anomaly.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.detection.anomaly.model.entity.Cluster;

public interface ClusterRepository extends JpaRepository<Cluster, Long> {
}
