package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import core.HRMDB;
import core.Sword;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class EnterURLScreenController implements Initializable{
	@FXML
	private TextField urlText;
	@FXML
	private Button button;
	
	// more properties
	private String type; // Add or Change
	private Sword swordSelected;
	private MessageScreenClass messageScreenClass = new MessageScreenClass(); // use to show message or error message
	private HRMDB hrmDB = new HRMDB("jdbc:ucanaccess://database/hrm.accdb", "", "");
	
	private HomeScreenControllerDB homeScreenControllerDB;
	
	
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Platform.runLater(()->{
			button.setText(type+" image");
			
		});
	}
	
	// Event Listener on Button[#button].onAction
	@FXML
	public void onClickButton(ActionEvent event) throws IOException {
		// check blank
		if(urlText.getText().equals("")) {
			// open error message 
			messageScreenClass.openMessageScreen(1, "Do not leave url text blank!");
			return;
		}
		
		
		// update image in car selected 
		swordSelected.setImage(urlText.getText());
		
		if(hrmDB.updateSword(swordSelected)) {
			// open message 
			messageScreenClass.openMessageScreen(0, type+" images successful!");
			
			// change image in home screen 
			homeScreenControllerDB.setImageView(urlText.getText());
		}
		else {
			// open error message 
			messageScreenClass.openMessageScreen(1, "URL is not valid!");
		}
		
	}
	
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}

	public String getURLText() {
		return urlText.getText();
	}

	public Sword getSwordSelected() {
		return swordSelected;
	}

	public void setSwordSelected(Sword swordSelected) {
		this.swordSelected = swordSelected;
	}
	
	public HomeScreenControllerDB getHomeScreenControllerDB() {
		return homeScreenControllerDB;
	}

	public void setHomeScreenControllerDB(HomeScreenControllerDB homeScreenControllerDB) {
		this.homeScreenControllerDB = homeScreenControllerDB;
	}

}
