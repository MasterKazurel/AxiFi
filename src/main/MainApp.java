package main;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import control.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainApp extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}
	private List<Stage> stages;
	private BorderPane rootLayout;
	
	public enum Views {
		PRIMARY("Primary"),
		LOGIN("Login.fxml"),
		MAIN("Main.fxml"),
		NEW_TRANS("NewTrans.fxml");
		private String val;
		private Views(String val)
		{ this.val = "/view/" + val; }
	}
    

    public MainApp() {
    }

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("AxiFi");
		primaryStage.setUserData(Views.PRIMARY);
		stages = new ArrayList<Stage>();
		
		stages.add(primaryStage);
		initRootLayout(primaryStage);
        showInNewStage(Views.LOGIN);
	}
	
	public void initRootLayout(Stage primaryStage) {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getURL("/view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            primaryStage.setScene(new Scene(rootLayout));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public Stage getStage(Views view) {
		for (Stage s: stages)
			if (s.getUserData().equals(view))
				return s;
		return null;
	}
	
	public void showInNewStage(Views view, Object... data) {
		Stage stage = new Stage();
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getURL(view.val));
            AnchorPane main = (AnchorPane) loader.load();

            stage.setScene(new Scene(main));
            
            // Give the controller access to the main app.
            Controller controller = loader.getController();
            controller.setMainApp(this);
            processData(controller, data);
            
            stage.show();
            stage.setUserData(view);
            stages.add(stage);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void show(Views view, Object... data) {
		Stage primaryStage = getStage(Views.PRIMARY);
		try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getURL(view.val));
            AnchorPane main = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(main);
            
            // Give the controller access to the main app.
            Controller controller = loader.getController();
            controller.setMainApp(this);
            processData(controller, data);
            
            switch(view) {
	            case LOGIN:
	            	primaryStage.setMaximized(false);
	            	break;
	            case MAIN:
	            	primaryStage.setMaximized(true);
	            	break;
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
		primaryStage.show();
	}
	
	public void close(Views view) {
		Stage temp = getStage(view);
		temp.close();
		stages.remove(temp);
		temp = null;
	}
	
	private void processData(Controller controller, Object... data) {
		if (data != null && data.length > 0)
			controller.receiveData(data);
	}
	
	// helper method for readability
	private URL getURL(String relPath) {
		return System.class.getResource(relPath);
	}
	
}
