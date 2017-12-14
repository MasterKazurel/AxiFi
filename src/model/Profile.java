package model;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Profile extends Observable {
	private int id; //id is used by the database as the primary key of the Profile data
	private StringProperty firstName;
	private StringProperty lastName;
	
	private DoubleProperty balance;
	private ObservableList<Transaction> transactionsObserver;
	private List<Transaction> transactions;
	
	//private ListChangeListener<Transaction> transListener;
	
	public Profile(String firstName, String lastName) {
		setFirstName(firstName);
		setLastName(lastName);
		transactions = new ArrayList<Transaction>();
		transactionsObserver = FXCollections.observableList(transactions);
		/*setupTransListener();
		setupTransactions();*/
	}
	
	public Profile( String firstName, String lastName, double balance) {
		setFirstName(firstName);
		setLastName(lastName);
		this.balance = new SimpleDoubleProperty(balance);
		transactions = new ArrayList<Transaction>();
		transactionsObserver = FXCollections.observableList(transactions);
		/*setupTransListener();
		setupTransactions();*/
	}
	
	Profile(int id, String firstName, String lastName, double balance) {
		this.id = id;
		setFirstName(firstName);
		setLastName(lastName);
		this.balance = new SimpleDoubleProperty(balance);
		transactions = new ArrayList<Transaction>();
		transactionsObserver = FXCollections.observableList(transactions);
		/*setupTransListener();
		setupTransactions();*/
	}
	
	Profile(int id, String firstName, String lastName) {
		setFirstName(firstName);
		setLastName(lastName);
		transactions = new ArrayList<Transaction>();
		transactionsObserver = FXCollections.observableList(transactions);
		/*setupTransListener();
		setupTransactions();*/
	}
	
	/*private void setupTransListener() {
		transListener = new ListChangeListener<Transaction> () {
			@Override
			public void onChanged(Change<? extends Transaction> c) {
				while (c.next()) {
					if (c.wasAdded()) 
						addToBalance((List<Transaction>) c.getAddedSubList());
					if (c.wasRemoved())
						subtractFromBalance((List<Transaction>) c.getRemoved());
				}
			}
		};
	}*/

	public String getFullName() {
		return firstName.getValue() + " " + lastName.getValue();
	}
	
	public void setFullName(String fullName) {
		String[] names = fullName.split("\\s+");
		if (names.length == 2) {
			firstName.setValue(names[0]);
			lastName.setValue(names[1]);
		} else throw new IllegalArgumentException();
	}
	
	public String getFirstName() {
		return firstName.getValue();
	}

	public int getId() {
		return id;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = new SimpleStringProperty(firstName);
	}

	public String getLastName() {
		return lastName.getValue();
	}

	public void setLastName(String lastName) {
		this.lastName = new SimpleStringProperty(lastName);
	}

	public double getBalance() {
		return balance.doubleValue();
	}
	
	public String getFormattedBalance() {
		return DecimalFormat.getCurrencyInstance().format(balance.doubleValue());
	}

	public ObservableList<Transaction> getTransactions() {
		return transactionsObserver;
	}
	
	/*private void setupTransactions() {
		transactions = new ArrayList<Transaction>();
		transactionsObserver = FXCollections.observableList(transactions);
		transactionsObserver.removeListener(transListener);
		transactionsObserver.addListener(transListener);
	}
	
	public void addToBalance(List<Transaction> ts) {
		ts.forEach(t -> balance.add(t.getAmount()));
	}
	
	public void subtractFromBalance(List<Transaction> ts) {
		ts.forEach(t -> balance.add(-t.getAmount()));
	}*/
	
}
