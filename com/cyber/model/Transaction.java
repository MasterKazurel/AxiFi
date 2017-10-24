package com.cyber.model;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.cyber.view.Format;

public class Transaction implements Comparable<Transaction>, Serializable {
	private static final long serialVersionUID = 1L;

	public enum Code {
		BLANK(0);
		private int val;
		private Code(int val) { this.val = val; }
		public int getVal() {return val;}
	}
	
	private Date time;
	private double amount;
	private String description;
	private Code code;
	private SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");


	public Transaction(Date time, double amount, String description, Code code) {
		this.amount = amount;
		this.description = description;
		this.code = code;
		this.time = time;
	}

	public Date getTime() {
		return time;
	}

	public String toString(){
		return "Date: " + format.format(time) +
				", " + "Balance: " + Format.toCurrency(amount) +
				", " + "Transaction: " + description;	
	}
	
	@Override
	public int compareTo(Transaction o) {
        if (this.time.toInstant().toEpochMilli() == ((Transaction) o).time.toInstant().toEpochMilli())
            return 0;
        else if ((this.time.toInstant().toEpochMilli()) > ((Transaction) o).time.toInstant().toEpochMilli())
            return 1;
        else
            return -1;
	}
	
}
