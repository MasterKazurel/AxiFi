package control;

import java.net.URL;
import java.util.ResourceBundle;

import application.Manager.Stages;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class CodesController extends Controller{
	@FXML private AnchorPane root;
	@FXML private Button closeBtn;
	
	@Override
	public void receiveData(Object... data) {
		// TODO Auto-generated method stub
		
	}
	
	@FXML
	public void close() {
		manager.close(Stages.CODES);
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
