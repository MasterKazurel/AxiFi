package model;

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
	private StringProperty firstName;
	private StringProperty lastName;
	private StringProperty password;
	
	private DoubleProperty balance;
	private ObservableList<Transaction> transactionsObserver;
	private List<Transaction> transactions;

	public Profile(String firstName, String lastName, String password, double balance) {
		super();
		setFirstName(firstName);
		setLastName(lastName);
		setPassword(password);
		setBalance(balance);
		transactions = new ArrayList<Transaction>();
		transactionsObserver = FXCollections.observableList(transactions);
	}

	public Profile(String firstName, String lastName, String password) {
		super();
		setFirstName(firstName);
		setLastName(lastName);
		setPassword(password);
		setBalance(0);
		transactions = new ArrayList<Transaction>();
		transactionsObserver = FXCollections.observableList(transactions);
	}

	public String getFullName() {
		return firstName.getValue() + " " + lastName.getValue();
	}
	
	public String getFirstName() {
		return firstName.getValue();
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

	public String getPassword() {
		return password.getValue();
	}

	public void setPassword(String password) {
		this.password = new SimpleStringProperty(password);
	}

	public double getBalance() {
		return balance.doubleValue();
	}

	public void setBalance(double balance) {
		this.balance = new SimpleDoubleProperty(balance);
	}

	public ObservableList<Transaction> getTransactions() {
		return transactionsObserver;
	}
	
}
