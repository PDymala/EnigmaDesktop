package enigma;

import enigma.view.EnigmaController;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {

		ViewLoader<AnchorPane, EnigmaController> viewLoader = new ViewLoader<AnchorPane, EnigmaController>(
				"view/EnigmaView.fxml");
		AnchorPane anchorPaneEmp = viewLoader.getLayout();

		Scene scene = new Scene(anchorPaneEmp);
		primaryStage.setScene(scene);
		primaryStage.setTitle("		ENIGMA		");

		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}