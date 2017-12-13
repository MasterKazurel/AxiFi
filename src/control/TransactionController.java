package control;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.ResourceBundle;

import application.Manager.Stages;
import application.Manager.Views;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import model.Profile;
import model.Transaction;

public class TransactionController extends Controller {
	
	@FXML private AnchorPane root;
	@FXML private HBox titleBox;
	@FXML private MenuButton transTypeMenu;
		@FXML private MenuItem depositItm, withdrawItm;
	@FXML private MenuButton paymentMenu;
		@FXML private MenuItem creditItm, checkItm;
	@FXML private Label dateLbl, amountLbl, descLbl,
				codeLbl, methodLbl, 
				actualAmountLbl, actualAmountValLbl,
				amountHeldLbl, amountHeldValLbl;
	@FXML private TextField amountFld, descFld, codeFld;
	@FXML private Button submitBtn, delBtn, cancelBtn, codeBtn;
	@FXML private DatePicker datePicker;
	
	private TableViewSelectionModel<Transaction> selection;
	private Profile owner;
	private Modes mode;
	
	public enum Modes {
		NEW,
		EDIT_SINGLE,
		EDIT_MULTI;
	}
	
/*--- SETUP ---------------------------------------------------------------------------*/
	
	/*
	 * transTypeMenu, depositItm, withdrawItm,
	 * dateLbl, datePicker,
	 * amountLbl, amountFld,
	 * descLbl, descFld, 
	 * codeLbl, codeFld, codeBtn,
	 * methodLbl, paymentMenu, creditItm, checkItm,
	 * actualAmountLbl, actualAmountValLbl, amountHeldLbl, amountHeldValLbl,
	 * submitBtn, delBtn, cancelBtn;
	 */
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		enable(false, dateLbl, datePicker, amountLbl, amountFld, 
					descLbl, descFld, codeLbl, codeFld, codeBtn,
					paymentMenu, methodLbl);
		show(false, actualAmountLbl, actualAmountValLbl,
				amountHeldLbl, amountHeldValLbl);
		setMode(Modes.NEW);
		setupValidation();
		setupStageDrag(titleBox, Stages.TRANS);
		setupStageClose(root, Stages.TRANS);
		setupFadeIn(root);
		setupMasking();
	}
	
	@Override
	protected void setupValidation() {
		//add to end if adding
		validations = new Validation<?>[]{
			new Validation<DatePicker>(dateLbl, datePicker, dp -> dp.getValue() == null, "You must choose a date. "),
			new Validation<TextField>(amountLbl, amountFld, fld -> fld.getText().isEmpty(), "Amount field is empty. ")
				.add(fld -> Double.isNaN(Double.parseDouble(fld.getText())), "Amount must be a decimal amount (e.g. 19.99)."),
			new Validation<TextField>(descLbl, descFld, fld -> fld.getText().isEmpty(), "Description field is empty. ")
		};
	}
	
	@Override
	protected void setupMasking() {
		/*amountFld.textProperty().addListener((obs, oldValue, newValue) -> {
	        if (!newValue.matches("/^([+-]?(\\d+(\\.\\d*)?)|(\\.\\d+))$/"))
	            amountFld.setText(newValue.replaceAll("[^\\d]", ""));
		});*/
	}
	
	@Override
	public void receiveData(Object... data) {
		switch (data.length) {
		case 0:
			manager.close(Stages.TRANS);
			break;
		case 1:
			if (data[0] != null && data[0] instanceof Profile) {
				owner = (Profile) data[0];
				setMode(Modes.NEW);
			}
			break;
		case 2:
			if (data[0] != null && data[0] instanceof TableViewSelectionModel &&
			data[1] != null && data[1] instanceof Profile) {
				selection = (TableViewSelectionModel<Transaction>) data[0];
				owner = (Profile) data[1];
				if (selections().size() > 1)
					setMode(Modes.EDIT_MULTI);
				else if (selections().size() == 1)
					setMode(Modes.EDIT_SINGLE);
				else manager.close(Stages.TRANS);
			}
			break;
		}
	}
	
/*----- HELPERS --------------------------------------------------------------------------*/
	
	private Transaction selection() 
	{ return selection.getSelectedItem(); }
	
	private List<Transaction> selections()
	{ return selection.getSelectedItems(); }
	
	private void fillForm() {
		if (selection().getAmount() < 0)
			switchTransType(new ActionEvent(withdrawItm, null));
		else switchTransType(new ActionEvent(depositItm, null));
		
		datePicker.setValue(selection().getTime());
		amountFld.setText(selection().getAmountProperty().getValue());
		
		descFld.setText(selection().getDescription());
	}
	
	private void setMode(Modes mode) {
		this.mode = mode;
		switch (this.mode) {
			case NEW:
				submitBtn.setText("Submit");
				show(false, delBtn);
				break;
			case EDIT_SINGLE:
				show(true, delBtn);
				fillForm();
			case EDIT_MULTI:
				show(true, delBtn);
				submitBtn.setText("Update");
				break;
		}
	}
	
