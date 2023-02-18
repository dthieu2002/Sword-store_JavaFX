package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import core.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import service.AuthenticationServiceImpl;

public class LoginScreenControllerDB implements Initializable{
	@FXML
	private Pane pane01;
	@FXML
	private RadioButton customerRadio;
	@FXML
	private ToggleGroup group123;
	@FXML
	private RadioButton masterRadio;
	@FXML
	private TextField userNameText;
	@FXML
	private PasswordField passwordText;
	@FXML
	private ImageView image01;
	@FXML
	private StackPane stackPane;
	@FXML
	private TextField showPasswordText;
	@FXML CheckBox checkBox;
	
	// create new object of class service
	// authentication use to check account 
	private AuthenticationServiceImpl authenticationServiceImpl = new AuthenticationServiceImpl();
	// message class use to show message or error
	private MessageScreenClass messageScreenClass = new MessageScreenClass();
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Platform.runLater(()->{
			// set background color
			pane01.styleProperty().set("-fx-background-color: linear-gradient(to top right,#000C40,black,black, #000C40);");	
			
			// set image to image view
			image01.setImage(new Image("images/loginImage03.png"));
			
			// set default selected customer radioS
			customerRadio.setSelected(true);
			stackPane.setDisable(true); // passwordText and showPasswordText
			checkBox.setDisable(true);
			checkBox.setTextFill(Color.WHITE);
			passwordText.toFront();
			showPasswordText.toBack();
			
		});
	}
	
	@FXML
	public void onClickChangeCheckBox() {
		if(checkBox.isSelected()) {
			showPasswordText.toFront();
			showPasswordText.setText(passwordText.getText());
		}
		else {
			passwordText.toFront();
			passwordText.setText(showPasswordText.getText());
		}
	}
	
	@FXML
	public void onClickRadioChange(ActionEvent event) {
		// reset text
		resetText();
		
		if(customerRadio.isSelected())	{
			stackPane.setDisable(true);
			checkBox.setDisable(true);
			checkBox.setSelected(false);
			passwordText.toFront();
			showPasswordText.toBack();
		}
		else {
			stackPane.setDisable(false);
			checkBox.setDisable(false);
		}
	}
	
	// Event Listener on Button.onAction
	@FXML
	public void onClickLoginButton(ActionEvent event) throws IOException {
		// check blank
		if(checkBlank()) return;
		
		// if is customer then go to home sreen 
		if(customerRadio.isSelected()) {
			// go to home screen
			goToHomeScreenDB();
		}
		
		else { 
			// set password for password text if show password is on
			if(checkBox.isSelected()) passwordText.setText(showPasswordText.getText());
			
			// if is mater then check user and password 
			User user = new User(userNameText.getText(), passwordText.getText());
			if(authenticationServiceImpl.checkUserLogin(user)) { // === case valid ===  
				// go to home screen
				goToHomeScreenDB();
			}
			else { // === wrong user or password === 
				// open messageScreen
				String message = "User name or password is wrong!";
				messageScreenClass.openMessageScreen(1, message);
			}
		}
		
	}
	
	@FXML
	public void onClickRegisterButton() throws IOException{
		closeCurrentStage();
		
		// go to register screen 
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("RegisterScreen.fxml"));
		
		Parent root = fxmlLoader.load();
		
		Scene registerScene = new Scene(root);
		registerScene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
		
		Stage registerStage = new Stage();
		
		registerStage.setScene(registerScene);
		registerStage.setTitle("Register screen:");
		registerStage.show();
	}
	
	public void goToHomeScreenDB() throws IOException {
		// close current stage 
		closeCurrentStage();
		
		FXMLLoader fxmlLoader = new FXMLLoader((getClass().getResource("HomeScreen.fxml")));
		Parent root = fxmlLoader.load();
		
		//=== get controller 
		HomeScreenControllerDB homeScreenControllerDB = fxmlLoader.getController();
		//set role 
		if(customerRadio.isSelected()) homeScreenControllerDB.setRole(100);
		else homeScreenControllerDB.setRole(200);
		// set user name 
		homeScreenControllerDB.setUserName(userNameText.getText());
		
		// set currentController
		homeScreenControllerDB.setCurrentController(homeScreenControllerDB);
		
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
		
		Stage homeStage = new Stage();
		homeStage.setScene(scene);
		homeStage.setTitle("Home screen:");
		homeStage.show();
	}

	public void closeCurrentStage() {
		Stage currentStage = (Stage) customerRadio.getScene().getWindow();
		currentStage.close();
	}
	
	public boolean checkBlank() throws IOException {
		boolean check=false;
		if(userNameText.getText().equals("")) check=true;
		else {
			if(masterRadio.isSelected()) {
				if(checkBox.isSelected()) {
					// check show password text
					if(showPasswordText.getText().equals("")) check=true;
				}
				else { // check password text
					if(passwordText.getText().equals("")) check=true;
				}
			}
		}
		
		// open message screen if check = true 
		if(check) {
			if(customerRadio.isSelected()) messageScreenClass.openMessageScreen(1, "Do not leave username blank!");
			else {
				// set text for password
				if(checkBox.isSelected()) passwordText.setText(showPasswordText.getText());
				else showPasswordText.setText(passwordText.getText());
				
				String message;
				if(userNameText.getText().equals("") && passwordText.getText().equals("")) {
					message="Do not leave username and password blank!";
				}
				else if(userNameText.getText().equals("")){
					message="Do not leave username blank!";
				}
				else message="Do not leave password blank!";
				
				// open message screen 
				messageScreenClass.openMessageScreen(1, message);
			}
		}
		return check;
	}
	
	public void resetText() {
		userNameText.setText("");
		passwordText.setText("");
		showPasswordText.setText("");
	}
}
