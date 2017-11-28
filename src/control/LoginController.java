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
		
	}
	
	@FXML
	private void login(ActionEvent e) {
		CsAdmin admin = authenticate();
		if (admin != null)
			manager.showSendDateClose(Stages.MAIN, Views.MAIN, Stages.LOGIN, admin);
	}
	
	@FXML
	private void exit(ActionEvent e) {
		System.exit(0);
	}
	
	@FXML
	private void minimize(ActionEvent e) {
		manager.getStage(Stages.LOGIN).setIconified(true);
	}
	
	private CsAdmin authenticate() {
		CsAdmin admin = jkit.openAdmin(usernameFld.getText());
		validations = new Validation<?>[] {
			new Validation<TextField>(loginLbl, usernameFld, fld -> admin == null || 
					!fld.getText().equals(admin.getLogin()), "Invalid login."),
			new Validation<TextField>(loginLbl, passwordFld, fld -> admin == null ||
					!passwordFld.getText().equals(admin.getPassword()), "Invalid login.")
		};
		if (Validation.run(validations))
			return admin;
		else {
			Animations.shake(manager.getStage(Stages.LOGIN));
			return null;
		}
	}

	@Override
	public void receiveData(Object... data) {
		throw new UnsupportedOperationException();
	}

	
}
