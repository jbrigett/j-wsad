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
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Centroid {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private TerminatedSession session;
	@Convert(converter = DoubleListToStringConverter.class)
	private List<Double> updateRate = new ArrayList<>();
	@Convert(converter = DoubleListToStringConverter.class)
	private List<Double> standardDeviation = new ArrayList<>();
	@Convert(converter = DoubleListToStringConverter.class)
	private List<Double> distanceFunctionalRelationship = new ArrayList<>();
	@Convert(converter = DoubleListToStringConverter.class)
	private List<Double> averageDistanceP = new ArrayList<>();
	//0
	private double taw;
	private double sumOfFeatures = 0;
	ArrayList<Integer> pointsIDS;
}
