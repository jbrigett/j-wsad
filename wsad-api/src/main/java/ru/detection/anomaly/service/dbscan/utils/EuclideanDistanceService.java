package ru.detection.anomaly.service.dbscan.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.detection.anomaly.model.entity.TerminatedSession;

import java.util.ArrayList;

@Slf4j
@Service
@RequiredArgsConstructor
public class EuclideanDistanceService {

	
	public static double calculateDistance(TerminatedSession session1, TerminatedSession session2) {
		ArrayList<Double> vector1 = session1.getFeatures();
		ArrayList<Double> vector2 = session2.getFeatures();
		int length = vector1.size();
		double distance = 0;
		for (int i = 0; i < length; i++) {
			distance += Math.pow(vector1.get(i) - vector2.get(i), 2);
		}
//		System.out.println("distance = " + Math.sqrt(distance));
		return Math.sqrt(distance);
	}

}
