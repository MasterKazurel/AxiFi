package control;

import application.Manager;
import application.Manager.Stages;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import model.JsonKit;
import view.Animations;

public abstract class Controller implements Initializable {
	
	protected Manager manager;
	protected JsonKit jkit;
	private double dx, dy;
	private Object userData;
	protected Validation<?>[] validations;
	
/*--- PUBLIC ---------------------------------------------------------------------------*/
	
	public Controller() {
		jkit = new JsonKit();
	}
	
	public void setManager(Manager main)
	{ this.manager = main; }
	
	/**Convenience method.
	 * @param obj - data to store
	 */
	public void setUserData(Object obj)
	{ this.userData = obj; }
	
	/**Gets data previously set with {@link #setUserData(Object)}.
	 * @return userData
	 */
	public Object getUserData()
	{ return userData; }
	
	
/*--- SETUP HELPERS ---------------------------------------------------------------------------*/
	
	protected void setupValidation() {}
	protected void setupMasking() {} // For limiting user input
	
	/** Fades in the new scene when the {@code root}'s
	 * sceneProperty changes.
	 * 
	 * @param root
	 */
	protected void setupFadeIn(Pane root) {
		root.sceneProperty().addListener((obs, oldScene, newScene) -> {
			if (newScene != null)
				Animations.fadeIn(newScene.getRoot());
		});
	}
	
	/**
	 * Sets up dragging the specificed {@code stage} with the mouse cursor
	 * when the user clicks on the specified {@code node}. 
	 * 
	 * @param node - the node to click on
	 * @param stageID - the stage to drag
	 */
	protected void setupStageDrag(Node node, Stages stageID) {
		node.setOnMousePressed(e -> {
			dx = manager.getStage(stageID).getX() - e.getScreenX();
			dy = manager.getStage(stageID).getY() - e.getScreenY();
		});
		node.setOnMouseDragged(e -> {
			manager.getStage(stageID).setX(e.getScreenX() + dx);
			manager.getStage(stageID).setY(e.getScreenY() + dy);
		});
	}
	
	/**
	 * Closes the specified {@code stage} when the user presses (KeyCode) ESCAPE.
	 * @param root - the pane to listen keys for on
	 * @param stageID - the stage to close
	 */
	protected void setupStageClose(Pane root, Stages stageID) {
		setupStageClose(root, stageID, KeyCode.ESCAPE);
	}
	
	/**
	 * Closes the specified {@code stage} when the user presses
	 * the specified {@code keyCode}.
	 * @param root - the pane to listen keys for on
	 * @param stageID - the stage to close
	 */
	protected void setupStageClose(Pane root, Stages stageID, KeyCode keyCode) {
		root.setOnKeyPressed(keyEvt -> {
			if (keyEvt.getCode().equals(keyCode))
				manager.close(stageID);
		});
	}
	
/*--- ABSTRACT ---------------------------------------------------------------------------*/
	
	public abstract void receiveData(Object... data);
}
