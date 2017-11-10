package control;

import application.Main;
import javafx.fxml.FXML;

public abstract class Controller {
	protected Main main;
	private Object userData;
	
	public void setMainApp(Main main)
	{ this.main = main; }
	
	public void setUserData(Object obj)
	{ this.userData = obj; }
	
	public Object getUserData()
	{ return userData; }
	
	public abstract void receiveData(Object... data);
	@FXML public abstract void initialize();
}
