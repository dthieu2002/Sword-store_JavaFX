package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
	@Override
	public void start(Stage arg0) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
		
		Scene scene = new Scene(root);
		// set style ( css ) 
		scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
		
		arg0.setScene(scene);
		arg0.setTitle("Login screen:");
		arg0.show();
	}
	public static void main(String[] args) {
		launch(args);
	}
}
