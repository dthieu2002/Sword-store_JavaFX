package gui;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MessageScreenClass {
	// type : 0 is message, 1 is error message
	public void openMessageScreen(int type,String message) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MessageScreen.fxml"));
		Parent root = fxmlLoader.load();
			
		MessageScreenController messageScreenController = fxmlLoader.getController();
		messageScreenController.setType(type);
		messageScreenController.setMessage(message);
	
		Stage messageStage = new Stage();
		messageStage.setScene(new Scene(root));
		if(type==0) messageStage.setTitle("Message:");
		else messageStage.setTitle("Error Message:");
		messageStage.show();
	}
}
