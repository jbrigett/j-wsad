package ru.detection.anomaly.service.dbscan;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.detection.anomaly.model.entity.Session;
import ru.detection.anomaly.model.entity.TerminatedSession;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class IDBSCAN {
    @Value("${idbscan.settings.eps}")
    protected double eps;
    @Value("${idbscan.settings.min-points}")
    protected int minSessions;
    private int clustersCount;
    protected List<Session> abnormalSessions = new ArrayList<>();

    /**
     * method to start clustering
     * @param sessionDtos - all terminated sessions
     */
    public void startDBSCAN(List<TerminatedSession> sessionDtos) {
        for (TerminatedSession terminatedSession : sessionDtos) {
            clusterSession(terminatedSession);
        }
    }

    /**
     * GEt new terminated session and make one of 4 choices:
     * 1) Noise - The calculated UpdSeedD(p) is empty, so there are no new core points. As a result, just p is assigned as a noise point.
     * 2) Creation - In this case, UpdSeedD(p) contains only core objects not belonging to any cluster in the previous clustering,
     * i.e. classified as noise. As a result, a new cluster is created containing p and these seed objects.
     * 3) Absorption - UpdSeedD(p) contains core objects belonging to exactly one cluster C, in the previous clustering state.
     * The new point p and possibly noise points in the UpdSeedD(p), if exists, are absorbed into cluster C.
     * 4) Merge - If UpdSeedD(p) contains core objects belonging to more than one cluster before the update.
     * In this case, all the clusters are merged into one new cluster as well as the new point p.
     * @param sessionDto - session to cluster
     */
    private void clusterSession(TerminatedSession sessionDto){
        ArrayList<Integer> updSeedPointIndexs = getUpdSeedSet(sessionDto);
        if(updSeedPointIndexs.size()==0){
            markAsNoise(sessionDto);
        }
        else if(updSeedContainsCorePatternsWithNoCluster(updSeedPointIndexs)){
            createCluster(sessionDto, updSeedPointIndexs);
        }
        else if(updSeedContainsCorePatternsFromOneCluster(updSeedPointIndexs)){
            joinCluster(sessionDto, updSeedPointIndexs);
        }
        else{
            mergeClusters(sessionDto, updSeedPointIndexs);
        }
        sessionDto.isVisited(true);


    }

    /**
     * Get the updSeed set of a datapoint
     * @param point point
     * @return updSeed set
     */
    private List<TerminatedSession> getSeedsToUpdate(TerminatedSession session){
        var toUpdate = new ArrayList<TerminatedSession>();

        for (int i = 0; i < this.dataset.size(); i++) {
            DatasetPattern p = this.dataset.get(i);
            if(pattern.getID() == p.getID()) continue;
            if(!p.isVisited()) break;
            double distance = EuclideanDistance.calculateDistance(pattern, p);
            //	System.out.println("distance = " + distance);
            if(distance > this.eps) continue;
            pattern.addToNeighborhoodPoints(p.getID());
            p.addToNeighborhoodPoints(pattern.getID());
            if(p.getPointsAtEpsIndexs().size() == this.minPts){
                p.pointCausedToBeCore(pattern.getID());
                updSeedIndex.add(p.getID());
                continue;
            }
            if(p.isCore(this.minPts)) updSeedIndex.add(p.getID());
        }
        return updSeedIndex;
    }

}
