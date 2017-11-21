package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import control.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Manager {

	private ObservableList<Controller> controllers;
	private List<Controller> controllersBack;
	private ObservableList<Stage> stages;
	private List<Stage> stagesBack;
	private ObservableList<Pane> views;
	private List<Pane> viewsBack;
	
	private Styles currentStyle;
	
	public enum Styles {
		DARK("DarkTheme.css"),
		LIGHT("LightTheme.css");
		
		private String val;

		private Styles(String val) {
			this.val = "/view/" + val;
		}
	}

	public enum Stages {
		MAIN,
		LOGIN, 
		TRANS, 
		DEL_ACC;
	}

	public enum Views {
		LOGIN("Login.fxml"), 
		MAIN("Main.fxml"), 
		TRANS("Trans.fxml"), 
		DEL_ACC("DeleteAcct.fxml");
		private String val;

		private Views(String val) {
			this.val = "/view/" + val;
		}
	}

/*----------------------------------------------------------------------------------------------------*/

	public Manager(Stage primaryStage) {
		currentStyle = Styles.DARK;
		stagesBack = new ArrayList<Stage>();
		stages = FXCollections.observableList(stagesBack);
		viewsBack = new ArrayList<Pane>();
		views = FXCollections.observableList(viewsBack);
		controllersBack = new ArrayList<Controller>();
		controllers = FXCollections.observableList(controllersBack);
		addStages(primaryStage);
	}
	
	private void addStages(Stage mainStage) {
		for (Stages s : Stages.values())
			switch (s) {
				case MAIN:
					mainStage.setTitle("AxiFi");
					mainStage.setUserData(Stages.MAIN);
					mainStage.setMaximized(true);
					stages.add(mainStage);
					break;
				case LOGIN:
					Stage loginStage = new Stage();
					loginStage.setMaximized(false);
					loginStage.initStyle(StageStyle.TRANSPARENT);
					loginStage.setUserData(Stages.LOGIN);
					loginStage.setResizable(false);
					loginStage.requestFocus();
					loginStage.sizeToScene();
					stages.add(loginStage);
					break;
				case TRANS:
					Stage transStage = new Stage();
					transStage.setMaximized(false);
					transStage.initStyle(StageStyle.TRANSPARENT);
					transStage.sceneProperty().addListener((obs, oldValue, newValue) -> {
						if (newValue != null) transStage.getScene().setFill(Color.TRANSPARENT);
					});
					transStage.sizeToScene();
					transStage.setUserData(Stages.TRANS);
					transStage.initOwner(mainStage);
					stages.add(transStage);
					break;
				case DEL_ACC:
					Stage delAcctStage = new Stage();
					delAcctStage.setMaximized(false);
					delAcctStage.initStyle(StageStyle.TRANSPARENT);
					delAcctStage.setUserData(Stages.DEL_ACC);
					delAcctStage.sizeToScene();
					delAcctStage.initOwner(mainStage);
					stages.add(delAcctStage);
					break;
			}
	}

/*----------------------------------------------------------------------------------------------------*/
		
	/**
	 * Passes data to the specified view's controller.
	 */
	public void sendData(Views viewID, Object... data) {
		getController(viewID).receiveData(data);
	}

	/**
	 * Shows the specified view on the given stage.
	 * 
	 * @param stageID
	 *            - the stage to show the view in
	 * @param viewID
	 *            - the view to show
	 * @param reload
	 *            - whether to reload the view
	 * 
	 * 
	 */
	public void show(Stages stageID, Views viewID, boolean reload) {
		Stage stg = getStage(stageID);
		
		Pane view = getView(viewID);
		view.setVisible(false);
		if (reload || view == null)
			loadView(viewID);
		if (stg.getScene() == null)
			stg.setScene(new Scene(view));
		else
			stg.getScene().setRoot(view);
		
		view.setVisible(true);
		view.getStylesheets().clear();
		view.getStylesheets().add(currentStyle.val);
		stg.show();
	}
	
	/**
	 * Shows the specified view (and reloads it) on the given stage.
	 * 
	 * @param stageID
	 *            - the stage to show the view in
	 * @param viewID
	 *            - the view to show
	 * 
	 * 
	 */
	public void show(Stages stageID, Views viewID) {
		Stage stg = getStage(stageID);

		Pane view = loadView(viewID);
		if (stg.getScene() == null)
			stg.setScene(new Scene(view));
		else
			stg.getScene().setRoot(view);
			
		view.getStylesheets().clear();
		view.getStylesheets().add(currentStyle.val);
		stg.show();
	}

	/**
	 * Closes the specified stage.
	 */
	public void close(Stages stageID) {
		getStage(stageID).close();
	}
	
	/**
	 * Switches the css stylesheet of all views to the specified css {@code fileName}.
	 */
	public void setStyle(Styles stylesheet) {
		currentStyle = stylesheet;
	}

/*----------------------------------------------------------------------------------------------------*/

	/**
	 * Loads the specified view.
	 */
	private Pane loadView(Views viewID) {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(System.class.getResource(viewID.val));
		Pane view = null;
		try {
			view = (Pane) loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		view.setUserData(viewID);
		addView(view);

		Controller controller = loader.<Controller>getController();
		controller.setUserData(viewID);
		controller.setManager(this);
		addController(controller);
		return view;
	}

	/*
	 * Returns true if no scene set... OR there is a scene set who's view isn't the
	 * view to switch to
	 */
	private boolean viewNeedsSwitching(Scene scene, Pane view) {
		return scene == null || (scene != null && !scene.getRoot().equals(view));
	}

	/*----------------------------------------------------------------------------------------------------*/

	/**
	 * Returns the {@code Stage} with the specified {@code stageID}.
	 */
	public Stage getStage(Stages stageID) {
		for (Stage s : stages)
			if (s.getUserData().equals(stageID))
				return s;
		return null;
	}

	private void addStage(Stage stage) {
		Stage s = getStage((Stages) stage.getUserData());
		if (s != null)
			stages.remove(s);
		stages.add(stage);
	}

	private void setStage(Stages stageID, Stage stage) {
		Stage stg = getStage(stageID);
		if (stg != null)
			stg = stage;
	}

	private void removeStage(Stages stageID) {
		stages.remove(getStage(stageID));
	}

	/**
	 * Returns the {@code Controller} of the specified {@code viewID}.
	 */
	public Pane getView(Views viewID) {
		for (Pane s : views)
			if (s.getUserData().equals(viewID))
				return s;
		return null;
	}

	private void addView(Pane view) {
		Pane v = getView((Views) view.getUserData());
		if (v != null)
			views.remove(v);
		views.add(view);
	}

	private void setView(Views viewID, Pane view) {
		Pane p = getView(viewID);
		if (p != null)
			p = view;
	}

	private void removeView(Views viewID) {
		views.remove(getView(viewID));
	}

	/**
	 * Returns the {@code Controller} of the specified {@code viewID}.
	 */
	public Controller getController(Views viewID) {
		for (Controller s : controllers)
			if (s.getUserData().equals(viewID))
				return s;
		return null;
	}

	private void addController(Controller controller) {
		Controller c = getController((Views) controller.getUserData());
		if (c != null)
			controllers.remove(c);
		controllers.add(controller);
	}

	private void setController(Views viewID, Controller controller) {
		Controller ctrl = getController(viewID);
		if (ctrl != null)
			ctrl = controller;
	}

	private void removeController(Views viewID) {
		controllers.remove(getController(viewID));
	}
}
