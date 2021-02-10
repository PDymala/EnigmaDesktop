package enigma.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class AsciiGenerator {

	private String csvFile;
	private BiMap<String, String> asciiMap = HashBiMap.create();
	private String line = "";
	private String cvsSplitBy = "\t";

	public AsciiGenerator(String csvFile) {
		this.csvFile = csvFile;

		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

			while ((line = br.readLine()) != null) {
				// use comma as separator
				String[] rotor = line.split(cvsSplitBy); // tymczasowa lista sczytanych elementow listy

				if (rotor.length < 2) {
				} else {
					asciiMap.put(rotor[1], rotor[0]);
				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		}


	}

	public String getAsciiCode(String value) {

		return asciiMap.get(value);
	}

	public String getSymbolFromAscii(String value) {
		return asciiMap.inverse().get(value);
	}
	
}
