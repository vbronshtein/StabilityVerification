package qa.allot.stabilityVerification.core.csvParsers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import qa.allot.stabilityVerification.core.policy.LineInstance;
import qa.allot.stabilityVerification.core.policy.PipeInstance;
import qa.allot.stabilityVerification.core.policy.PolicyInstance;
import qa.allot.stabilityVerification.core.policy.VcInstance;

public class ParseExpectedTrafficCsv {

	public List<PolicyInstance> readVcFromCsv() {
		String expectedCsvFile = "files/Expected.csv";
		String line = "";
		String cvsSplitBy = ",";
		List<PolicyInstance> policyInstances = new ArrayList<>();
		boolean isFirstLine = true;

		try (BufferedReader br = new BufferedReader(new FileReader(expectedCsvFile))) {

			while ((line = br.readLine()) != null) {
				// use comma as separator
				String[] csvStrem = line.split(cvsSplitBy);

				if (isFirstLine) {
					isFirstLine = false;
					continue;
				}
				if (!csvStrem[2].equals("")) {

					VcInstance vcInstance = new VcInstance();
					vcInstance.setVcName(csvStrem[2]);
					vcInstance.setPipeName(csvStrem[1]);
					vcInstance.setLineName(csvStrem[0]);

					int firstTrafficStreamIndex = 6;
					int lastTrafficStreamIndex = 25;
					int currIndex = firstTrafficStreamIndex;
					List<String> trafficStreams = new ArrayList<>();
					while (currIndex < lastTrafficStreamIndex && !csvStrem[currIndex].equals("")) {
						trafficStreams.add(csvStrem[currIndex]);
						currIndex++;

					}
					vcInstance.setTrafficStreamNames(trafficStreams);

					vcInstance.setQos(Integer.parseInt(csvStrem[3]) > 0 ? true : false);
					vcInstance.setDos(Integer.parseInt(csvStrem[4]) > 0 ? true : false);
					vcInstance.setJumbo(Integer.parseInt(csvStrem[5]) > 0 ? true : false);
					vcInstance.setJumbo(Integer.parseInt(csvStrem[5]) > 0 ? true : false);

					policyInstances.add(vcInstance);
				} else if (!csvStrem[1].equals("")) {
					PipeInstance pipeInstance = new PipeInstance();
					pipeInstance.setPipeName(csvStrem[1]);
					pipeInstance.setLineName(csvStrem[0]);

					policyInstances.add(pipeInstance);
				} else if (!csvStrem[0].equals("")) {
					LineInstance lineInstance = new LineInstance();
					lineInstance.setLineName(csvStrem[0]);

					policyInstances.add(lineInstance);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return policyInstances;
	}

}
