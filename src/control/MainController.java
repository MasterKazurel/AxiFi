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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import model.Account;
import model.Transaction;

public class MainController extends Controller {
	@FXML private BorderPane root;
    @FXML private TableView<Transaction> transTable;
    
    @FXML private TableColumn<Transaction, String> dateCol, amountCol, 
    									descripCol;

    @FXML private Label firstNameLabel, lastNameLabel, streetLabel, 
    				postalCodeLabel, cityLabel, birthdayLabel;
    @FXML Button newAccBtn, delAccBtn, logoutBtn, newTransBtn;
    
    private List<Transaction> transactions;

    @FXML
	public void initialize() {
    	transactions = new ArrayList<Transaction>();
    	// Add some sample data
    	transactions.add(new Transaction("10/15/17", "T-shirts", "$100.00"));
    	transactions.add(new Transaction("9/27/17", "Cups", "$50.00"));
    	transactions.add(new Transaction("11/01/17", "Party hats", "$1000.00"));
    	transTable.setItems(FXCollections.observableList(transactions));
        // Initialize the person table with the two columns.
        dateCol.setCellValueFactory(cellData -> cellData.getValue().getTime());
        amountCol.setCellValueFactory(cellData -> cellData.getValue().getAmount());
        descripCol.setCellValueFactory(cellData -> cellData.getValue().getDescription());
    }
    
    @FXML
    private void createAccount(ActionEvent e) {
    	// Create account UI and add to list
    }
    
    @FXML
    private void deleteAccount(ActionEvent e) {
    	// Clear account object from list
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
		checkData(data, Account.class, Transaction.class);
		transactions.add((Transaction)data[0]);
		transTable.setItems(FXCollections.observableList(transactions));
	}
	
	private void checkData(Object[] data, Class<?>... types) {
		boolean valid = false;
		if (data.length != 1)
			valid = false;
		else for (Object d: data)
			for (Class<?> type: types)
				if (type.isInstance(d)) {
					valid = true;
					break;
				}
				
		if (!valid)
			throw new IllegalArgumentException("Requires only one object of one of the following types: Account, Transaction.");
	}
    
}