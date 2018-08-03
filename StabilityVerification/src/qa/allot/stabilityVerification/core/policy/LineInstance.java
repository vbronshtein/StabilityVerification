package qa.allot.stabilityVerification.core.policy;

import java.util.ArrayList;
import java.util.List;

public class LineInstance extends PolicyInstance {
	private List<PipeInstance> pipeInstances = new ArrayList<>();

	public List<PipeInstance> getPipeInstances() {
		return pipeInstances;
	}

	public void addToPipeInstances(PipeInstance pipeInstance) {
		this.pipeInstances.add(pipeInstance);
	}

	public void lineCalculation() {
		if (this.getLineName().equals("TIME")) {
			this.setBw((int) (pipeInstances.get(0).getBw()));
			this.setCer(pipeInstances.get(0).getCer());
			this.setLiveConnections(pipeInstances.get(0).getLiveConnections());
			this.setDropConnections(pipeInstances.get(0).getDropConnections());
		} else {
			for (PipeInstance pipeInstance : pipeInstances) {
				this.setBw((int) (getBw() + pipeInstance.getBw()));
				this.setCer(getCer() + pipeInstance.getCer());
				this.setLiveConnections(getLiveConnections() + pipeInstance.getLiveConnections());
				this.setDropConnections(getDropConnections() + pipeInstance.getDropConnections());
			}
		}
	}

}
