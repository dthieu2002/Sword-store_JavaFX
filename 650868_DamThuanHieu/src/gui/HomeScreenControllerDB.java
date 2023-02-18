package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import core.HRMDB;
import core.Sword;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class HomeScreenControllerDB implements Initializable{
	@FXML
	private Pane backgroundPane;
	@FXML
	private TableView<Sword> table;
	@FXML
	private TableColumn<Sword, String> idColumn;
	@FXML
	private TableColumn<Sword, String> nameColumn;
	@FXML
	private TableColumn<Sword, Float> heightColumn;
	@FXML
	private TableColumn<Sword, Float> weightColumn;
	@FXML
	private TableColumn<Sword, String> madeInColumn;
	@FXML
	private TableColumn<Sword, String> materialColumn;
	@FXML
	private TableColumn<Sword, Float> priceColumn;
	@FXML
	private TextField idText;
	@FXML
	private TextField nameText;
	@FXML
	private TextField heightText;
	@FXML
	private TextField weightText;
	@FXML
	private TextField madeInText;
	@FXML
	private TextField materialText;
	@FXML
	private TextField priceText;
	@FXML
	private Button addButton;
	@FXML
	private Button updateButton;
	@FXML
	private Button deleteButton;
	@FXML
	private Label loginMessage;
	@FXML
	private ImageView imageView;
	@FXML
	private Button addImageButton;
	@FXML
	private Button changeImageButton;
	@FXML
	private Button deleteImageButton;

	private int role; // role = 100 is customer , role = 200 is master
	private String userName;
	
	// create new object of class HRMDB
	private HRMDB hrmDB = new HRMDB("jdbc:ucanaccess://database/hrm.accdb", "", "");
	private MessageScreenClass messageScreenClass = new MessageScreenClass();
	
	// sử dụng để lưu controller đang sử dụng ( lấy từ login screen ) 
	// và để truyền controller này cho lớp Enter URL để có thể upload or change ảnh 
	// sẽ hiển thị ngay lập tức sau khi nhập 
	private HomeScreenControllerDB currentController;
	
	
	// method 
	
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Platform.runLater(()->{
			
			// set text fill of login message
			loginMessage.setTextFill(Color.YELLOW);
			
			// set background
			backgroundPane.styleProperty().set("-fx-background-color: linear-gradient(to right, #000046, #1cb5e0);");
			
			// === set login message ===
			if(role == 100) { // is customer
				loginMessage.setText("Customer login:\n"+userName);
				
				// disable all text and button
				idText.setDisable(true);
				nameText.setDisable(true);
				heightText.setDisable(true);
				weightText.setDisable(true);
				madeInText.setDisable(true);
				materialText.setDisable(true);
				priceText.setDisable(true);
				
				addButton.setDisable(true);
				updateButton.setDisable(true);
				deleteButton.setDisable(true);
				
				addImageButton.setDisable(true);
				changeImageButton.setDisable(true);
				deleteImageButton.setDisable(true);
	
				
			}
			else { // is master 
				loginMessage.setText("Master login:\n"+userName);
			}
			
			idColumn = (TableColumn<Sword, String>) table.getVisibleLeafColumn(0);
			idColumn.setCellValueFactory(new PropertyValueFactory<Sword, String>("id"));
			
			nameColumn = (TableColumn<Sword, String>) table.getVisibleLeafColumn(1);
			nameColumn.setCellValueFactory(new PropertyValueFactory<Sword, String>("name"));
			
			heightColumn = (TableColumn<Sword, Float>) table.getVisibleLeafColumn(2);
			heightColumn.setCellValueFactory(new PropertyValueFactory<Sword, Float>("height"));
			
			weightColumn = (TableColumn<Sword, Float>) table.getVisibleLeafColumn(3);
			weightColumn.setCellValueFactory(new PropertyValueFactory<Sword, Float>("weight"));
			
			madeInColumn = (TableColumn<Sword, String>) table.getVisibleLeafColumn(4);
			madeInColumn.setCellValueFactory(new PropertyValueFactory<Sword, String>("madeIn"));
			
			materialColumn = (TableColumn<Sword, String>) table.getVisibleLeafColumn(5);
			materialColumn.setCellValueFactory(new PropertyValueFactory<Sword, String>("material"));
			
			priceColumn = (TableColumn<Sword, Float>) table.getVisibleLeafColumn(6);
			priceColumn.setCellValueFactory(new PropertyValueFactory<Sword, Float>("price"));
			
			// get sword list 
			List<Sword> swordList = hrmDB.getSwordList();
			ObservableList<Sword> obsList = FXCollections.observableArrayList(swordList);
			
			// set item for table
			table.setItems(obsList);
			
		});
		
	}
	// Event Listener on TableView[#table].onMouseClicked
	@FXML
	public void onClickRow(MouseEvent event) {
		idText.setText(table.getSelectionModel().getSelectedItem().getId());
		nameText.setText(table.getSelectionModel().getSelectedItem().getName());
		heightText.setText(Float.toString(table.getSelectionModel().getSelectedItem().getHeight()));
		weightText.setText(Float.toString(table.getSelectionModel().getSelectedItem().getWeight()));
		madeInText.setText(table.getSelectionModel().getSelectedItem().getMadeIn());
		materialText.setText(table.getSelectionModel().getSelectedItem().getMaterial());
		priceText.setText(Float.toString(table.getSelectionModel().getSelectedItem().getPrice()));
		
		imageView.setImage(new Image(table.getSelectionModel().getSelectedItem().getImage()));
	}
	
	@FXML
	public void onClickAddButton(ActionEvent event) throws IOException {
		// check blank
		if(checkBlank()) {
			// raise error
			messageScreenClass.openMessageScreen(1, "Do not leave any text blank!");
			return;
		}
		
		Sword swordAdd = getSwordObjectFromTextField();
		if(hrmDB.addSword(swordAdd)) {
			table.getItems().add(swordAdd);
			// open message 
			messageScreenClass.openMessageScreen(0, "Add successful!");
			resetText();
		}
		else messageScreenClass.openMessageScreen(1, "Can't add!");
	}

	@FXML
	public void onClickUpdateButton(ActionEvent event) throws IOException {
		int index = table.getSelectionModel().getSelectedIndex();
		if(index >=0) {
			// check blank
			if(checkBlank()) {
				// raise error
				messageScreenClass.openMessageScreen(1, "Do not leave any text blank!");
				return;
			}
			
			Sword swordNew = getSwordObjectFromTextField();
			if(hrmDB.updateSword(swordNew)) {
				table.getItems().set(index, swordNew);
				// open message
				messageScreenClass.openMessageScreen(0, "Update successful!");
				resetText();
			}
			else messageScreenClass.openMessageScreen(1, "Can't update!");
		}
	}

	@FXML
	public void onClickDeleteButton(ActionEvent event) throws IOException {
		int index = table.getSelectionModel().getSelectedIndex();
		if(index>=0) {
			Sword swordSelected = table.getSelectionModel().getSelectedItem();
			if(hrmDB.deleteSword(swordSelected.getId())) {
				table.getItems().remove(index);
				// open message
				messageScreenClass.openMessageScreen(0, "Delete successful!");
				resetText();
			}
			else {
				//raise error 
				messageScreenClass.openMessageScreen(1, "Can't delete");
			}
		}
	}

	@FXML
	public void onClickAddImageButton(ActionEvent event) throws IOException {
		// get sword selected 
		Sword swordSelected = table.getSelectionModel().getSelectedItem();
				
		// check if is not image , if is empty then add
		if(swordSelected.getImage().equals("images/emptyImage.png")) {
			openURLScreen("Add");
		}
		else {
			// raise error
			messageScreenClass.openMessageScreen(1, "Can't add because there are already exits image!");
		}
	}

	@FXML
	public void onClickChangeImageButton(ActionEvent event) throws IOException {
		// get sword selected 
		Sword swordSelected = table.getSelectionModel().getSelectedItem();
		
		// check if is not image 
		if(swordSelected.getImage().equals("images/emptyImage.png")) {
			// raise error 
			messageScreenClass.openMessageScreen(1, "Can't change because there is no image!");
		}
		else {
			// open url scene
			openURLScreen("Change");
		}
	}

	@FXML
	public void onClickDeleteImageButton(ActionEvent event) throws IOException {
		// get sword selected 
		Sword swordSelected = table.getSelectionModel().getSelectedItem();
				
		// check if is not image , if empty then raise erro
		if(swordSelected.getImage().equals("images/emptyImage.png")) {
			// raise error 
			messageScreenClass.openMessageScreen(1, "Can't delete because there is no image!");
		}
		else {
			// set empty image
			swordSelected.setImage("images/emptyImage.png");
			imageView.setImage(new Image("images/emptyImage.png"));
			
			// update empty image in database
			hrmDB.updateSword(swordSelected);
			
			// open message
			messageScreenClass.openMessageScreen(0, "Delete image successful!");
		}

	}
	
	// type is Add or Change
	public void openURLScreen(String type) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EnterURLScreen.fxml"));
		Parent root = fxmlLoader.load();
		
		EnterURLScreenController enterURLScreenController = fxmlLoader.getController();
		enterURLScreenController.setType(type);
		enterURLScreenController.setSwordSelected(table.getSelectionModel().getSelectedItem());
		
		// use to change image display now after  add or update 
		enterURLScreenController.setHomeScreenControllerDB(currentController);
		
		Scene urlScene = new Scene(root);
		urlScene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
		
		Stage urlStage = new Stage();
		urlStage.setScene(urlScene);
		urlStage.setTitle(type+" image: ");
		urlStage.show();
	}
	
	public Sword getSwordObjectFromTextField() {
		String id = idText.getText();
		String name=nameText.getText();
		float height = Float.parseFloat(heightText.getText());
		float weight = Float.parseFloat(weightText.getText());
		String madeIn = madeInText.getText();
		String material = materialText.getText();
		float price = Float.parseFloat(priceText.getText());
		
		return new Sword(id,name,height,weight,madeIn,material,price,"");
	}
	
	public boolean checkBlank() {
		if(idText.getText().equals("")) return true;
		if(nameText.getText().equals("")) return true;
		if(heightText.getText().equals("")) return true;
		if(weightText.getText().equals("")) return true;
		if(madeInText.getText().equals("")) return true;
		if(materialText.getText().equals("")) return true;
		if(priceText.getText().equals("")) return true;
		
		return false;
	}
	
	public void resetText() {
		idText.setText("");
		nameText.setText("");
		heightText.setText("");
		weightText.setText("");
		madeInText.setText("");
		materialText.setText("");
		priceText.setText("");
	}
	
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public HomeScreenControllerDB getCurrentController() {
		return currentController;
	}
	public void setCurrentController(HomeScreenControllerDB currentController) {
		this.currentController = currentController;
	}
	
	public void setImageView(String url) {
		imageView.setImage(new Image(url));
	}
}
