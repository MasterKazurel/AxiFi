package control;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Manager.Stages;
import application.Manager.Views;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import model.Transaction;
import view.Animations;

public class TransactionController extends Controller {
	@FXML private AnchorPane root;
	@FXML HBox titleBox;
	@FXML private MenuButton transTypeMenu;
		@FXML private MenuItem depositItm, withdrawItm;
	@FXML private MenuButton paymentMenu;
		@FXML private MenuItem creditItm, checkItm;
	@FXML private Label dateLbl, amountLbl, methodLbl, 
				actualAmountLbl, actualAmountValLbl,
				amountHeldLbl, amountHeldValLbl, descLbl,
				msgLbl;
	@FXML private TextField amountFld, descFld;
	@FXML private Button submitBtn, delBtn, cancelBtn;
	@FXML private DatePicker datePicker;
	
	private TableViewSelectionModel<Transaction> selection;
	private Modes mode;
	
	public enum Modes {
		NEW,
		EDIT_SINGLE,
		EDIT_MULTI;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		show(false, actualAmountLbl, actualAmountValLbl, 
					amountHeldLbl, amountHeldValLbl,
					paymentMenu, methodLbl, msgLbl);
		root.sceneProperty().addListener((obs, oldScene, newScene) -> {
			if (newScene != null)
				Animations.fadeIn(root);
		});
		amountFld.textProperty().addListener((obs, oldValue, newValue) -> {
	        if (!newValue.matches("\"[0-9]{1,13}(\\\\.[0-9]*)?\""))
	            amountFld.setText(newValue.replaceAll("[^\\d]", ""));
		});
		
		setupValidation();
		setupStageDrag(titleBox, Stages.TRANS);
	}
	
	@Override
	protected void setupValidation() {
		validations = new Validation<?>[]{
			new Validation<DatePicker>(dateLbl, datePicker, dp -> dp.getValue() == null, "You must choose a date. "),
			new Validation<TextField>(amountLbl, amountFld, fld -> fld.getText().isEmpty(), "Amount field is empty. "),
			new Validation<TextField>(descLbl, descFld, fld -> fld.getText().isEmpty(), "Description field is empty. ")
		};
	}
	
	@Override
	public void receiveData(Object... data) {
		if (data[0] != null && data[0] instanceof TableViewSelectionModel) {
			selection = (TableViewSelectionModel<Transaction>) data[0];
			if (selections().size() > 1)
				setMode(Modes.EDIT_MULTI);
			else
				setMode(Modes.EDIT_SINGLE);
		}
	}
	
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
		switch (mode) {
			case NEW:
				if (Validation.run(validations))
					manager.sendData(Views.MAIN, new Transaction(datePicker.getValue(), 
						Double.parseDouble(amountFld.getText()), 
						descFld.getText()));
				break;
			case EDIT_SINGLE:
				if (Validation.run(validations)) {
					selection().setTime(datePicker.getValue());
					selection().setAmount(Double.parseDouble(amountFld.getText()));
					selection().setDescription(descFld.getText());
				}
				break;
			case EDIT_MULTI:
				for (Transaction tran: selections()) {
					if (new Validation<DatePicker>(datePicker, dp -> dp.getValue() == null).test())
						tran.setTime(datePicker.getValue());
					if (new Validation<TextField>(amountFld, fld -> fld.getText().trim().isEmpty()).test())
						tran.setAmount(Double.parseDouble(amountFld.getText()));
					if (new Validation<TextField>(descFld, fld -> fld.getText().trim().isEmpty()).test())
						tran.setDescription(descFld.getText());
				}
				break;
		}
		selection.getTableView().refresh();
		manager.close(Stages.TRANS);
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
	
	private void show(boolean show, Node... nodes) {
		for (Node n: nodes) {
			n.setVisible(show);
			Animations.fadeIn(n);
		}
	}
	
	private void setMode(Modes mode) {
		this.mode = mode;
		switch (this.mode) {
			case NEW:
				submitBtn.setText("Submit");
				delBtn.setVisible(false);
				break;
			case EDIT_SINGLE:
				fillForm();
			case EDIT_MULTI:
				delBtn.setVisible(true);
				submitBtn.setText("Update");
				break;
		}
	}
	
}
