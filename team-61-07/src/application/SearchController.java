package application;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SearchController implements Initializable{
	
	private final static String title = "Journaly McJournalFace";
	SearchEntry search = new SearchEntry();
	Stage stage;
	JournalController journalController;
	JournalEntry entry = new JournalEntry();
	
	@FXML
    private ListView<String> listView;
	
	 @FXML
	 private TextField searchBar;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		listView.getItems().addAll(search.getEntryList());
	}
	
	@FXML
    void searchClicked(ActionEvent event) throws Exception {
		ArrayList<String> filterWords;
		filterWords = search.searchTitles(searchBar.getText(), search.getEntryList());
		listView.getItems().clear();
		listView.getItems().addAll(filterWords);
    }

	@FXML
    void rowClicked(MouseEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("views/journalPage.fxml"));
    	Parent root = loader.load();
    	journalController = loader.getController();
		String name = listView.getSelectionModel().getSelectedItem();
		
		journalController.datePicker.setValue(LocalDate.parse(entry.getDate(name)));
		journalController.timePicker.setValue(LocalTime.parse(entry.getTime(name)));
		journalController.journalTitle.setText(entry.getTitle(name));
		journalController.journalContent.setText(entry.getContents(name));
		
		Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root, 600, 400);
		stage.setScene(scene);
		stage.setTitle(title);
		stage.show();
    }
	
	/**
     * Returns to the homepage
     * @param event The action that will happen when the login button is clicked
     * @throws IOException The error that will be thrown when an error occurs in this method
     */
    @FXML
    void searchCancelled(ActionEvent event) throws IOException {
    	URL urlRoot = getClass().getClassLoader().getResource("views/homepage.fxml");
		Parent root = FXMLLoader.load(urlRoot);
		
		Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root, 600, 400);
		stage.setScene(scene);
		stage.setTitle(title);
		stage.show();
    }

}
