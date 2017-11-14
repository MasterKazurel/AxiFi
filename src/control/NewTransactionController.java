package control;

import application.Main.Stages;
import application.Main.Views;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.Transaction;

public class NewTransactionController extends Controller {
	@FXML private AnchorPane root;
	@FXML private MenuButton transTypeMenu;
		@FXML private MenuItem depositItm, withdrawItm;
	@FXML private MenuButton paymentMenu;
		@FXML private MenuItem creditItm, checkItm;
	@FXML private Label amountLbl, methodLbl, 
				actualAmountLbl, actualAmountValLbl,
				amountHeldLbl, amountHeldValLbl;
	@FXML private TextField amountFld;
	@FXML private Button submitBtn;
	
	@FXML
	public void initialize() {
		show(false, actualAmountLbl, actualAmountValLbl, 
					amountHeldLbl, amountHeldValLbl,
					paymentMenu, methodLbl);
	}
	
	@FXML
	private void switchTransType(ActionEvent e) {
		Object src = e.getSource();
		if (src.equals(depositItm)) {
			transTypeMenu.setText(depositItm.getText());
			show(true, actualAmountLbl, actualAmountValLbl, 
					amountHeldLbl, amountHeldValLbl,
					paymentMenu, methodLbl);
			actualAmountLbl.setText("Amount being deposited: ");
		}
		else if (src.equals(withdrawItm)) {
			transTypeMenu.setText(withdrawItm.getText());
			show(true, actualAmountLbl, actualAmountValLbl);
			show(false, paymentMenu, methodLbl,
					amountHeldLbl, amountHeldValLbl);
			actualAmountLbl.setText("Amount being withdrawn: ");
		}
	}
	
	@FXML
	private void switchPayment(ActionEvent e) {
		Object src = e.getSource();
		if (src.equals(creditItm)) {
			paymentMenu.setText(creditItm.getText());
		}
		else if (src.equals(checkItm)) {
			paymentMenu.setText(checkItm.getText());
		}
	}
	
	@FXML
	private void submit(ActionEvent e) {
		main.sendData(Views.MAIN, new Transaction("10/01/17", "100.0", "shoes"));
		main.close(Stages.NEW_TRANS);
	}
	
	private void show(boolean show, Node... nodes) {
		for (Node n: nodes)
			n.setVisible(show);
	}

	@Override
	public void receiveData(Object... data) {
		throw new UnsupportedOperationException();
	}
}
