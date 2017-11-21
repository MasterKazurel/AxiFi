package control;

import java.net.URL;
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
import javafx.scene.control.TableView;
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
	
	private TableView<Transaction> table;
	private Transaction trans;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		show(false, actualAmountLbl, actualAmountValLbl, 
					amountHeldLbl, amountHeldValLbl,
					paymentMenu, methodLbl, msgLbl);
		root.sceneProperty().addListener((obs, oldScene, newScene) -> {
			if (newScene != null) {
				Animations.fadeIn(root);
				delBtn.setVisible(false);
				mode = Modes.NEW;
				submitBtn.setText("Submit");
			}
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
		Validation.setupOnFocusLost(validations);
	}
	
	@Override
	public void receiveData(Object... data) {
		if (data[0] != null && data[0] instanceof TableView) {
			mode = Modes.EDIT;
			table = (TableView<Transaction>) data[0];
			trans = table.getSelectionModel().getSelectedItem();
			if (trans != null) {
				delBtn.setVisible(true);
				submitBtn.setText("Update");
			}
			else manager.close(Stages.TRANS);
		}
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
		if (Validation.run(validations)) {
			switch (mode) {
				case NEW:
					manager.sendData(Views.MAIN, new Transaction(datePicker.getValue(), 
							Double.parseDouble(amountFld.getText()), 
							descFld.getText()));
					break;
				case EDIT:
					trans = new Transaction(datePicker.getValue(), 
							Double.parseDouble(amountFld.getText()), 
							descFld.getText());
					break;
			}
			manager.close(Stages.TRANS);
		}
	}
	
	@FXML
	private void delete(ActionEvent e) {
		table.getItems().remove(trans);
		manager.close(Stages.TRANS);
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
	
}
