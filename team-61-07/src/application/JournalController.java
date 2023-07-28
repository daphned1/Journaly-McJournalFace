package application;

import java.io.IOException;
import java.net.URL;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class JournalController {
	Journal journalManager = new Journal();
	JournalEntry userJournal = new JournalEntry();
	
	SearchController searchController;
	private final static String title = "Journaly McJournalFace";
	String fileTitle ="";
	// From Journal Entry Page
    @FXML
    public JFXDatePicker datePicker;
    @FXML
    public TextField journalTitle;
    @FXML
    public JFXTimePicker timePicker;
    @FXML
    public TextArea journalContent;
    @FXML
    public Label id;
    
    // From Edit Journal Page 
    @FXML
    private TextField titleEdit;

    @FXML
    private JFXDatePicker editDatePicker;

    @FXML
    private JFXTimePicker editTimePicker;
    
    /**
     * Setting the old file name 
     * @param name The old file name 
     */
    public void setOldName(String name) {
    	fileTitle = name;
    }
    
    /**
     * Getter for the old file name 
     * @return fileTitle
     */
    public String getOldName() {
    	return fileTitle;
    }
    /**
     * Exiting journal 
     * @param event The action that will happen when the exit button is clicked
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
     * @param event The action that will happen when the save button is clicked
     * @throws IOException The error that will be thrown when an error occurs in this method
     */
    @FXML
    void saveJournalEntry(ActionEvent event) throws IOException {
    	// If there is no value for date or time, show the error pop-up
    	if (datePicker.getValue() == null || timePicker.getValue() == null) {
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
     * @param event The action that will happen when the save button is clicked
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
     * @param event The action that will happen when the save error button is clicked
     */
    @FXML
    void okError(ActionEvent event) {
    	Node source = (Node) event.getSource();
    	Stage stage = (Stage) source.getScene().getWindow();
    	stage.close();
    }
    
    /**
     * Opens the edit page 
     * @param event The action that will happen when the edit button is clicked
     * @throws IOException The error that will be thrown when an error occurs in this method
     */
    @FXML
    void editButton(ActionEvent event) throws IOException {
    	// If there is no value for date or time, show the error pop-up
    	if (datePicker.getValue() == null || timePicker.getValue() == null) {
    		URL urlRoot = getClass().getClassLoader().getResource("views/saveJournalError.fxml");
    		Parent root = FXMLLoader.load(urlRoot);
    		
    		Scene scene = new Scene (root, 292, 170);
    		Stage stage = new Stage();
    		stage.initModality(Modality.APPLICATION_MODAL);
    		stage.setScene(scene);
    		stage.showAndWait();
    	}
    	else {
    		userJournal = new JournalEntry(datePicker.getValue().toString(), timePicker.getValue().toString(), journalTitle.getText(), journalContent.getText(), "tempFileName");
            userJournal.setOldFileName(getOldName());
        	journalManager.modifyEntry(userJournal);
            	
        	FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("views/editPopup.fxml"));
            Parent root = loader.load();
        		
        	Scene scene = new Scene (root, 220, 178);
        	Stage stage = new Stage();
        	stage.initModality(Modality.APPLICATION_MODAL);
        	stage.setScene(scene);
        	stage.showAndWait();
    	}
    	
    }
    
    /**
     * Exiting edit 
     * @param event The action that will happen when the cancel button is clicked
     * @throws IOException The error that will be thrown when an error occurs in this method 
     */
    @FXML
    void cancelButton(ActionEvent event) {
    	Node source = (Node) event.getSource();
    	Stage stage = (Stage) source.getScene().getWindow();
    	stage.close();
    }
    
    /**
     * Saving journal 
     * @param event The action that will happen when the save button is clicked
     * @throws IOException The error that will be thrown when an error occurs in this method 
     */
    @FXML
    void saveButton(ActionEvent event) {
    	Node source = (Node) event.getSource();
    	Stage stage = (Stage) source.getScene().getWindow();
    	stage.close();
    }
    // Edit Journal Page 
    /**
     * Exiting edit confirmation
     * @param event The action that will happen when the cancel button is clicked
     * @throws IOException The error that will be thrown when an error occurs in this method 
     */
    @FXML
    void cancelClicked(ActionEvent event) {
    	Node source = (Node) event.getSource();
    	Stage stage = (Stage) source.getScene().getWindow();
    	stage.close();
    }
}
