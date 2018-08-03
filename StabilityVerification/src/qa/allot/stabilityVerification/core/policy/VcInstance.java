package qa.allot.stabilityVerification.core.policy;

import java.util.List;

import qa.allot.stabilityVerification.core.trafficStreams.GlobalVariables;
import qa.allot.stabilityVerification.core.trafficStreams.QosTable;
import qa.allot.stabilityVerification.core.trafficStreams.TrafficStream;

public class VcInstance extends PolicyInstance {

	public void bwCalculation(List<TrafficStream> trafficStreams, QosTable qosTable) {
		if (this.getLineName().equals("Dos") || this.getLineName().equals("Bypass")
				|| this.getLineName().equals("Drop")) {
			this.setBw(0);
		} else if (this.getLineName().equals("QoS") && this.getVcName().contains("MinVc-pri")) {
			// need redefine QosInstance as Singleton and calculate all values
			switch (this.getVcName()) {
			case "MinVc-pri1":
				this.setBw(qosTable.getVcQosMbPri1());
				break;
			case "MinVc-pri2":
				this.setBw(qosTable.getVcQosMbPri2());
				break;
			case "MinVc-pri3":
				this.setBw(qosTable.getVcQosMbPri3());
				break;
			case "MinVc-pri4":
				this.setBw(qosTable.getVcQosMbPri4());
				break;

			default:
				break;
			}

		} else {

			for (TrafficStream trafficStream : trafficStreams) {
				for (String streamName : getTrafficStreamNames()) {
					if (streamName.equals(trafficStream.getName())) {
						this.setBw((int) (getBw() + trafficStream.getBw()));
					}
				}
			}
		}
	}

	public void cerClculation(List<TrafficStream> trafficStreams) {
		if (this.getLineName().equals("Bypass")) {
			this.setCer(0);
		} else {
			for (TrafficStream trafficStream : trafficStreams) {
				for (String streamName : getTrafficStreamNames()) {
					if (streamName.equals(trafficStream.getName()) && trafficStream.isInternalStream()) {
						this.setCer(this.getCer() + trafficStream.getCer());
					}

				}
			}
		}

	}

	public void liveConnectionCalculation(List<TrafficStream> trafficStreams) {
		if (this.getLineName().equals("Bypass")) {
			this.setLiveConnections(0);
		} else {
			for (TrafficStream trafficStream : trafficStreams) {
				for (String streamName : getTrafficStreamNames()) {
					if (streamName.equals(trafficStream.getName()) && trafficStream.isInternalStream()) {
						setLiveConnections(getLiveConnections() + trafficStream.getNoc());
					}

				}
			}
		}

	}

	public void dropConnectionCalculation(List<TrafficStream> trafficStreams) {
		int monitorResolution = 30;
		if (isDos()) {
			setDropConnections(getCer() * monitorResolution);
		}

	}

}
