package control;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.Manager.Stages;
import application.Manager.Views;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import model.CsAdmin;
import model.Profile;
import model.Testing;
import model.Transaction;

public class MainController extends Controller {
	@FXML private BorderPane root;
    @FXML private TableView<Transaction> table;
    
    @FXML private TableColumn<Transaction, String> dateCol, amountCol, 
    									descripCol;

    @FXML private Label firstNameLabel, lastNameLabel, streetLabel, 
    				postalCodeLabel, cityLabel, birthdayLabel;
    @FXML MenuButton accMenuBtn;
    @FXML Button newAccBtn, delAccBtn, logoutBtn, newTransBtn;
    
    private CsAdmin admin;
    private Profile currAcc;
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    	table.setOnKeyPressed(keyEvt -> {
    		switch (keyEvt.getCode()) {
    			case DELETE:
    				
    				break;
    			default:
    				break;
    		}
    	});
	}
    
    @Override
	protected void setupValidation() {
		// TODO Auto-generated method stub
		
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
			showTransactions();
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
    
    @FXML
    private void createAccount(ActionEvent e) {
    	// Create account UI and add to list
    }
    
    @FXML
    private void deleteAccount(ActionEvent e) {
    	manager.showSendData(Stages.DEL_ACC, Views.DEL_ACC, admin, currAcc);
    }
    
    @FXML
    private void newTransaction(ActionEvent e) {
    	manager.show(Stages.TRANS, Views.TRANS);
    }
    
    @FXML
    private void editTransactions() {
    	manager.showSendData(Stages.TRANS, Views.TRANS, table.getSelectionModel());
    }
    
    @FXML
    private void logout(ActionEvent e) {
    	manager.showClose(Stages.LOGIN, Views.LOGIN, Stages.MAIN);
    }
	
	private void showAccounts() {
		admin.getUsers().addListener((ListChangeListener.Change<? extends Profile> c) -> {
			while (c.next())
				if (c.wasRemoved()) {
					accMenuBtn.getItems().remove(c.getFrom());
					if (!admin.getUsers().isEmpty()) {
						currAcc = admin.getUsers().get(0);
						accMenuBtn.setText(admin.getUsers().get(0).getFullName());
					} else {
						switchAccounts(null);
					}
					showTransactions();
				}
				else if (c.wasAdded())
					addAccounts((List<Profile>)c.getAddedSubList());
		});
		if (accMenuBtn.getItems().isEmpty() && !admin.getUsers().isEmpty()) {
			addAccounts(admin.getUsers());
			currAcc = admin.getUsers().get(0);
			accMenuBtn.setText(admin.getUsers().get(0).getFullName());
			showTransactions();
		}
	}
	
	private void switchAccounts(Profile user) {
		if (user != null) {
	    	accMenuBtn.setText(user.getFullName());
	    	currAcc = user;
	    	showTransactions();
		} else {
			accMenuBtn.setText("Select an account");
			currAcc = null;
			showTransactions();
		}
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
		});
		accMenuBtn.getItems().addAll(items);
	}
	
	private void showTransactions() {
		if (currAcc != null) {
			table.setItems(currAcc.getTransactions());
			dateCol.setCellValueFactory(cellData -> cellData.getValue().getTimeProperty());
	        amountCol.setCellValueFactory(cellData -> cellData.getValue().getAmountProperty());
	        descripCol.setCellValueFactory(cellData -> cellData.getValue().getDescriptionProperty());
		} else table.getItems().clear();
	}

}