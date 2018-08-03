package qa.allot.stabilityVerification.core.policy;

import java.util.List;

import qa.allot.stabilityVerification.core.trafficStreams.TrafficStream;

public interface PolicyCalcInterface {
	void bwCalculation() ;
	void cerClculation(List<TrafficStream> trafficStreams);
	void liveConnectionCalculation(List<TrafficStream> trafficStreams);
	void dropConnectionCalculation(List<TrafficStream> trafficStreams);
}


