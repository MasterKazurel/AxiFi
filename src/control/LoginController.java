package control;

import application.Main.Stages;
import application.Main.Views;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class LoginController extends Controller {
	@FXML AnchorPane root;
	@FXML TextField usernameFld, passwordFld;
	@FXML Button loginBtn;
	
	@FXML
	public void initialize() {
		// TODO Auto-generated method stub
		
	}
	
	@FXML
	private void login(ActionEvent e) {
		main.show(Stages.MAIN, Views.MAIN);
		main.sendData(Views.MAIN, usernameFld.getText(), passwordFld.getText());
		main.close(Stages.LOGIN);
	}

	@Override
	public void receiveData(Object... data) {
		throw new UnsupportedOperationException();
	}

	
}
