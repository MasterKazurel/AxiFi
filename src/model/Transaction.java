package model;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Transaction {
	private int id; //id is used as the primary key for Transaction data
	private int userId; //userId is used as a foreign key determining who the transaction is applied to
	private StringProperty type; //This is the type of the transaction
	private StringProperty time;
	private StringProperty amount;
	private StringProperty description;
	
	public Transaction(String time, String amount, String description) {
		super();
		this.time = new SimpleStringProperty(time);
		this.amount = new SimpleStringProperty(amount);
		this.description = new SimpleStringProperty(description);
	}
	
	public Transaction(int id, int userId, String purpose, String type, double amount) {
		this.id = id;
		this.userId = userId;
		this.description = new SimpleStringProperty(purpose);
		this.type = new SimpleStringProperty(type);
		this.amount = new SimpleStringProperty(Double.toString(amount));
	}
	
	public Transaction(String purpose, String type, double amount) {
		this.description = new SimpleStringProperty(purpose);
		this.type = new SimpleStringProperty(type);
		this.amount = new SimpleStringProperty(Double.toString(amount));
	}
	
	public StringProperty getTime() {
		return time;
	}

	public void setTime(StringProperty time) {
		this.time = time;
	}

	public StringProperty getAmount() {
		return amount;
	}

	public void setAmount(StringProperty amount) {
		this.amount = amount;
	}

	public StringProperty getDescription() {
		return description;
	}

	public void setDescription(StringProperty description) {
		this.description = description;
	}

	
}
