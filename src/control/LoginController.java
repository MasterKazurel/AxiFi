package control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import main.MainApp.Views;

public class LoginController extends Controller {
	@FXML TextField usernameFld, passwordFld;
	@FXML Button loginBtn;
	
	@FXML
	private void login(ActionEvent e) {
		mainApp.show(Views.MAIN);
		mainApp.close(Views.LOGIN);
	}

	@Override
	public void receiveData(Object... data) {
		throw new UnsupportedOperationException();
	}

}
