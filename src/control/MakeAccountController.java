package control;

import java.net.URL;
import java.util.ResourceBundle;

import application.Manager.Stages;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.CsAdmin;
import model.Profile;

public class MakeAccountController extends Controller {
	@FXML Label nameLbl, fundsLbl;
	@FXML TextField nameFld, fundsFld;
	
	private CsAdmin admin;
	
	@FXML
	private void cancel() {
		manager.close(Stages.NEW_ACC);
	}
	
	@FXML
	private void submit() {
		if (new Validation<TextField>(nameLbl, nameFld, 
				fld -> admin.getUsers().stream().anyMatch(user -> user.getFullName().equalsIgnoreCase(fld.getText())), 
				"Account already exists. ").test()) {
			Profile acc = new Profile(nameFld.getText(), "test");
			jkit.writeProfile(acc.getFullName());
			admin.getUsers().add(acc);
			manager.close(Stages.NEW_ACC);
		}
	}

	@Override
	public void receiveData(Object... data) {
		admin = (CsAdmin) data[0];
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void setupValidation() {
		// TODO Auto-generated method stub
		
	}

}
