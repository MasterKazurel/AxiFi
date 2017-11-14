package model;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Transaction {
	
	private StringProperty time;
	private StringProperty amount;
	private StringProperty description;
	
	public Transaction(String time, String amount, String description) {
		super();
		this.time = new SimpleStringProperty(time);
		this.amount = new SimpleStringProperty(amount);
		this.description = new SimpleStringProperty(description);
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
