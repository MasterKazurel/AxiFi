package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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
		setupLogging("logs.txt");
		manager = new Manager(primaryStage);
        manager.show(Stages.LOGIN, Views.LOGIN);
	}
	
	private void setupLogging(String fileName) {
		try {
			File f = new File(fileName);
			if (!f.exists()) f.createNewFile();
			CopyPrintStream cpStream = new CopyPrintStream(new FileOutputStream(f, false), System.out);
			System.setOut(cpStream);
			System.setErr(cpStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
