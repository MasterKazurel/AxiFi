package control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import main.MainApp.Views;

public class LoginController extends Controller {
	@FXML TextField usernameFld, passwordFld;
	@FXML Button loginBtn;
	@FXML ImageView logoImgView;
	
	@FXML private void handleLogin(ActionEvent e) {
		mainApp.show(Views.MAIN);
	}

}
