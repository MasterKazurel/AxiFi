package control;

import java.io.File;

import application.Main;
import javafx.fxml.FXML;
import model.DatabaseKit;

public abstract class Controller {
	protected Main main;
	protected DatabaseKit db;
	private Object userData;
	
	private String database = "data.db";
	
	public Controller() {
		db = new DatabaseKit();
		File dbFile = new File(database);
		//If it's not there initialize a new one and build its schema
		if(dbFile.exists()) {
			db.initDatabase(database);
		}
		else {
			//Rebuild the schema
			db.initDatabase(database);
			db.buildSchema();
			db.insertAdmin("csadmin", "csci323");
		}
	}
	
	public void setMainApp(Main main)
	{ this.main = main; }
	
	public void setUserData(Object obj)
	{ this.userData = obj; }
	
	public Object getUserData()
	{ return userData; }
	
	public abstract void receiveData(Object... data);
	@FXML public abstract void initialize();
}
