package enigma.model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class Rotor {

	private BiMap<String, String> rotor1 = HashBiMap.create();

	private String csvFile;
	private String line = "";
	private String cvsSplitBy = "\t";
	private Queue<String> rotor1QueKeys = new LinkedList<String>();
	private Queue<String> rotor1QueValues = new LinkedList<String>();
	private List<String> temp = new ArrayList<String>();
	private String initialCode;
	private int rotorNumber;
	private int characterNumber;

	/**
	 * Setting up the character mixing device. This class requires Google Guava
	 * BiMap and HashBiMap.
	 *
	 * @param csvFile
	 *            A text file containing 8 columns representing 4 rotors of Enigma
	 *            machine. 3 of those rotors are dynamic, while the last one is
	 *            static and mirrored Usually in first, third, fifth, seventh column
	 *            there are continous numbers ascenting from smallest to biggest. In
	 *            second, fourth, sixth column, there are random mixed number that
	 *            mix the given character. The eight column is mirrored, therefore
	 *            if A in column seven equals to B in column eighth, B in column
	 *            seven also equals to A in column eight.
	 * 
	 * @param initialCode
	 *            A string of 4 characters. They are the initial rotors 1-4 position
	 * @param rotorNumber
	 *            A number of rotor that is being used in given time
	 * @param characterNumber
	 *            A number of character in given text
	 */

	public Rotor(String csvFile, String initialCode, int rotorNumber, int characterNumber) {

		this.csvFile = csvFile;
		this.initialCode = initialCode;
		this.rotorNumber = rotorNumber;
		this.characterNumber = characterNumber;

		// changing rotor number to correct collumn in csvFile

		if (rotorNumber == 1) {
			rotorNumber = 0;
		} else if (rotorNumber == 2) {
			rotorNumber = 2;
		} else if (rotorNumber == 3) {
			rotorNumber = 4;
		} else if (rotorNumber == 4) {
			rotorNumber = 6;
		}

		// reading csvFile and putting values into temporary queues
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(csvFile)))) {

			while ((line = br.readLine()) != null) {
				// use comma as separator or tab if differnt file used

				String[] rotor = line.split(cvsSplitBy);

				// putting keys of given rotor into queue 1
				rotor1QueKeys.add(rotor[rotorNumber]);
				// putting values of given rotor into queue 2
				rotor1QueValues.add(rotor[rotorNumber + 1]);
				// temporary list to know how much to rotate
				temp.add(rotor[rotorNumber + 1]);

			}

			// rotating the rotor 1

			if (rotorNumber == 0) {
				characterNumber = characterNumber % 95; // rotating only by the reminder of rotor lenght devided by
														// number of characters

				for (int x = 0; x < temp.indexOf(initialCode) + characterNumber; x++) { // rotating

					rotor1QueValues.add(rotor1QueValues.poll());

				}
				// rotating the rotor 2
			} else if (rotorNumber == 2) {
				// this one rotates by one after first one makes a full cycle
				characterNumber = (int) characterNumber / 95;

				for (int x = 0; x < temp.indexOf(initialCode) + characterNumber; x++) { // rotating

					rotor1QueValues.add(rotor1QueValues.poll());

				}

			} else if (rotorNumber == 4) {
				// this one rotates by one after second one makes a full cycle
				characterNumber = (int) characterNumber / 9025;

				for (int x = 0; x < temp.indexOf(initialCode) + characterNumber; x++) { // rotating

					rotor1QueValues.add(rotor1QueValues.poll());

				}
			} else if (rotorNumber == 6) {
				// this one is static

				for (int x = 0; x < 95; x++) {
					rotor1QueValues.add(rotor1QueValues.poll());

				}

			}

			temp.clear(); // deleting temporary list

			for (int x = 0; x < 95; x++) { // creating proper, rotated rotor as a BiMap

				rotor1.put(rotor1QueKeys.poll(), rotor1QueValues.poll());

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Returns a value for a given key after proper rotor is constructed
	 * 
	 * @param x
	 *            key
	 * @return value for a given key, after proper rotor is constructed
	 */
	public String getCodeNormalRotor(String x) {
		return rotor1.get(x);
	}

	/**
	 * Returns a key for a given value after proper rotor is constructed
	 * 
	 * @param x
	 *            value
	 * @return key for a given value, after proper rotor is constructed
	 */
	public String getCodeReverseRotor(String x) {

		return rotor1.inverse().get(x);
	}

}
