package control;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import main.MainApp.Views;
import model.Transaction;

public class MainController extends Controller {
    @FXML private TableView<Transaction> transTable;
    
    @FXML private TableColumn<Transaction, String> dateCol, amountCol, 
    									descripCol;

    @FXML private Label firstNameLabel, lastNameLabel, streetLabel, 
    				postalCodeLabel, cityLabel, birthdayLabel;
    @FXML Button newAccBtn, delAccBtn, logoutBtn, newTransBtn;
    
    private List<Transaction> transactions;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public MainController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
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
    private void handleAction (ActionEvent e) {
    	Object src = e.getSource();
    	if (src.equals(newTransBtn))
    		mainApp.show(Views.NEW_TRANS);
    	else if (src.equals(logoutBtn))
    		mainApp.show(Views.LOGIN);
    }
}