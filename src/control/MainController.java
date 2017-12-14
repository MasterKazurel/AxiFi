package control;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.SwingUtilities;

import application.Manager.Stages;
import application.Manager.Views;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
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
import model.Transaction;

public class MainController extends Controller {
	
	@FXML private BorderPane root;
    @FXML private TableView<Transaction> table;
    
    @FXML private TableColumn<Transaction, String> userCol, dateCol, amountCol, 
    									descripCol, feeCol;

    @FXML private Label balanceLbl, feesLbl;
    
    @FXML MenuButton accMenuBtn;
    @FXML MenuItem codes, print, exit, userGuide, allItm;
    @FXML Button newAccBtn, editAccBtn, delAccBtn, logoutBtn, newTransBtn, editTransBtn;
    
    private CsAdmin admin;
    private Profile currAcc;
    
    private ListChangeListener<Profile> usersListener;
    private ListChangeListener<Transaction> tableListener;
/*--- SETUP ---------------------------------------------------------------------------*/	
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    	// setup keyboard commands
    	table.setOnKeyPressed(keyEvt -> {
    		switch (keyEvt.getCode()) {
	    		case ENTER:
	    			if (editTransBtn.isVisible() && !editTransBtn.isDisabled())
	    				editTransactions();
	    			break;
    			case DELETE:
    				if (editTransBtn.isArmed() && !editTransBtn.isDisabled())
	    				if (currAcc != null)
	    					currAcc.getTransactions().removeAll(table.getSelectionModel().getSelectedItems());
	    				else {
	    					for (Profile p: admin.getUsers())
	    						p.getTransactions().removeAll(table.getSelectionModel().getSelectedItems());
	    				}
    				break;
    			case ADD:
    				if (newTransBtn.isArmed() && !newTransBtn.isDisabled())
    					newTransaction(new ActionEvent(new Object(), null));
    				break;
    			default:
    				break;
    		}
    	});
    	setupFadeIn(root);
    	setupOnShow(root, (obs, oldScene, newScene) -> {
    		if (newScene != null) refresh();
    	});
    	usersListener = new ListChangeListener<Profile>() {
			@Override
			public void onChanged(Change<? extends Profile> c) {
				while (c.next()) {
					if (c.wasRemoved()) {
						List<Profile> removed = (List<Profile>) c.getRemoved();
						for (Profile r: removed)
							accMenuBtn.getItems().removeIf(a -> a.getText().equals(r.getFullName()));
						db.deleteProfiles(removed);
						switchAccounts(null);
						refresh();
					}
					if (c.wasAdded()) {
						addAccounts(false, (List<Profile>) c.getAddedSubList());
						db.insertProfiles((List<Profile>) c.getAddedSubList());
						refresh();
					}
				}
			}
		};
		tableListener = new ListChangeListener<Transaction> () {
			@Override
			public void onChanged(Change<? extends Transaction> c) {
				while (c.next()) {
					if (c.wasAdded()) {
						db.insertTransactions((List<Transaction>)c.getAddedSubList());
						refresh();
					}
					if (c.wasRemoved()) {
						db.deleteTransactions((List<Transaction>)c.getRemoved());
						refresh();
					}
				}
			}
		};
	}
    
    /**
     * Requires only one object of one of the following types:
     * Transaction.
     * 
     * @throws IllegalArgumentException
     * - if the parameters do not conform to the requirements
     * above
     */
	@Override
	public void receiveData(Object... data) {
		Class<?> type = checkData(data);
		if (type.equals(Transaction.class)) {
			table.getItems().add((Transaction) data[0]);
		} else
			throw new IllegalArgumentException("Requires only one object of one of the following types: Transaction.");
	}
	