/*--------- FXML ------------------------------------------------------------------------*/
	
	@FXML
	private void switchTransType(ActionEvent e) {
		Object src = e.getSource();
		if (src.equals(depositItm)) {
			transTypeMenu.setText(depositItm.getText());
			enable(true, dateLbl, datePicker, amountLbl, amountFld, 
					descLbl, descFld, codeLbl, codeFld, codeBtn,
					paymentMenu, methodLbl, 
					codeLbl, codeFld, codeBtn);
			show(true, actualAmountLbl, actualAmountValLbl,
					amountHeldLbl, amountHeldValLbl);
			actualAmountLbl.setText("Amount being deposited: ");
		}
		else if (src.equals(withdrawItm)) {
			transTypeMenu.setText(withdrawItm.getText());
			enable(true, dateLbl, datePicker, amountLbl, amountFld,
					descLbl, descFld, codeLbl, codeFld, codeBtn);
			show(true, actualAmountLbl, actualAmountValLbl);
			show(false, amountHeldLbl, amountHeldValLbl);
			enable(false, paymentMenu, methodLbl,
					codeLbl, codeFld, codeBtn);
			actualAmountLbl.setText("Amount being withdrawn: ");
		}
	}
	
	@FXML
	private void switchPayment(ActionEvent e) {
		Object src = e.getSource();
		if (src.equals(creditItm)) {
			paymentMenu.setText(creditItm.getText());
			computeAmounts(src);
		}
		else if (src.equals(checkItm)) {
			paymentMenu.setText(checkItm.getText());
			computeAmounts(src);
		}
	}
	
	private void computeAmounts(Object src) {
		if (validations[1].test()) {
			double amt = 0;
			try {
				amt = Double.parseDouble(amountFld.getText());
			} catch (NumberFormatException evt) {
				evt.printStackTrace();
			}
			double fees = amt;
			if (src.equals(creditItm))
				fees *= .12;
			else if (src.equals(checkItm))
				fees *= .08;
			double actualAmt = amt - fees;
			NumberFormat dFmt = DecimalFormat.getCurrencyInstance();
			actualAmountValLbl.setText(dFmt.format(actualAmt));
			amountHeldValLbl.setText(dFmt.format(fees));
		}
	}
	
	@FXML
	private void submit(ActionEvent e) {
		switch (mode) {
			case NEW:
				if (Validation.run(validations)) {
					Transaction newTrans = new Transaction(
							owner.getId(),
							datePicker.getValue(),
							descFld.getText(),
							Double.parseDouble(amountFld.getText())
						);
					owner.getTransactions().add(newTrans);
					manager.close(Stages.TRANS);
				}
				break;
			case EDIT_SINGLE:
				if (Validation.run(validations)) {
					selection().setTime(datePicker.getValue());
					selection().setAmount(Double.parseDouble(amountFld.getText()));
					selection().setDescription(descFld.getText());
					manager.close(Stages.TRANS);
				}
				break;
			case EDIT_MULTI:
				for (Transaction tran: selections()) {
					if (validations[0].test())
						tran.setTime(datePicker.getValue());
					if (validations[1].test())
						tran.setAmount(Double.parseDouble(amountFld.getText()));
					if (validations[2].test())
						tran.setDescription(descFld.getText());
				}
				manager.close(Stages.TRANS);
				break;
		}
		
	}
	
	@FXML
	private void delete(ActionEvent e) {
		switch (mode) {
			case NEW:
				break;
			case EDIT_SINGLE:
				selection.getTableView().getItems().remove(selection());
				manager.close(Stages.TRANS);
				break;
			case EDIT_MULTI:
				selection.getTableView().getItems().removeAll(selections());
				manager.close(Stages.TRANS);
				break;
		}
	}
	
	@FXML
	private void exit(ActionEvent e) {
		manager.close(Stages.TRANS);
	}
	
	@FXML
	private void openDatePicker(ActionEvent e) {
		datePicker.show();
	}
	
	@FXML
	private void codes(ActionEvent e) {
		manager.show(Stages.CODES, Views.CODES);
	}
	
}
