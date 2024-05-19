package ru.detection.anomaly.service.dbscan.service;

import datasets.DatasetPattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import measures.EuclideanDistance;
import org.springframework.stereotype.Service;
import ru.detection.anomaly.model.entity.Centroid;
import ru.detection.anomaly.model.entity.TerminatedSession;

import java.util.ArrayList;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataPartitioningService {
 	private ArrayList<TerminatedSession> sessions;
	private Centroid[] centroids;

	public DataPartitioningService(ArrayList<TerminatedSession> list, int k) {
		this.sessions = list;
		this.centroids = new Centroid[k];
		for (int i = 0; i < k; i++) {
			int randomIndex = 0 + (int)(Math.random() * (((200-1) - 0) + 1));
			TerminatedSession p = this.sessions.get(randomIndex);
			this.centroids[i] = new Centroid(i, p);
		}
	}
	
	/**
	 * return the centroids
	 * @return centroids
	 */
	public Centroid[] getCentroids() {
		return centroids;
	}
	
	
	/**
	 * Insert point into partitions
	 * @param point data point
	 * @return the partition id
	 */
	public int partitionPoint(TerminatedSession point){
		double distance = Double.MAX_VALUE;
		Centroid cen = null;
		for (int j = 0; j < this.centroids.length; j++) {
			//double d = calculateDistanceBtwTwoPoints(this.centroids[j].getX(), this.centroids[j].getY(), point);
			double d = EuclideanDistance.calculateDistance(centroids[j].getPattern(), point);
			if(d<distance){
				cen = this.centroids[j];
				distance = d;
			}
		}
		point.setAssignedCentroidID(cen.getID());
		cen.updateCentroid(point);
		return cen.getID();
	}
	
	/**
	 * run the partitioning algorithm to al data
	 */
	public void run(){
		for (int i = 0; i < this.sessions.size(); i++) {
			TerminatedSession point = this.sessions.get(i);
			partitionPoint(point);
		}
	}
}
