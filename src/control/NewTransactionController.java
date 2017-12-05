package control;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
//import java.sql.Date;
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
	@FXML
	private AnchorPane root;
	@FXML
	private MenuButton transTypeMenu;
	@FXML
	private MenuItem depositItm, withdrawItm;
	@FXML
	private MenuButton paymentMenu;
	@FXML
	private MenuItem creditItm, checkItm;
	@FXML
	private Label amountLbl, methodLbl, actualAmountLbl, actualAmountValLbl, amountHeldLbl, amountHeldValLbl;
	@FXML
	private TextField amountFld, accountFld;
	@FXML
	private Button submitBtn;

	@FXML
	public void initialize() {
		show(false, actualAmountLbl, actualAmountValLbl, amountHeldLbl, amountHeldValLbl, paymentMenu, methodLbl);
	}

	@FXML
	private void switchTransType(ActionEvent e) {
		Object src = e.getSource();
		if (src.equals(depositItm)) {
			transTypeMenu.setText(depositItm.getText());
			show(true, actualAmountLbl, actualAmountValLbl, amountHeldLbl, amountHeldValLbl, paymentMenu, methodLbl);
			actualAmountLbl.setText("Amount being deposited: ");
		} else if (src.equals(withdrawItm)) {
			transTypeMenu.setText(withdrawItm.getText());
			show(true, actualAmountLbl, actualAmountValLbl);
			show(false, paymentMenu, methodLbl, amountHeldLbl, amountHeldValLbl);
			actualAmountLbl.setText("Amount being withdrawn: ");
		}
	}

	@FXML
	private void switchPayment(ActionEvent e) {
		Object src = e.getSource();
		if (src.equals(creditItm)) {
			paymentMenu.setText(creditItm.getText());
			if (amountFld.getText() != "") {
				Double amt = Double.parseDouble(amountFld.getText());
				Double actualAmt = amt - (amt * .12);
				actualAmountValLbl.setText("$" + actualAmt.toString());
			}
			if (accountFld.getText()!= "") {
				Double amt = Double.parseDouble(amountFld.getText());
				Double actualAmt = (amt * .12);
				amountHeldValLbl.setText("$" + actualAmt.toString());
			}

		} else if (src.equals(checkItm)) {
			paymentMenu.setText(checkItm.getText());
			if (amountFld.getText() != "") {
				Double amt = Double.parseDouble(amountFld.getText());
				Double actualAmt = amt - (amt * .08);
				actualAmountValLbl.setText("$" + actualAmt.toString());
			}
			if (accountFld.getText()!= "") {
				Double amt = Double.parseDouble(amountFld.getText());
				Double actualAmt = (amt * .08);
				amountHeldValLbl.setText("$" + actualAmt.toString());
			}
		}
	}

	@FXML
	private void updateAmt(ActionEvent e) {
		Object src = e.getSource();
		if (src.equals(creditItm)) {
			Double amt = Double.parseDouble(amountFld.getText());
			Double actualAmt = amt - (amt * .12);
			actualAmountValLbl.setText(actualAmt.toString());
		}
	}

	@FXML
	private void submit(ActionEvent e) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate localDate = LocalDate.now();

		if (accountFld.getText() == null)
			accountFld.setText("You must enter an account code!");
		if (amountFld.getText() == null)
			amountFld.setText("You must enter an amount!");
		else {
			main.sendData(Views.MAIN,
					new Transaction(dtf.format(localDate), accountFld.getText(), amountFld.getText()));
			main.close(Stages.NEW_TRANS);
		}
	}

	@FXML
	private void codes(ActionEvent e) {
		main.show(Stages.CODES, Views.CODES);
	}

	private void show(boolean show, Node... nodes) {
		for (Node n : nodes)
			n.setVisible(show);
	}

	public void close() {
		main.close(Stages.NEW_TRANS);
	}

	@Override
	public void receiveData(Object... data) {
		throw new UnsupportedOperationException();
	}
}
