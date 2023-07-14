package application;
	
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	
	private final static String title = "Journaly McJournalFace";
	
	@Override
	public void start(Stage primaryStage) {
		try {
			// Connecting to the login fxml file 
			URL urlRoot = getClass().getClassLoader().getResource("views/login.fxml");
			Parent root = FXMLLoader.load(urlRoot);
			
			Scene scene = new Scene(root, 600, 400);
			primaryStage.setScene(scene);
			primaryStage.setTitle(title);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