/*--- HELPERS ---------------------------------------------------------------------------*/	
	
    // Validates data coming in
    private Class<?> checkData(Object[] data) {
		if (data.length == 1 && data[0] instanceof Transaction)
			return Transaction.class;
		else
			throw new IllegalArgumentException("Requires only one object of one of the following types: Transaction.");
	}
    
    public void refresh() {
    	admin = db.getAdmin();
		showAccounts();
		table.refresh();
    }
	
    // Add accounts to menu button and setup changelisteners
	private void showAccounts() {
		admin.getUsers().removeListener(usersListener);
		admin.getUsers().addListener(usersListener);
		if (!admin.getUsers().isEmpty())
			accMenuBtn.getItems().clear();
		addAccounts(true, admin.getUsers());
		if (currAcc == null) {
			accMenuBtn.setText(allItm.getText());
			switchAccounts(null);
		} else switchAccounts(currAcc);
	}
	
	// Switch data & UI
	private void switchAccounts(Profile user) {
		if (user != null) {
			enable(true, delAccBtn, editAccBtn, newTransBtn, editTransBtn);
	    	accMenuBtn.setText(user.getFullName());
	    	currAcc = user;
	    	showTransactions();
		} else {
			enable(false, delAccBtn, editAccBtn, newTransBtn, editTransBtn);
			currAcc = null;
			accMenuBtn.setText(allItm.getText());
			showTransactions();
		}
    }
	
	// Add buttons
	private void addAccounts(boolean clear, List<Profile> accs) {
		if (clear) accMenuBtn.getItems().clear();
		if (!accMenuBtn.getItems().contains(allItm)) {
			allItm.setOnAction(e -> switchAccounts(null));
			accMenuBtn.getItems().add(allItm);
		}
		// Add accounts
		List<MenuItem> items = new ArrayList<MenuItem>();
		accs.forEach(user -> {
			MenuItem item = new MenuItem();
			item.setText(user.getFullName());
			item.setOnAction(e -> switchAccounts(user));
			items.add(item);
		});
		accMenuBtn.getItems().addAll(items);
	}
	
	// Set table items
	private void showTransactions() {
		if (currAcc != null) {
			table.setItems(currAcc.getTransactions());
			userCol.setVisible(false);
		} else {
			table.setItems(db.getTransactions()); //Overview
			userCol.setVisible(true);
			userCol.setCellValueFactory(cellData -> new SimpleStringProperty(db.getOwner(cellData.getValue()).getFullName()));
		}
		table.getItems().removeListener(tableListener);
		table.getItems().addListener(tableListener);
		dateCol.setCellValueFactory(cellData -> cellData.getValue().getTimeProperty());
        amountCol.setCellValueFactory(cellData -> cellData.getValue().getFormattedAmountProperty());
        descripCol.setCellValueFactory(cellData -> cellData.getValue().getDescriptionProperty());
        feeCol.setCellValueFactory(cellData -> cellData.getValue().getFormattedFeeProperty());
	}
	
/*--- FXML ---------------------------------------------------------------------------*/	
	
	@FXML
	private void codes(ActionEvent e) {
		manager.show(Stages.CODES, Views.CODES);
	}
    
    @FXML
    private void createAccount(ActionEvent e) {
    	if (admin != null)
    		manager.showSendData(Stages.NEW_ACC, Views.NEW_ACC, admin);
    }
    
    @FXML
    private void editAccount(ActionEvent e) {
    	if (admin != null && currAcc != null)
    		manager.showSendData(Stages.NEW_ACC, Views.NEW_ACC, admin, currAcc);
    }
    
    @FXML
    private void deleteAccount(ActionEvent e) {
    	if (admin != null && currAcc != null)
    		manager.showSendData(Stages.DEL_ACC, Views.DEL_ACC, admin, currAcc);
    }
    
    @FXML
    private void showCodes(ActionEvent e) {
    	manager.show(Stages.CODES, Views.CODES);
    }
    
    @FXML
    private void newTransaction(ActionEvent e) {
    	if (currAcc != null)
    		manager.showSendData(Stages.TRANS, Views.TRANS, currAcc);
    }
    
    @FXML
    private void editTransactions() {
    	if (table.getSelectionModel() != null && !table.getSelectionModel().isEmpty())
    		manager.showSendData(Stages.TRANS, Views.TRANS, table.getSelectionModel(), currAcc);
    }
    
    @FXML
    private void logout(ActionEvent e) {
    	manager.showClose(Stages.LOGIN, Views.LOGIN, Stages.MAIN);
    }
    
    @FXML
	private void exit(ActionEvent e) {
		System.exit(0);
	}
    
    /**
     *  @throws IllegalStateException, SecurityException 
     */
    @FXML
	public void print() {
		Printer printer = Printer.getDefaultPrinter();
		PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT,
				Printer.MarginType.HARDWARE_MINIMUM);
		PrinterJob job = PrinterJob.createPrinterJob();
		SwingUtilities.invokeLater(() -> {
			if (job != null && job.showPrintDialog(table.getScene().getWindow()) && job.printPage(pageLayout, table))
				job.endJob();
		});
		
	}

}