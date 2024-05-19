package ru.detection.anomaly.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Entity
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Cluster {
    /**id of cluster*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**is cluster still active*/
    private boolean isActive;
    /**included density regions*/
    private List<DenseRegion> denseRegions;
    /**included sessions*/
    private List<TerminatedSession> sessions;
    /**all observed sessions: both included and not*/
    private Map<TerminatedSession, Boolean> sessionsSeen;

    public void addSession(TerminatedSession session) {
        if(!this.sessionsSeen.containsKey(session)){
            this.sessions.add(session);
            this.sessionsSeen.put(session, true);
        }
    }

    public void addDenseRegion(DenseRegion region){
        this.denseRegions.add(region);
    }

    public void addSessionsList(List<TerminatedSession> sessions){
        this.sessions.addAll(sessions);
    }
}
