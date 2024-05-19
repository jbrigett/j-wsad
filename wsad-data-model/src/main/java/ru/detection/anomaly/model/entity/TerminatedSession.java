package ru.detection.anomaly.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.detection.anomaly.model.entity.converter.DoubleListToStringConverter;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TerminatedSession {
    /**session's id*/
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    /**all key features of session*/
    @Convert(converter = DoubleListToStringConverter.class)
    private List<Double> features;
    /**is already observed*/
    private boolean isVisited;
    /**is abnormal session*/
    private boolean isNoise;
    /**is boarder session*/
    private boolean isBoarder;
    /**cluster of session*/
    private String originalCluster;
    /**new cluster of session*/
    private String assignedCluster;
    private int indexInPartition;
    private int pointCausedToBeCore;
    /**sessions in epsilon*/
    private ArrayList<TerminatedSession> sessionsAtEps;
    private int assignedCentroidId;


    public double calculateDistance(TerminatedSession other) {
        double[] otherFeatures = other.getFeatures();
        double distance = 0.0;
        for (int i = 0; i < features.length; i++) {
            distance += Math.pow(features[i] - otherFeatures[i], 2);
        }
        return Math.sqrt(distance);
    }

    public List<Integer> getNeighbors(List<TerminatedSessionDto> sessions, double epsilon) {
        List<Integer> neighbors = new ArrayList<>();
        for (TerminatedSessionDto s : sessions) {
            if (this.calculateDistance(s) <= epsilon) {
                neighbors.add(s.getId());
            }
        }
        return neighbors;
    }


    public boolean isCore(int minPts){
        if(this.pointsAtEpsIndexs.size()>= minPts) return true;
        return false;
    }

}
