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
import javafx.stage.Stage;

public class LoginController {
	
	private Account user;
	private final static String title = "Journaly McJournalFace";
	final String FILE_NAME = "JMJAccount";
	private FlatFile fileManager;
	
	@FXML
	private PasswordField passwordField;
	@FXML
    private TextField oldPass;
    @FXML
    private TextField newPass;
    @FXML
    private TextField repeatNewPass;
    @FXML
    private TextField securityQuestion;
    @FXML
    private TextField ansQuestion;

    @FXML
    void loginClicked(ActionEvent event) throws IOException {
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
    }
    
    @FXML
    void submitClicked(ActionEvent event) throws IOException {
    	// Checks if the old password, security question, and answer question textfield is not empty. If not, proceed with changing password and save to file
    	if (oldPass.getText().isEmpty() == false && securityQuestion.getText().isEmpty() == false && ansQuestion.getText().isEmpty() == false) {
    		user = new Account(newPass.getText(), securityQuestion.getText(), ansQuestion.getText());
    		fileManager = new FlatFile();
    		Account defaultAccount = new Account();//creating default account
			fileManager.createEmptyFile(FILE_NAME);//creating file
			fileManager.writeFile(FILE_NAME, defaultAccount.getPassword() + "\n" + defaultAccount.getQuestion() + "\n" + defaultAccount.getAnswer());//writing default account to file
    	
    		user.accountFromArray(fileManager.readFileArray(FILE_NAME));//setting program account to file account
    		user.changePassword(oldPass.getText(), newPass.getText(), repeatNewPass.getText(), securityQuestion.getText(), ansQuestion.getText());//running changePassword method to change after default password
    		fileManager.writeFile(FILE_NAME, user.getPassword() + "\n" + user.getQuestion() + "\n" + user.getAnswer());//writing default account to file
    		
    		// Once password submission is done, show the confirmation page 
    		URL urlRoot = getClass().getClassLoader().getResource("views/confirmChangePass.fxml");
    		Parent root = FXMLLoader.load(urlRoot);
    		
    		Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
    		Scene scene = new Scene(root, 292, 170);
    		stage.setScene(scene);
    		stage.setTitle(title);
    		stage.show();
    	}
    }
    
    @FXML
    void okClicked(ActionEvent event) throws IOException {
    	URL urlRoot = getClass().getClassLoader().getResource("views/login.fxml");
		Parent root = FXMLLoader.load(urlRoot);
		
		Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root, 600, 400);
		stage.setScene(scene);
		stage.setTitle(title);
		stage.show();
    }


}
