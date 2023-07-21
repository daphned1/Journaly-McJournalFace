package application;

import java.io.IOException;
import java.net.URL;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LoginController {
	
	Account user = new Account();
	JournalEntry userJournal;
	Journal journalManager = new Journal();
	private final static String title = "Journaly McJournalFace";
	
	// From Login Page
	@FXML
	private PasswordField passwordField;
	
	//From First time Change Password FXML
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
    
    // From Reset Password FXML
    @FXML
    private PasswordField resetRepeatPass;
    @FXML
    private PasswordField resetNewPass;
    @FXML
    private TextField resetSecQuestion;
    @FXML
    private TextField resetSecAnswer;
    
    // From Homepage 
    @FXML
    private Button createdJournal;
    
    // From Journal Entry Page
    @FXML
    private JFXDatePicker datePicker;
    @FXML
    private TextField journalTitle;
    @FXML
    private JFXTimePicker timePicker;
    @FXML
    private TextArea journalContent;
    
    
    // Login FXML Methods 
    /**
     * Method for logging in. Will check if this is first time login, or if an account already exists
     * @param event The action that will happen when the login button is clicked
     * @throws IOException The error that will be thrown when an error occurs in this method 
     */
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
    
    /**
     * Opening the forgot password scene
     * @param event
     * @throws IOException
     */
    @FXML
    void forgotPassClicked(ActionEvent event) throws IOException {
    	URL urlRoot = getClass().getClassLoader().getResource("views/forgetPassword.fxml");
		Parent root = FXMLLoader.load(urlRoot);
		
		Scene scene = new Scene (root, 265, 333);
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setScene(scene);
		stage.showAndWait();
    }
    
    // ChangePass FXML Methods 
    /**
     * Changing the password for first time login. 
     * @param event The action that will happen when the login button is clicked
     * @throws IOException The error that will be thrown when an error occurs in this method 
     */
    @FXML
    void submitClicked(ActionEvent event) throws IOException {
    	// Checks if the old password, security question, and answer question textfield is not empty. If not, proceed with changing password and save to file
    	if (oldPass.getText().equals("p") && oldPass.getText().isEmpty() == false && securityQuestion.getText().isEmpty() == false && ansQuestion.getText().isEmpty() == false) {
    		//user = new Account(newPass.getText(), securityQuestion.getText(), ansQuestion.getText());
    		user.setPassword(newPass.getText());
    		user.setQuestion(securityQuestion.getText());
    		user.setAnswer(ansQuestion.getText());

    		user.changePassword(oldPass.getText(), newPass.getText(), repeatNewPass.getText(), securityQuestion.getText(), ansQuestion.getText());
    		
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
    
    /**
     * Changing scene from changing password page to login page when the cancel button is clicked 
     * @param event The action that will happen when the login button is clicked
     * @throws IOException The error that will be thrown when an error occurs in this method 
     */
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
    
    // Confirmation FXML Methods 
    /**
     * Closing confirmation pop-up
     * @param event The action that will happen when the login button is clicked
     * @throws IOException The error that will be thrown when an error occurs in this method 
     */
    @FXML
    void okClicked(ActionEvent event) throws IOException {
    	Node source = (Node) event.getSource();
    	Stage stage = (Stage) source.getScene().getWindow();
    	stage.close();
    }
    
    // Forget Password FXML Methods 
    @FXML
    void passResetSubmitClicked(ActionEvent event) {
    	if (user.getAnswer().equals(resetSecAnswer.getText())) {
    		// Reset the password 
        	user.resetPassword(resetSecAnswer.getText(), resetNewPass.getText(), resetRepeatPass.getText(), resetSecQuestion.getText());
        	// Close pop-up once done
    		Node source = (Node) event.getSource();
        	Stage stage = (Stage) source.getScene().getWindow();
        	stage.close();
    	}
    }
    
    /**
     * Closes the forget password pop-up
     * @param event The action that will happen when the login button is clicked
     * @throws IOException The error that will be thrown when an error occurs in this method
     */
    @FXML
    void resetCancelClicked(ActionEvent event) throws IOException {
    	Node source = (Node) event.getSource();
    	Stage stage = (Stage) source.getScene().getWindow();
    	stage.close();
    }
    
    // Homepage FXML Methods 
    /**
     * Opens the journal page where user can write the title, pick date-time
     * @param event The action that will happen when the login button is clicked
     * @throws IOException The error that will be thrown when an error occurs in this method
     */
    @FXML
    void newEntryClicked(ActionEvent event) throws IOException {
    	URL urlRoot = getClass().getClassLoader().getResource("views/journalPage.fxml");
		Parent root = FXMLLoader.load(urlRoot);
		
		Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root, 600, 400);
		stage.setScene(scene);
		stage.setTitle(title);
		stage.show();
    }
    
    /**
     * When the journal entry button is clicked, it'll go into the journal entry page where user can write their content
     * @param event
     * @throws IOException
     */
    @FXML
    void journalEntryClicked(ActionEvent event) throws IOException {
    	URL urlRoot = getClass().getClassLoader().getResource("views/journalPage.fxml");
		Parent root = FXMLLoader.load(urlRoot);
		
		Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root, 600, 400);
		stage.setScene(scene);
		stage.setTitle(title);
		stage.show();
    }
    
    /**
     * Logging out of application 
     * @param event The action that will happen when the login button is clicked
     * @throws IOException The error that will be thrown when an error occurs in this method 
     */
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
    
    // Journal Entry Page
    /**
     * Exiting journal 
     * @param event The action that will happen when the login button is clicked
     * @throws IOException The error that will be thrown when an error occurs in this method 
     */
    @FXML
    void exitJournalEntry(ActionEvent event) throws IOException {
    	URL urlRoot = getClass().getClassLoader().getResource("views/homepage.fxml");
		Parent root = FXMLLoader.load(urlRoot);
		
		Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root, 600, 400);
		stage.setScene(scene);
		stage.setTitle(title);
		stage.show();
    }
    
    /**
     * Saving the journal entry 
     * @param event The action that will happen when the login button is clicked
     * @throws IOException The error that will be thrown when an error occurs in this method
     */
    @FXML
    void saveJournalEntry(ActionEvent event) throws IOException {
    	// If there is no value for date or time, show the error pop-up
    	if (datePicker.getValue() == null && timePicker.getValue() == null) {
    		URL urlRoot = getClass().getClassLoader().getResource("views/saveJournalError.fxml");
    		Parent root = FXMLLoader.load(urlRoot);
    		
    		Scene scene = new Scene (root, 292, 170);
    		Stage stage = new Stage();
    		stage.initModality(Modality.APPLICATION_MODAL);
    		stage.setScene(scene);
    		stage.showAndWait();
    	}
    	// If everything else is fine, show save confirmation
    	else {
    		userJournal = new JournalEntry(datePicker.getValue().toString(), timePicker.getValue().toString(), journalTitle.getText(), journalContent.getText(), "tempFileName");
        	journalManager.exportEntry(userJournal);
        	
        	URL urlRoot = getClass().getClassLoader().getResource("views/saveEntryConfirm.fxml");
    		Parent root = FXMLLoader.load(urlRoot);
    		
    		Scene scene = new Scene (root, 292, 170);
    		Stage stage = new Stage();
    		stage.initModality(Modality.APPLICATION_MODAL);
    		stage.setScene(scene);
    		stage.showAndWait();
    	}
    	
    }
    
    /**
     * Closes the save journal entry pop-up
     * @param event The action that will happen when the login button is clicked
     * @throws IOException The error that will be thrown when an error occurs in this method
     */
    @FXML
    void okJournalEntrySave(ActionEvent event) {
    	Node source = (Node) event.getSource();
    	Stage stage = (Stage) source.getScene().getWindow();
    	stage.close();
    }
    
    /**
     * Closes the saving error journal pop-up 
     * @param event The action that will happen when the login button is clicked
     * @throws IOException The error that will be thrown when an error occurs in this method
     */
    @FXML
    void okError(ActionEvent event) {
    	Node source = (Node) event.getSource();
    	Stage stage = (Stage) source.getScene().getWindow();
    	stage.close();
    }


}
