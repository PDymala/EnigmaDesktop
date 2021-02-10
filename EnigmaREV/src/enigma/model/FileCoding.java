package enigma.model;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class FileCoding {

	private String inputFile;
	private String outputFile;

	private Queue<String> rotor1QueKeys = new LinkedList<String>();
	private Queue<String> rotor1QueValues = new LinkedList<String>();
	private List<String> temp = new ArrayList<String>();
	private String initialCode;
	private int rotorNumber;
	private int characterNumber = 0;
	// private String codedCharacter;
	AsciiGenerator ascii = new AsciiGenerator("src/prp/ascii95.tsv");
	String rotorInput = "src/prp/enigma95_rew.tsv";

	/**
	 * This class creates coded text based on given string.
	 *
	 * @param inputFile
	 *            url to text file that has to be coded
	 * @param outputFile
	 *            url to newly created file
	 * @param secretCode
	 *            String (text) secret code to code and decode given text. Has to
	 *            have 4 characters.
	 */

	public FileCoding(String inputFile, String outputFile, String secretCode) {

		this.inputFile = inputFile;
		this.outputFile = outputFile;
		FileReader inputStream = null;
		FileWriter outputStream = null;

		// reads character by character from file
		try {
			inputStream = new FileReader(inputFile);
			outputStream = new FileWriter(outputFile);

			int c = -1;
			int tempCharacterNumber = 0;
			while ((c = inputStream.read()) > -1) {

				char character = (char) c;

				if (c > 31 && c < 127) {

					// the function mixes given character 3 times on dynamic rotor, once on a mirror
					// rotor and 3 times on reversed dynamic rotor

					String tempCharacter = "";
					Rotor rotor1 = new Rotor(rotorInput, ascii.getAsciiCode(Character.toString(secretCode.charAt(0))),
							1, tempCharacterNumber);
					tempCharacter = ascii.getSymbolFromAscii(
							rotor1.getCodeNormalRotor(ascii.getAsciiCode(Character.toString(character))));

					Rotor rotor2 = new Rotor(rotorInput, ascii.getAsciiCode(Character.toString(secretCode.charAt(1))),
							2, tempCharacterNumber);
					String tempCharacter2 = ascii
							.getSymbolFromAscii(rotor2.getCodeNormalRotor(ascii.getAsciiCode(tempCharacter)));

					Rotor rotor3 = new Rotor(rotorInput, ascii.getAsciiCode(Character.toString(secretCode.charAt(2))),
							3, tempCharacterNumber);
					String tempCharacter3 = ascii
							.getSymbolFromAscii(rotor3.getCodeNormalRotor(ascii.getAsciiCode(tempCharacter2)));

					Rotor rotor4 = new Rotor(rotorInput, ascii.getAsciiCode(Character.toString(secretCode.charAt(3))),
							4, tempCharacterNumber);
					String tempCharacter4 = ascii
							.getSymbolFromAscii(rotor4.getCodeNormalRotor(ascii.getAsciiCode(tempCharacter3)));

					Rotor rotor5 = new Rotor(rotorInput, ascii.getAsciiCode(Character.toString(secretCode.charAt(2))),
							3, tempCharacterNumber);
					String tempCharacter5 = ascii
							.getSymbolFromAscii(rotor5.getCodeReverseRotor(ascii.getAsciiCode(tempCharacter4)));

					Rotor rotor6 = new Rotor(rotorInput, ascii.getAsciiCode(Character.toString(secretCode.charAt(1))),
							2, tempCharacterNumber);
					String tempCharacter6 = ascii
							.getSymbolFromAscii(rotor6.getCodeReverseRotor(ascii.getAsciiCode(tempCharacter5)));

					Rotor rotor7 = new Rotor(rotorInput, ascii.getAsciiCode(Character.toString(secretCode.charAt(0))),
							1, tempCharacterNumber);
					String tempCharacter7 = ascii
							.getSymbolFromAscii(rotor7.getCodeReverseRotor(ascii.getAsciiCode(tempCharacter6)));

					outputStream.write(tempCharacter7);
					tempCharacterNumber++;
				}
			}
			outputStream.close();
			inputStream.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
