package control;

import application.Main.Stages;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CodesController extends Controller{
	@FXML private Button closeBtn;
	
	@Override
	public void receiveData(Object... data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initialize() {
		
	}
	@FXML
	public void close() {
		main.close(Stages.CODES);
	}

}
