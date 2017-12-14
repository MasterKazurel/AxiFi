package model;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Observable;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Transaction extends Observable {
	
	private int id;
	private int userID;
	private LocalDate time;
	private double amount;
	private String description;
	private double fees;
	
	public Transaction(int userID, LocalDate time, String description, double amount, double fees) {
		super();
		this.userID = userID;
		this.time = time;
		this.amount = amount;
		this.description = description;
		this.fees = fees;
	}
	
	public Transaction(int id, int userID, LocalDate time, String description, double amount, double fees) {
		super();
		this.id = id;
		this.userID = userID;
		this.time = time;
		this.amount = amount;
		this.description = description;
		this.fees = fees;
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
	
	public void setTime(String time) {
		this.time = LocalDate.parse(time);
	}

	public StringProperty getAmountProperty() {
		return new SimpleStringProperty(String.valueOf(amount));
	}
	
	public StringProperty getFormattedAmountProperty() {
		return new SimpleStringProperty(DecimalFormat.getCurrencyInstance().format(amount));
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
	
	@Override
	public String toString() {
		return "Transaction [id=" + id + ", userID=" + userID + ", time=" + time + ", amount=" + amount
				+ ", description=" + description + ", fees=" + fees + "]";
	}

	public int getID() {
		return id;
	}
	
	public int getUserID() {
		return userID;
	}
	
	public double getFee() {
		return fees;
	}
	
	public double getFeeAmount() {
		return fees * amount;
	}
	
	public StringProperty getFormattedFeeProperty() {
		return new SimpleStringProperty(DecimalFormat.getCurrencyInstance().format(getFeeAmount()));
	}

	
}
