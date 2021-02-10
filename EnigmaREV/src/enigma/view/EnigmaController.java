package enigma.view;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import enigma.model.FileCoding;
import enigma.model.RandomSecretCode;
import enigma.model.TextCoding;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

public class EnigmaController {

	@FXML
	RadioButton methodText;
	@FXML
	RadioButton methodFile;
	@FXML
	Button loadFileToCode;
	@FXML
	Button loadFinishedFile;
	@FXML
	Label fileReady;
	@FXML
	TextArea textWritten;
	@FXML
	TextArea textReady;
	@FXML
	Button exit;
	@FXML
	TextField secretCode;
	@FXML
	Button randomCodeButton;
	@FXML
	Button about;

	String fileToCode;
	String codedFile;

	@FXML
	public void aboutWindow() {
		Optional<ButtonType> result = AlertBox.showAndWait(AlertType.INFORMATION, "About the app",
				"The given application was made by Piotr Dymala (p.dymala@gmail.com) on the basis of given instruction on JAVA EE studies on Technical University of Warsaw (post-graduate studies). Originally, as a part of one of the exams, it had readed secret code from a file and didn't have so called mirror rotor, i.e. it had a separate option to code and encode");
	}

	@FXML
	public void generateRandomSecretCode() {

		RandomSecretCode ran = new RandomSecretCode(4);
		secretCode.setText(ran.getSecretCode());

	}

	@FXML
	public void methodText(ActionEvent e) {

		textWritten.setDisable(false);
		textReady.setDisable(false);
		loadFileToCode.setDisable(true);
		fileReady.setDisable(true);
	}

	@FXML
	public void methodFile(ActionEvent e) {

		textWritten.setDisable(true);
		textReady.setDisable(true);
		loadFileToCode.setDisable(false);
		fileReady.setDisable(false);
	}

	@FXML
	public void loadFiletoCode(ActionEvent e) {
		if (secretCode.getText() != null) {

			FileChooser fileChooser = new FileChooser();
			File selFile = fileChooser.showOpenDialog(null);

			try {
				if (selFile != null) {
					fileToCode = selFile.getCanonicalPath();
					fileReady.setText("File loaded");
				} else {
				}
			} catch (IOException ex) {

				Optional<ButtonType> result = AlertBox.showAndWait(AlertType.WARNING, "Error", "Catalog choice error");

			}

		} else {
			Optional<ButtonType> result = AlertBox.showAndWait(AlertType.WARNING, "Error", "No secret code given");
		}
	}

	@FXML
	public void runCoding(ActionEvent e) {
		if (secretCode.getText().length() < 1) {
			Optional<ButtonType> result = AlertBox.showAndWait(AlertType.WARNING, "Error", "No secret code given");

		} else if (secretCode.getText().length() > 4) {
			Optional<ButtonType> result = AlertBox.showAndWait(AlertType.WARNING, "Error",
					"Secret code has to many character. Has to have 4");
		}

		else {

			if (methodText.isSelected() == true) {

				if (textWritten.getText().length() < 1) {
					Optional<ButtonType> result = AlertBox.showAndWait(AlertType.WARNING, "Error",
							"No text given to code");
				} else {

					TextCoding textCoding = new TextCoding(textWritten.getText(), secretCode.getText());

					textReady.setText(textCoding.getOutputText());
				}
			}

			else if (methodFile.isSelected() == true) {

				if (fileToCode != null) {
					codedFile = fileToCode + "_coded.txt";

					FileCoding fileCoding = new FileCoding(fileToCode, codedFile, secretCode.getText());
					textReady.setText("File ready");

					Optional<ButtonType> result = AlertBox.showAndWait(AlertType.INFORMATION, "Ready", "File ready");

				} else {

					Optional<ButtonType> result = AlertBox.showAndWait(AlertType.WARNING, "Error",
							"No secret code given");
				}

			}

			else {

				Optional<ButtonType> result = AlertBox.showAndWait(AlertType.WARNING, "Error", "No secret code given");

			}
		}
	}

	@FXML
	public void exit(ActionEvent ae) {

		Optional<ButtonType> result = AlertBox.showAndWait(AlertType.CONFIRMATION, "Quiting",
				"Do You want to quit the application?");
		if (result.orElse(ButtonType.CANCEL) == ButtonType.OK) {

			Platform.exit();
		}
	}

}
