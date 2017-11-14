package control;

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
import model.CsAdmin;
import model.Transaction;

public class MainController extends Controller {
	@FXML private BorderPane root;
    @FXML private TableView<Transaction> transTable;
    
    @FXML private TableColumn<Transaction, String> dateCol, amountCol, 
    									descripCol;

    @FXML private Label firstNameLabel, lastNameLabel, streetLabel, 
    				postalCodeLabel, cityLabel, birthdayLabel;
    @FXML Button newAccBtn, delAccBtn, logoutBtn, newTransBtn;
    
    private CsAdmin admin;
    
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
		Class<?> type = checkData(data);
		if (type.equals(String.class)) {
			admin = jkit.openAdmin((String)data[0]);
			admin.getTransactions().add(new Transaction("10/01/17", "$100.00", "T-shirts"));
			admin.getTransactions().add(new Transaction("10/23/17", "$23.00", "Cups"));
			admin.getTransactions().add(new Transaction("11/02/17", "$1000.00", "Party hats"));
			transTable.setItems(FXCollections.observableList(admin.getTransactions()));
			dateCol.setCellValueFactory(cellData -> cellData.getValue().getTime());
	        amountCol.setCellValueFactory(cellData -> cellData.getValue().getAmount());
	        descripCol.setCellValueFactory(cellData -> cellData.getValue().getDescription());
		} else 
			throw new IllegalArgumentException("Requires only one object of one of the following types: Account, Transaction.");
	}
	
	private Class<?> checkData(Object[] data) {
		if (data.length == 1 && Transaction.class.isInstance(data[0]))
			return Transaction.class;
		else if (data.length == 2 && data[0] instanceof String && data[1] instanceof String)
			return String.class;
		else
			throw new IllegalArgumentException("Requires only one object of one of the following types: Account, Transaction.");
	}
    
}