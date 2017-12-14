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
	
/*--- SETUP ---------------------------------------------------------------------------*/
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setupStageDrag(titleBox, Stages.LOGIN);
		setupOnShow(root, (obs, oldValue, newValue) -> {
			if (newValue != null) usernameFld.requestFocus();
		});
	}
	
	@Override
	public void receiveData(Object... data) {
		throw new UnsupportedOperationException();
	}
	
/*--- HELPERS ---------------------------------------------------------------------------*/	
	
	private CsAdmin authenticate() {
		CsAdmin admin = db.getAdmin();
		validations = new Validation<?>[] {
			new Validation<TextField>(loginLbl, usernameFld, fld -> !admin.getLogin().equals(fld.getText()), "Invalid login."),
			new Validation<TextField>(loginLbl, passwordFld, fld -> !admin.getPassword().equals(fld.getText()), "Invalid login.")
		};
		if (Validation.run(validations))
			return admin;
		else {
			Animations.shake(manager.getStage(Stages.LOGIN));
			return null;
		}
	}
	
/*--- FXML ---------------------------------------------------------------------------*/
	
	@FXML
	private void login(ActionEvent e) {
		CsAdmin admin = authenticate();
		if (admin != null)
			manager.showClose(Stages.MAIN, Views.MAIN, Stages.LOGIN);
	}
	
	@FXML
	private void exit(ActionEvent e) {
		System.exit(0);
	}
	
	@FXML
	private void minimize(ActionEvent e) {
		manager.getStage(Stages.LOGIN).setIconified(true);
	}
	
}
