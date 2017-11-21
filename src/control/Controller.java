package control;

import application.Manager;
import application.Manager.Stages;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import model.JsonKit;

public abstract class Controller implements Initializable {
	protected Manager manager;
	protected JsonKit jkit;
	private double dx, dy;
	private Object userData;
	protected Modes mode;
	protected Validation<?>[] validations;
	
	protected enum Modes {
		NEW,
		EDIT;
	}
	
	public Controller() {
		jkit = new JsonKit();
	}
	
	public void setManager(Manager main)
	{ this.manager = main; }
	
	public void setUserData(Object obj)
	{ this.userData = obj; }
	
	public Object getUserData()
	{ return userData; }
	
	protected void setupStageDrag(Node n, Stages stageID) {
		n.setOnMousePressed(e -> {
			dx = manager.getStage(stageID).getX() - e.getScreenX();
			dy = manager.getStage(stageID).getY() - e.getScreenY();
		});
		n.setOnMouseDragged(e -> {
			manager.getStage(stageID).setX(e.getScreenX() + dx);
			manager.getStage(stageID).setY(e.getScreenY() + dy);
		});
	}
	
	protected abstract void setupValidation();
	public abstract void receiveData(Object... data);
}
