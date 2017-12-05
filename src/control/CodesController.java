package control;

import java.net.URL;
import java.util.ResourceBundle;

import application.Manager.Stages;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class CodesController extends Controller {
	
	@FXML private AnchorPane root;
	@FXML private HBox titleBox;
	@FXML private Button closeBtn;
	
/*--- SETUP ---------------------------------------------------------------------------*/
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setupStageDrag(titleBox, Stages.CODES);
		setupStageClose(root, Stages.CODES);
		setupFadeIn(root);
	}
	
	@Override
	public void receiveData(Object... data) {
		// TODO Auto-generated method stub
		
	}

/*--- FXML ---------------------------------------------------------------------------*/
	
	@FXML
	public void exit() {
		manager.close(Stages.CODES);
	}

}
