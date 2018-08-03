package qa.allot.stabilityVerification.core.policy;

import java.util.List;

import qa.allot.stabilityVerification.core.trafficStreams.QosTable;
import qa.allot.stabilityVerification.core.trafficStreams.TrafficStream;

public class PolicyInstanceCalculation {
	private List<PolicyInstance> policyInstances;

	public PolicyInstanceCalculation(List<PolicyInstance> policyInstances) {
		super();
		this.policyInstances = policyInstances;
	}

	public void poliClasification() {
		for (PolicyInstance policyInstance : policyInstances) {
			//VCs classification to Pipes
			if (policyInstance instanceof VcInstance) {
				VcInstance vcInstance = (VcInstance) policyInstance;
				for (PolicyInstance policyInstance2 : policyInstances) {
					if (policyInstance2 instanceof PipeInstance
							&& policyInstance.getLineName().equals(policyInstance2.getLineName())
							&& policyInstance.getPipeName().equals(policyInstance2.getPipeName())) {
						PipeInstance pipeInstance = (PipeInstance) policyInstance2;
						pipeInstance.adToVcList(vcInstance);
						break;
					}
				}
			} else if (policyInstance instanceof PipeInstance) {
				PipeInstance pipeInstance = (PipeInstance) policyInstance;
				for (PolicyInstance policyInstance2 : policyInstances) {
					if (policyInstance2 instanceof LineInstance
							&& policyInstance2.getLineName().equals(policyInstance.getLineName())) {
						LineInstance lineInstance = (LineInstance) policyInstance2;
						lineInstance.addToPipeInstances(pipeInstance);
						break;
					}
				}

			} 
		}
	}

	public void CalculatePolicyValues(List<TrafficStream> trafficStreams , QosTable qosTable) {
		for (PolicyInstance policyInstance : policyInstances) {
			if (policyInstance instanceof VcInstance) {
				VcInstance vcInstance = (VcInstance) policyInstance;
				vcInstance.bwCalculation(trafficStreams ,qosTable);
				vcInstance.cerClculation(trafficStreams);
				vcInstance.liveConnectionCalculation(trafficStreams);
				vcInstance.dropConnectionCalculation(trafficStreams);
			}
		}
		for (PolicyInstance policyInstance : policyInstances) {
			if (policyInstance instanceof PipeInstance) {
				PipeInstance pipeInstance = (PipeInstance) policyInstance;
				pipeInstance.pipeCalculation();
			}
		}
		for (PolicyInstance policyInstance : policyInstances) {
			if (policyInstance instanceof LineInstance) {
				LineInstance lineInstance = (LineInstance) policyInstance;
				lineInstance.lineCalculation();
			}
		}
	}
}
