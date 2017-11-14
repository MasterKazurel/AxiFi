package application;

import application.Manager.Stages;
import application.Manager.Views;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	private Manager manager;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		manager = new Manager(primaryStage);
        manager.show(Stages.LOGIN, Views.LOGIN);
	}

}
