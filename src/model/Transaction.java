package model;

import java.time.LocalDate;
import java.util.Observable;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Transaction extends Observable {
	
	private LocalDate time;
	private double amount;
	private String description;
	
	
	public Transaction(LocalDate time, double amount, String description) {
		super();
		this.time = time;
		this.amount = amount;
		this.description = description;
	}

	public StringProperty getTimeProperty() {
		return new SimpleStringProperty(time.toString());
	}
	
	public LocalDate getTime() {
		return time;
	}

	public void setTime(LocalDate time) {
		this.time = time;
	}

	public StringProperty getAmountProperty() {
		return new SimpleStringProperty(String.valueOf(amount));
	}
	
	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public StringProperty getDescriptionProperty() {
		return new SimpleStringProperty(description);
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
}
