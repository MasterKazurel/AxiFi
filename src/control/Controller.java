package control;

import application.Manager;
import javafx.fxml.FXML;
import model.JsonKit;

public abstract class Controller {
	protected Manager manager;
	protected JsonKit jkit;
	private Object userData;
	
	public Controller() {
		jkit = new JsonKit();
	}
	
	public void setManager(Manager main)
	{ this.manager = main; }
	
	public void setUserData(Object obj)
	{ this.userData = obj; }
	
	public Object getUserData()
	{ return userData; }
	
	public abstract void receiveData(Object... data);
	@FXML public abstract void initialize();
}
