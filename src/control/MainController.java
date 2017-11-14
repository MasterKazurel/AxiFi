package control;

import java.util.ArrayList;
import java.util.List;

import application.Main.Stages;
import application.Main.Views;
import javafx.collections.FXCollections;
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
    @FXML Button newAccBtn, delAccBtn, logoutBtn, newTransBtn;
    
    private CsAdmin admin;
    private Profile currAcc;
    
    @Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}
    
    @FXML
    private void createAccount(ActionEvent e) {
    	// Create account UI and add to list
    }
    
    @FXML
    private void deleteAccount(ActionEvent e) {
    	main.sendData(Views.DEL_ACC, currAcc);
    	main.show(Stages.DEL_ACC, Views.DEL_ACC);
    }
    
    @FXML
    private void newTransaction(ActionEvent e) {
    	main.show(Stages.NEW_TRANS, Views.NEW_TRANS);
    }
    
    @FXML
    private void logout(ActionEvent e) {
    	main.show(Stages.LOGIN, Views.LOGIN);
    	main.close(Stages.MAIN);
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
			// Add accounts
			List<MenuItem> items = new ArrayList<MenuItem>();
			admin.getUsers().forEach(user -> {
				MenuItem item = new MenuItem();
				item.setText(user.getFullName());
				item.setOnAction(e -> switchAccounts(user));
				items.add(item);
			});
			accMenuBtn.getItems().addAll(items);
		} else
			throw new IllegalArgumentException("Requires only one object of one of the following types: Account, Transaction.");
	}
	
	private void showTransactions() {
		// Add transactions
		transTable.setItems(FXCollections.observableList(currAcc.getTransactions()));
		// Display columns
		dateCol.setCellValueFactory(cellData -> cellData.getValue().getTime());
        amountCol.setCellValueFactory(cellData -> cellData.getValue().getAmount());
        descripCol.setCellValueFactory(cellData -> cellData.getValue().getDescription());
	}
	
	private void switchAccounts(Profile user) {
    	accMenuBtn.setText(user.getFullName());
    	currAcc = user;
    	showTransactions();
    }
	
	private Class<?> checkData(Object[] data) {
		if (data.length == 1 && Transaction.class.isInstance(data[0]))
			return Transaction.class;
		else if (data.length == 1 && data[0] instanceof CsAdmin)
			return CsAdmin.class;
		else
			throw new IllegalArgumentException("Requires only one object of one of the following types: Account, Transaction.");
	}
    
}