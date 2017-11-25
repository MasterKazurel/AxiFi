package control;

import application.Main.Stages;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.CsAdmin;
import model.Profile;

public class DeleteAccountController extends Controller {
	@FXML Label promptLbl;
	@FXML TextField adminPwFld;
	@FXML Button submitBtn, cancelBtn;
	private CsAdmin admin;
	private Profile acc;
	
	@Override
	public void initialize() {
		
	}
	
	@FXML
	private void cancel() {
		main.close(Stages.DEL_ACC);
	}
	
	@FXML
	private void submit() {
		if (admin.getPassword().equals(adminPwFld.getText())) {
			jkit.deleteProfile(acc.getFirstName());
			admin.getUsers().remove(acc);
			main.close(Stages.DEL_ACC);
		}
		else {
			promptLbl.setText("Invalid password. ");
			Animations.shake(promptLbl);
		}
	}

	@Override
	public void receiveData(Object... data) {
		// TODO Auto-generated method stub
		admin = (CsAdmin) data[0];
		acc = (Profile) data[1];
		System.out.println(acc);
		promptLbl.setText("Are you sure you want to delete " + acc.getFullName() + "'s account? (Warning: all account data will be lost!)");
	}

}