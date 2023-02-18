package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import core.HRMDB;
import core.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class RegisterScreenDBController implements Initializable{
	@FXML
	private Pane backgroundPane;
	@FXML
	private TextField userNameText;
	@FXML
	private TextField passwordText;
	@FXML
	private TextField passwordConfirmText;
	
	// more property
	private MessageScreenClass messageScreenClass = new MessageScreenClass(); // use to open message screen
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Platform.runLater(()->{
			// set background color
			backgroundPane.styleProperty().set("-fx-background-color: linear-gradient(to right, #8e9eab,#eef2f3);");	
	
		});
	}
	
	// Event Listener on Button.onAction
	@FXML
	public void onClickCancelButton(ActionEvent event) throws IOException {
		closeCurrentStage();
		
		//open login screen 
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoginScreen.fxml"));
		Parent root = fxmlLoader.load();
		
		// add scc
		Scene loginScene = new Scene(root);
		loginScene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
		
		Stage loginStage = new Stage();
		loginStage.setScene(loginScene);
		loginStage.setTitle("Login screen:");
		loginStage.show();
	}
	
	// Event Listener on Button.onAction
	@FXML
	public void onClickRegisterButton(ActionEvent event) throws IOException {
		if(checkBlank()) return;
		
		// check password with passwordConfirm 
		if(passwordText.getText().equals(passwordConfirmText.getText()) == false) {
			// open error message 
			messageScreenClass.openMessageScreen(1, "Password and password confirm is not same!");
			return;
		}
		
		// add user into user model database 
		HRMDB hrmDB = new HRMDB("jdbc:ucanaccess://database/hrm.accdb","", "");
		
		if(hrmDB.addUser(new User(userNameText.getText(), passwordText.getText()))) {
			// open message
			messageScreenClass.openMessageScreen(0, "Register successful!");
		}
		else {
			// open error message 
			messageScreenClass.openMessageScreen(1, "Username has been already exists!");
		}
		
	}
	
	public boolean checkBlank() throws IOException {
		boolean check=false;
		
		if(userNameText.getText().equals("")) check = true;
		else if(passwordText.getText().equals("")) check = true;
		else if(passwordConfirmText.getText().equals("")) check=true;
		
		// open error message if any text is blank
		if(check) {
			// 0 is message , 1 is error message
			messageScreenClass.openMessageScreen(1, "Do not leave any text blank!");
		}
		
		return check;
	}
	
	public void closeCurrentStage() {
		Stage registerStage = (Stage) userNameText.getScene().getWindow();
		registerStage.close();
	}

	
}
