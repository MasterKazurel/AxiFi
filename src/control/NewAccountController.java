package control;

import java.net.URL;
import java.util.ResourceBundle;

import application.Manager.Stages;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import model.CsAdmin;
import model.Profile;

public class NewAccountController extends Controller {
	
	@FXML AnchorPane root;
	@FXML Label nameLbl, fundsLbl;
	@FXML HBox titleBox;
	@FXML TextField nameFld, fundsFld;
	
	private CsAdmin admin;
	
/*--- SETUP ---------------------------------------------------------------------------*/
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setupStageDrag(titleBox, Stages.NEW_ACC);
		setupStageClose(root, Stages.NEW_ACC);
		setupFadeIn(root);
	}
	
	@Override
	public void receiveData(Object... data) {
		admin = (CsAdmin) data[0];
	}
	
/*--- FXML ---------------------------------------------------------------------------*/
	
	@FXML
	private void exit() {
		manager.close(Stages.NEW_ACC);
		setupStageDrag(titleBox, Stages.NEW_ACC);
	}
	
	@FXML
	private void submit() {
		if (new Validation<TextField>(nameLbl, nameFld, 
				fld -> admin.getUsers().stream().anyMatch(user -> user.getFullName().equalsIgnoreCase(fld.getText())), 
				"Account already exists. ").test()) {
			Profile acc = new Profile(nameFld.getText(), "test");
			db.insertProfile(acc);
			admin.getUsers().add(acc);
			manager.close(Stages.NEW_ACC);
		}
	}

}
