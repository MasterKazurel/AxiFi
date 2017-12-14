package control;

import java.net.URL;
import java.util.ResourceBundle;

import application.Manager.Stages;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import model.CsAdmin;
import model.Profile;

public class AccountController extends Controller {
	
	@FXML private AnchorPane root;
	@FXML private Label firstNameLbl, lastNameLbl, fundsLbl, balanceLbl, balanceValLbl;
	@FXML private HBox titleBox;
	@FXML private TextField firstNameFld, lastNameFld, fundsFld;
	@FXML private Button submitBtn, cancelBtn;
	
	private CsAdmin admin;
	private Profile acc;
	
	private Mode mode;
	
	private enum Mode {
		NEW,
		EDIT;
	}
	
/*--- SETUP ---------------------------------------------------------------------------*/
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setupStageDrag(titleBox, Stages.NEW_ACC);
		setupStageClose(root, Stages.NEW_ACC);
		setupFadeIn(root);
		setMode(Mode.NEW);
	}
	
	@Override
	public void receiveData(Object... data) {
		admin = (CsAdmin) data[0];
		if (data.length > 1) {
			acc = (Profile) data[1];
			setMode(Mode.EDIT);
		}
	}
	
/*--- HELPERS ---------------------------------------------------------------------------*/
	
	private void setMode(Mode mode) {
		this.mode = mode;
		switch (mode) {
			case NEW:
				show(true, submitBtn);
				show(false, balanceLbl, balanceValLbl);
				cancelBtn.setText("Cancel");
				break;
			case EDIT:
				show(false, submitBtn);
				show(true, balanceLbl, balanceValLbl);
				cancelBtn.setText("Close");
				fillForm();
				break;
		}
	}
	
	private void fillForm() {
		firstNameFld.setText(acc.getFirstName());
		lastNameFld.setText(acc.getLastName());
		balanceValLbl.setText(acc.getFormattedBalance());
	}
	
/*--- FXML ---------------------------------------------------------------------------*/
	
	@FXML
	private void exit() {
		manager.close(Stages.NEW_ACC);
	}
	
	@FXML
	private void submit() {
		if (new Validation<TextField>(firstNameLbl, firstNameFld, 
				fld -> admin.getUsers().stream().anyMatch(user -> user.getFullName().equalsIgnoreCase(fld.getText())), 
				"Account already exists. ").test()) {
			Profile acc = new Profile(firstNameFld.getText(), lastNameFld.getText(), Double.parseDouble(fundsFld.getText()));
			admin.getUsers().add(acc);
			manager.close(Stages.NEW_ACC);
		}
	}
	
}
