package control;

import application.Main;
import javafx.fxml.FXML;
import model.JsonKit;

public abstract class Controller {
	protected Main main;
	protected JsonKit jkit;
	private Object userData;
	
	public Controller() {
		jkit = new JsonKit();
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
