package control;

import main.MainApp;

public abstract class Controller {
	protected MainApp mainApp;
	
	public void setMainApp(MainApp mainApp)
	{ this.mainApp = mainApp; }
}
