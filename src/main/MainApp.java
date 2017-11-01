package main;

import java.io.IOException;
import java.net.URL;

import control.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {
	private Stage primaryStage;
	private BorderPane rootLayout;
	
	public enum Views {
		LOGIN("Login.fxml"),
		MAIN("Main.fxml"),
		TRANSACTION("NewTransaction.fxml");
		private String val;
		private Views(String val)
		{ this.val = "/view/" + val; }
	}
    

    public MainApp() {
    }

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("AxiFi");
		initRootLayout();
        show(Views.LOGIN);
	}
	
	public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getURL("/view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	public void show(Views view) {
		try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getURL(view.val));
            AnchorPane Main = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(Main);
            
            // Give the controller access to the main app.
            Controller controller = loader.getController();
            controller.setMainApp(this);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	// helper method for readability
	private URL getURL(String relPath) {
		return System.class.getResource(relPath);
	}


	public static void main(String[] args) {
		launch(args);
	}
}
