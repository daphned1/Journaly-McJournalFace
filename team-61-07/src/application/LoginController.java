package application;

import java.io.IOException;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LoginController {
	
	Account user = new Account();
	private final static String title = "Journaly McJournalFace";
	final String FILE_NAME = "JMJAccount";
	private FlatFile fileManager;
	
	@FXML
	private PasswordField passwordField;
	@FXML
    private PasswordField oldPass;
    @FXML
    private PasswordField newPass;
    @FXML
    private PasswordField repeatNewPass;
    @FXML
    private TextField securityQuestion;
    @FXML
    private TextField ansQuestion;
    
    @FXML
    void loginClicked(ActionEvent event) throws IOException {
    	//System.out.println(user.getPassword());
    	// Checks if this is first time login, if it is, go to changePassword scene
    	if (passwordField.getText().equals("p")) {
    		URL urlRoot = getClass().getClassLoader().getResource("views/changePass.fxml");
			Parent root = FXMLLoader.load(urlRoot);
			
			Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
			Scene scene = new Scene(root, 600, 400);
			stage.setScene(scene);
			stage.setTitle(title);
			stage.show();
			
    	}
    	
    	// Checks if account exists. If it does, redirect to homepage 
    	
    	else if (user.accountLogin(passwordField.getText())) {
    		URL urlRoot = getClass().getClassLoader().getResource("views/homepage.fxml");
    		Parent root = FXMLLoader.load(urlRoot);
    		Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
    		Scene scene = new Scene(root, 600, 400);
    		stage.setScene(scene);
    		stage.setTitle(title);
    		stage.show();
    	}
    }
    
    // Change password page -> confirmation
    @FXML
    void submitClicked(ActionEvent event) throws IOException {
    	// Checks if the old password, security question, and answer question textfield is not empty. If not, proceed with changing password and save to file
    	if (oldPass.getText().equals("p") && oldPass.getText().isEmpty() == false && securityQuestion.getText().isEmpty() == false && ansQuestion.getText().isEmpty() == false) {
    		//user = new Account(newPass.getText(), securityQuestion.getText(), ansQuestion.getText());
    		user.setPassword(newPass.getText());
    		user.setQuestion(securityQuestion.getText());
    		user.setAnswer(ansQuestion.getText());

    		user.changeFile(oldPass.getText(), newPass.getText(), repeatNewPass.getText(), securityQuestion.getText(), ansQuestion.getText());
    		
    		// Once password submission is done, show the confirmation pop-up
    		URL urlRoot = getClass().getClassLoader().getResource("views/confirmChangePass.fxml");
    		Parent root = FXMLLoader.load(urlRoot);
    		
    		Scene scene = new Scene (root, 292, 170);
    		Stage stage = new Stage();
    		stage.initModality(Modality.APPLICATION_MODAL);
    		stage.setScene(scene);
    		stage.showAndWait();
    		
    		// Once pop-up closes, have the reset password change to homepage 
    		urlRoot = getClass().getClassLoader().getResource("views/homepage.fxml");
        	root = FXMLLoader.load(urlRoot);
        	stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
    		scene = new Scene(root, 600, 400);
    		stage.setScene(scene);
    		stage.setTitle(title);
    		stage.show();
    		
    	}
    }
    
    // Changing password -> login page 
    @FXML
    void cancelClicked(ActionEvent event) throws IOException {
    	URL urlRoot = getClass().getClassLoader().getResource("views/login.fxml");
		Parent root = FXMLLoader.load(urlRoot);
		
		Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root, 600, 400);
		stage.setScene(scene);
		stage.setTitle(title);
		stage.show();
    }
    
    // Closing confirmation pop-up
    @FXML
    boolean okClicked(ActionEvent event) throws IOException {
    	boolean clicked = false;
    	Node source = (Node) event.getSource();
    	Stage stage = (Stage) source.getScene().getWindow();
    	stage.close();
    	clicked = true;
    	return clicked;
    }
    
    
    //homepage -> login
    @FXML
    void logoutClicked(ActionEvent event) throws IOException {
    	URL urlRoot = getClass().getClassLoader().getResource("views/login.fxml");
		Parent root = FXMLLoader.load(urlRoot);
		
		Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root, 600, 400);
		stage.setScene(scene);
		stage.setTitle(title);
		stage.show();
    }

}
