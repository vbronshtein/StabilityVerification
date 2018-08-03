package test;

import java.util.List;

import qa.allot.stabilityVerification.core.csvParsers.GenerateFinalCsv;
import qa.allot.stabilityVerification.core.csvParsers.ParseExpectedTrafficCsv;
import qa.allot.stabilityVerification.core.csvParsers.ParseTrafficCsvToTrafficStreams;
import qa.allot.stabilityVerification.core.policy.PolicyInstance;
import qa.allot.stabilityVerification.core.policy.PolicyInstanceCalculation;
import qa.allot.stabilityVerification.core.trafficStreams.GlobalVariables;
import qa.allot.stabilityVerification.core.trafficStreams.QosTable;
import qa.allot.stabilityVerification.core.trafficStreams.TrafficStream;
import qa.allot.stabilityVerification.core.trafficStreams.TrafficStreamCalculation;

public class Test {
	public static void main(String[] args) {

		 int numOfLinks = 5;
		 int portSpeed = 10_000;
		 int portUtilizationPercent = 24;
		 boolean jamboEnable = true;
//		int numOfLinks = Integer.parseInt(args[0]);
//
//		int portSpeed = Integer.parseInt(args[1]);
//		int portUtilizationPercent = Integer.parseInt(args[2]);
		/**
		 * Traffic streams parsing and calculation
		 */

		System.out.println("---------start---------");
		// Parsing traffic streams from csv
		ParseTrafficCsvToTrafficStreams parse = new ParseTrafficCsvToTrafficStreams(); // need to be a singleton
		List<TrafficStream> streams = parse.readCsv();

		System.out.println("---------Generate global Variables---------");
		// Calculation of Global variables
		GlobalVariables globalVariables = new GlobalVariables(numOfLinks, portSpeed, portUtilizationPercent, streams);
		System.out.println("FpsPerStream : " + globalVariables.getFpsPerStream());

		// Calculation of BW/CER/NOC per traffic stream
		System.out.println("---------Start calculation---------");
		TrafficStreamCalculation trafficStreamCalculation = new TrafficStreamCalculation();
		trafficStreamCalculation.calculateBwNocCer(streams, numOfLinks, globalVariables.getFpsPerStream());

		/**
		 * Calculate Expected valuef for Policy instances
		 */

		// Parse Policy inctances into PolicyInstance object
		ParseExpectedTrafficCsv parseEcpected = new ParseExpectedTrafficCsv();
		List<PolicyInstance> policyInstances = parseEcpected.readVcFromCsv();

		// Classification of VCs instances to Pipes / Pips to Lines
		PolicyInstanceCalculation policyInstanceCalculation = new PolicyInstanceCalculation(policyInstances);
		policyInstanceCalculation.poliClasification();

		// Calculate QoS table
		QosTable qosTable = new QosTable();
		qosTable.stcQosCalculation(streams);
		
		policyInstanceCalculation.CalculatePolicyValues(streams, qosTable);
		qosTable.print();

		//Additional bussiness logic ( remove TIME pipes/vs )
		
		// Generate Final Csv
		System.out.println("---------Generate Final csv---------");
		GenerateFinalCsv finalCsv = new GenerateFinalCsv();
		finalCsv.createResultCsv(policyInstances);
		System.out.println("File generated on : " + finalCsv.getCsvoutFile());
		System.out.println("---------done---------");
	}
}
