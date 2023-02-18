package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class MessageScreenController implements Initializable{
	@FXML
	private Label messageText;

	// property to set messageText
	private int type; // 0 is message and 1 is error
	private String message;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Platform.runLater(()->{
			if(type==0) messageText.setTextFill(Color.GREEN); // message is green 
			else messageText.setTextFill(Color.RED); // error is red 
			
			messageText.setText(message);
		});
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
