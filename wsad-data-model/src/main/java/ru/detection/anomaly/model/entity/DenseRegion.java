package ru.detection.anomaly.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class DenseRegion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long clusterId;
	private List<TerminatedSession> sessions = new ArrayList<>();
	private List<TerminatedSession> boarderSessions = new ArrayList<>();
	private boolean isClustered;
	private boolean active;

	public void setClusterId(Long clusterId) {
		this.clusterId = clusterId;
		this.isClustered = true;
	}
	public void addSession(TerminatedSession sessionDto){
		this.sessions.add(sessionDto);
	}
	
	public void addBoarderPoint(TerminatedSession pointIndex){
		this.boarderSessions.add(pointIndex);
		this.sessions.add(pointIndex);
	}
}
