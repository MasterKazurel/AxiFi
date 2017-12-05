package control;

import application.Main.Stages;
import application.Main.Views;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import model.CsAdmin;

public class LoginController extends Controller {
	@FXML AnchorPane root;
	@FXML Label loginLbl;
	@FXML TextField usernameFld, passwordFld;
	@FXML Button loginBtn;
	
	@FXML
	public void initialize() {
		
	}
	
	@FXML
	private void login(ActionEvent e) {
		CsAdmin admin = authenticate(usernameFld.getText(), passwordFld.getText());
		if (admin != null) {
			main.sendData(Views.MAIN, admin);
			main.show(Stages.MAIN, Views.MAIN);
			main.close(Stages.LOGIN);
		} else {
			showError("Invalid Login. ");
			Animations.shake(loginLbl);
		}
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
	}

	@Override
	public void receiveData(Object... data) {
		throw new UnsupportedOperationException();
	}

	
}
