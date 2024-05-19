package ru.detection.anomaly.model.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.detection.anomaly.model.entity.Centroid;
import ru.detection.anomaly.model.entity.TerminatedSession;
import ru.detection.anomaly.model.repository.CentroidRepository;

import java.util.ArrayList;

@Slf4j
@Service
@RequiredArgsConstructor
public class CentroidService {
    @Value("${idbscan.centroids.a-metric}")
    private double aMetric;
    @Value("${idbscan.centroids.b-metric}")
    private double bMetric;
    @Value("${idbscan.centroids.c-metric}")
    private double cMetric;
    @Value("${idbscan.centroids.k-metric}")
    private double kMetric;

    private final CentroidRepository repository;

    public void updateCentroid(Centroid centroid, TerminatedSession newSession){
        this.pointsIDS.add(newSession.getId());
        double lambda = 0.5*(1- (calculateDotProduct(this.session.getFeatures(), newSession.getFeatures())/
                calculateScalarMeasureOfCosine(this.session.getFeatures(), newSession.getFeatures())));


        this.taw = Math.exp(-this.aMetric*(1-lambda));
        this.taw = this.k*this.taw + (1-this.k) * this.taw;

        double pe = calculatePE();
        for (int i = 0; i < this.session.getFeatures().length; i++) {
            double feature = this.session.getFeatures().get(i);
            double modifiedFeature = feature - (feature - p.getFeatureVector().get(i)) * this.taw * this.averageDistanceP[i] * pe;
            this.session.getFeatures().set(i, modifiedFeature);
        }
        double [] diffs = new double[this.session.getFeatureVector().size()];
        for (int i = 0; i < diffs.length; i++) {
            diffs[i] = Math.abs(p.getFeatureVector().get(i)-this.session.getFeatureVector().get(i));
        }
        calculateUpdatingRate(diffs);
        calculateStandardDeviation(diffs);
        calculateDistanceFunctionalRelationship();
        calculateAverageDistanceP();
    }

    /**calculate a dot product */
    private double calculateDotProduct(ArrayList<Double> list1, ArrayList<Double> list2){
        double result=0;
        for (int i = 0; i < list1.size(); i++) {
            result+= list1.get(i)*list2.get(i);
        }
        return result;
    }

    private double calculateScalarMeasureOfCosine(ArrayList<Double> list1, ArrayList<Double> list2){
        double result = 0;
        for (int i = 0; i < list1.size(); i++) {
            result+= Math.pow(list1.get(i) - list2.get(i), 2);
        }
        return Math.sqrt(result);
    }

    public double calculateDistance(TerminatedSession p){
        return EuclideanDistance.calculateDistance(this.session, p);
    }


    /**calculate distance between given session and centroid, alpha is less than 1*/
    private void calculateUpdatingRate(double [] distance){
        for (int i = 0; i < distance.length; i++) {
            this.updateRate[i] = (this.aMetric*this.updateRate[i]) + ((1-this.aMetric)*distance[i]);
        }
    }

    /**calculate distance between given session and centroid, betha is less than 1*/
    private void calculateStandardDeviation(double [] distance){
        for (int i = 0; i < distance.length; i++) {
            this.standardDeviation[i] = (this.bMetric*this.standardDeviation[i]) + ((1-this.bMetric) * Math.abs(this.updateRate[i]- distance[i]) );
        }
    }

    /**functional relationship between updatingRate and standardDeviation*/
    private void calculateDistanceFunctionalRelationship(){
        for (int i = 0; i < this.distanceFunctionalRelationship.length; i++) {
            double e =  Math.exp(-0.5*(this.standardDeviation[i]/(1+this.updateRate[i])));
            this.distanceFunctionalRelationship[i] = (2/(1+ e ))-1;
        }
    }

    /**calculate average functional relationship between updatingRate and standardDeviation*/
    private void calculateAverageDistanceP(){
        for (int i = 0; i < this.averageDistanceP.length; i++) {
            this.averageDistanceP[i] = cMetric *this.averageDistanceP[i] + (1- cMetric)*this.distanceFunctionalRelationship[i];
        }
    }

    public double calculatePE(){
        double pe = 0;
        for (int i = 0; i < this.session.getFeatures().length(); i++) {
            double norm = normalizeFeature(this.session.getFeatures().get(i), this.sumOfFeatures);
            pe += norm * Math.log10(norm);
        }
        return (1- (pe/Math.log10(0.5)));
    }

    private double normalizeFeature(double feature, double sum){
        return feature/sum;
    }
}
