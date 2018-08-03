package qa.allot.stabilityVerification.core.csvParsers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import qa.allot.stabilityVerification.core.trafficStreams.TrafficStream;


public class ParseTrafficCsvToTrafficStreams {
	
	/**
	 * Method parse "files/TrafficStreams.csv" into List of TrafficStreams Objects
	 * 
	 * 
	 * @return
	 */
	public List<TrafficStream> readCsv() {
		String csvFile = "files/TrafficStreams.csv";
		String line = "";
        String cvsSplitBy = ",";
        List<TrafficStream> streams = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] csvStrem = line.split(cvsSplitBy);

                TrafficStream stream = new TrafficStream();
                stream.setName(csvStrem[0]);
                stream.setInternalStream(Integer.parseInt(csvStrem[3]) > 0 ? true : false);
                stream.setFrameLength(Integer.parseInt(csvStrem[6]));
                stream.setModifierCount(Integer.parseInt(csvStrem[9]));
                stream.setTimeOut(Integer.parseInt(csvStrem[45]));
                streams.add(stream);
                
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return streams;
	}	

}
