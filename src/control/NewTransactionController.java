package control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import main.MainApp.Views;

public class NewTransactionController extends Controller {
	@FXML private MenuButton transTypeMenu;
		@FXML private MenuItem depositItm, withdrawItm;
	@FXML private MenuButton paymentMenu;
		@FXML private MenuItem creditItm, checkItm;
	@FXML private Label amountLbl, methodLbl, 
				actualAmountLbl, actualAmountValLbl,
				amountHeldLbl, amountHeldValLbl;
	@FXML private TextField amountFld;
	@FXML private Button submitBtn;
	
	@FXML private void handleAction(ActionEvent e) {
		Object src = e.getSource();
		if (src.equals(depositItm)) {
			transTypeMenu.setText(depositItm.getText());
			showTransDetails(true);
			paymentMenu.setVisible(true);
			methodLbl.setVisible(true);
			actualAmountLbl.setText("Amount being deposited: ");
		}
		else if (src.equals(withdrawItm)) {
			transTypeMenu.setText(withdrawItm.getText());
			showTransDetails(true);
			paymentMenu.setVisible(false);
			methodLbl.setVisible(false);
			actualAmountLbl.setText("Amount being withdrawn: ");
		}
		else if (src.equals(creditItm)) {
			paymentMenu.setText(creditItm.getText());
		}
		else if (src.equals(checkItm)) {
			paymentMenu.setText(checkItm.getText());
		}
		else if (src.equals(submitBtn)) {
			mainApp.show(Views.MAIN);
		}
	}
	
	/**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
	@FXML
	public void initialize() {
		showTransDetails(false);
	}
	
	private void showTransDetails(boolean show) {
		actualAmountLbl.setVisible(show);
		actualAmountValLbl.setVisible(show);
		amountHeldLbl.setVisible(show);
		amountHeldValLbl.setVisible(show);
	}
}
