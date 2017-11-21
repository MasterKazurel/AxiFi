package control;

import java.net.URL;
import java.util.ResourceBundle;

import application.Manager.Stages;
import application.Manager.Views;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import model.CsAdmin;
import view.Animations;

public class LoginController extends Controller {
	@FXML AnchorPane root;
	@FXML HBox titleBox;
	@FXML Label loginLbl;
	@FXML TextField usernameFld, passwordFld;
	@FXML Button loginBtn, minBtn, exitBtn;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		root.sceneProperty().addListener((obs, oldValue, newValue) -> {
			if (newValue != null) usernameFld.requestFocus();
		});
		setupStageDrag(titleBox, Stages.LOGIN);
	}
	
	@Override
	protected void setupValidation() {
		// TODO Auto-generated method stub
		
	}
	
	@FXML
	private void login(ActionEvent e) {
		CsAdmin admin = authenticate(usernameFld.getText(), passwordFld.getText());
		if (admin != null) {
			manager.show(Stages.MAIN, Views.MAIN);
			manager.sendData(Views.MAIN, admin);
			manager.close(Stages.LOGIN);
		} else showError("Invalid Login. ");
	}
	
	@FXML
	private void exit(ActionEvent e) {
		System.exit(0);
	}
	
	@FXML
	private void minimize(ActionEvent e) {
		manager.getStage(Stages.LOGIN).setIconified(true);
	}
	
	private CsAdmin authenticate(String username, String password) {
		boolean secPass = db.adminLogin(username, password); //If it's true things were correct
		CsAdmin admin = db.queryAdmin(username, password);
		if (secPass) {
			return admin;
		}
		else if (admin == null) {
			return admin;
		}
		else {
			return null;
		}
	}
	
	private void showError(String msg) {
		loginLbl.setText(msg);
		loginLbl.setStyle("-fx-text-fill: red; " + loginLbl.getStyle());
		Animations.shake(loginLbl);
	}

	@Override
	public void receiveData(Object... data) {
		throw new UnsupportedOperationException();
	}

	
}
