package application;

import java.io.IOException;

import control.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
	
	private Controller[] controllers;
	private Stage[] stages;
	private Pane[] views;
	
	public enum Stages {
		MAIN,
		LOGIN,
		NEW_TRANS,
		DEL_ACC,
		NEW_ACC;
	}
	
	public enum Views {
		LOGIN("Login.fxml"),
		MAIN("Main.fxml"),
		NEW_TRANS("NewTrans.fxml"),
		DEL_ACC("DeleteAcct.fxml"),
		NEW_ACC("MakeAcct.fxml");
		private String val;
		private Views(String val)
		{ this.val = "/view/" + val; }
	}
	
/*-----------------------------------------------------------------------------*/

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		initStages(primaryStage);
		initViewsAndControllers();
        show(Stages.LOGIN, Views.LOGIN);
	}
	
	// Creates and adds the stages to the stages array.
	private void initStages(Stage mainStage) {
		stages = new Stage[Stages.values().length];
		int ct = 0;
		for (Stages s: Stages.values())
			switch (s) {
				case MAIN:
					mainStage.setTitle("AxiFi");
					mainStage.setUserData(Stages.MAIN);
					mainStage.setMaximized(true);
					stages[ct] = mainStage;
					ct++;
					break;
				case LOGIN:
					Stage loginStage = new Stage();
					loginStage.setMaximized(false);
					loginStage.setUserData(Stages.LOGIN);
					stages[ct] = loginStage;
					ct++;
					break;
				case NEW_TRANS:
					Stage transStage = new Stage();
					transStage.setMaximized(false);
					transStage.initStyle(StageStyle.UNDECORATED);
					transStage.setUserData(Stages.LOGIN);
					stages[ct] = transStage;
					ct++;
					break;
				case DEL_ACC:
					Stage delAcctStage = new Stage();
					delAcctStage.setMaximized(false);
					delAcctStage.initStyle(StageStyle.UNDECORATED);
					delAcctStage.setUserData(Stages.DEL_ACC);
					stages[ct] = delAcctStage;
					ct++;
					break;
					
				case NEW_ACC:
					Stage newAccStage = new Stage();
					newAccStage.setMaximized(false);
					newAccStage.initStyle(StageStyle.UNDECORATED);
					newAccStage.setUserData(Stages.NEW_ACC);
					stages[ct] = newAccStage;
					ct++;
					break;
			}
	}
	
	// Loads FXMLs and adds their Panes and controllers to the respective arrays
	private void initViewsAndControllers() {
		views = new Pane[Views.values().length];
		controllers = new Controller[Views.values().length];
		int ct = 0;
		for (Views view: Views.values()) {
			FXMLLoader loader = new FXMLLoader();
            loader.setLocation(
            		System.class.getResource(view.val)
            );
			Pane main = null;
			try {
				main = (Pane) loader.load();
			} catch (IOException e) {
				e.printStackTrace();
			}
			main.setUserData(view);
			views[ct] = main;
			controllers[ct] = loader.<Controller>getController();
			controllers[ct].setMainApp(this);
			ct++;
		}
	}
	
/*-----------------------------------------------------------------------------*/
	
	/**
	 * Returns the {@code Stage} with the specified {@code stageID}.
	 */
	public Stage getStage(Stages stageID) {
		for (Stage s: stages)
			if (s.getUserData().equals(stageID))
				return s;
		return null;
	}
	
	/**
	 * Passes data to the specified view's controller.
	 */
	public void sendData(Views viewID, Object... data) {
		controllers[viewID.ordinal()].receiveData(data);
	}
	
	/**
	 * Shows the specified view on the given stage.
	 * @param stageID - the stage to show the view in
	 * @param viewID - the view to show
	 * @param data - any data to pass to the new view's controller (must conform
	 * to the controller's specifications)
	 * 
	 */
	public void show(Stages stageID, Views viewID) {
		Stage stg = stages[stageID.ordinal()];
		
		if (viewNeedsSwitching(stg.getScene(), views[viewID.ordinal()]))
			stg.setScene(new Scene(views[viewID.ordinal()]));
		
		stg.show();
	}
	
	/* Returns true if no scene set...
	 * OR
	 * there is a scene set who's view isn't the view
	 * to switch to
	 */
	private boolean viewNeedsSwitching(Scene scene, Pane view) {
		return scene == null ||
				(scene != null && !scene.getRoot().equals(view));
	}
	
	/**
	 * Closes the specified stage.
	 */
	public void close(Stages stageID) {
		stages[stageID.ordinal()].close();
	}
	
}
