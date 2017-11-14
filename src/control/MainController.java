package control;

import java.util.ArrayList;
import java.util.List;

import application.Manager.Stages;
import application.Manager.Views;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import model.CsAdmin;
import model.Profile;
import model.Testing;
import model.Transaction;

public class MainController extends Controller {
	@FXML private BorderPane root;
    @FXML private TableView<Transaction> transTable;
    
    @FXML private TableColumn<Transaction, String> dateCol, amountCol, 
    									descripCol;

    @FXML private Label firstNameLabel, lastNameLabel, streetLabel, 
    				postalCodeLabel, cityLabel, birthdayLabel;
    @FXML MenuButton accMenuBtn;
    @FXML Button newAccBtn, delAccBtn, logoutBtn, newTransBtn, codesBtn;
    
    private CsAdmin admin;
    private Profile currAcc;
    
    @Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}
    @FXML
    private void codes(ActionEvent e) {
    	main.show(Stages.CODES, Views.CODES);
    }
    
    @FXML
    private void deleteAccount(ActionEvent e) {
    	manager.show(Stages.DEL_ACC, Views.DEL_ACC);
    	manager.sendData(Views.DEL_ACC, admin, currAcc);
    }
    @FXML
    private void makeAccount(ActionEvent e) {
    	main.sendData(Views.NEW_ACC, admin, currAcc);
    	main.show(Stages.NEW_ACC, Views.NEW_ACC);
    }
    
    @FXML
    private void newTransaction(ActionEvent e) {
    	manager.show(Stages.NEW_TRANS, Views.NEW_TRANS);
    }
    
    @FXML
    private void logout(ActionEvent e) {
    	manager.show(Stages.LOGIN, Views.LOGIN);
    	manager.close(Stages.MAIN);
    }
	
	private void showAccounts() {
		admin.getUsers().addListener(new ListChangeListener<Profile>() {
			@Override
			public void onChanged(Change<? extends Profile> c) {
				while (c.next())
					if (c.wasRemoved()) {
						accMenuBtn.getItems().remove(c.getFrom());
						currAcc = admin.getUsers().get(0);
						accMenuBtn.setText(admin.getUsers().get(0).getFullName());
						updateTransactions();
					}
			}
		});
		if (accMenuBtn.getItems().isEmpty()) 
			addAccounts(admin.getUsers());
		currAcc = admin.getUsers().get(0);
		accMenuBtn.setText(admin.getUsers().get(0).getFullName());
	}
	
	private void addAccounts(List<Profile> accs) {
		// Add accounts
		List<MenuItem> items = new ArrayList<MenuItem>();
		accs.forEach(user -> {
			MenuItem item = new MenuItem();
			if (!accMenuBtn.getItems().contains(item));
			item.setText(user.getFullName());
			item.setOnAction(e -> switchAccounts(user));
			items.add(item);
			user.getTransactions().addListener(new ListChangeListener<Transaction>() {
				@Override
				public void onChanged(Change<? extends Transaction> c) {
					while (c.next())
						if (c.wasAdded())
							updateTransactions();
				}
			});
		});
		accMenuBtn.getItems().addAll(items);
	}
	
	private void updateTransactions() {
		// Add transactions
		transTable.setItems(currAcc.getTransactions());
		// Display columns
		dateCol.setCellValueFactory(cellData -> cellData.getValue().getTime());
        amountCol.setCellValueFactory(cellData -> cellData.getValue().getAmount());
        descripCol.setCellValueFactory(cellData -> cellData.getValue().getDescription());
	}
	
	private void switchAccounts(Profile user) {
    	accMenuBtn.setText(user.getFullName());
    	currAcc = user;
    	updateTransactions();
    }
	
	/**
     * Requires only one object of one of the following types:
     * Account, Transaction.
     * 
     * @throws IllegalArgumentException
     * - if the parameters do not conform to the requirements
     * above
     */
	@Override
	public void receiveData(Object... data) {
		Class<?> type = checkData(data);
		if (type.equals(CsAdmin.class)) {
			admin = (CsAdmin)data[0];
			Testing.fakeAdmin(admin); // shhhhh
			showAccounts();
			updateTransactions();
		} else if (type.equals(Transaction.class)) {
			currAcc.getTransactions().add((Transaction)data[0]);
		} else
			throw new IllegalArgumentException("Requires only one object of one of the following types: Account, Transaction.");
		
	}
	
	private Class<?> checkData(Object[] data) {
		if (data.length == 1 && data[0] instanceof Transaction)
			return Transaction.class;
		else if (data.length == 1 && data[0] instanceof CsAdmin)
			return CsAdmin.class;
		else
			throw new IllegalArgumentException("Requires only one object of one of the following types: Account, Transaction.");
	}
    
}