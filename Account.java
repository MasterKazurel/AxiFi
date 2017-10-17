

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Scanner;

public class Account {
	private Double balance;
	private String userName, password, time, accountType;
	private Timestamp ts;
	newLinkedList<Record> recordStack = new LinkedListStack<Record>();
	
	@SuppressWarnings("resource")
	// when a Weight object is created (in the main class), this scans the File
	// and creates objects of everything contained within it
	public Account(File fileName) {
		Scanner scanner = null;
		try {
			scanner = new Scanner(fileName).useDelimiter(",");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		password = scanner.next().trim(); // scans the first line to pull the
											// password
		scanning(scanner); // starts recursive scanning function

	}

	// scans through the rest of the lines that hold the user records
	// makes Record objects of each line
	public void scanning(Scanner inputScanner) {
		if (!inputScanner.hasNext())
			return;

		time = inputScanner.next().trim();
		accountType = inputScanner.next().trim();
		balance = Double.parseDouble(inputScanner.next().trim());
		
		ts = Timestamp.valueOf(time);
		recordStack.push(new Record(ts, accountType, balance));
		scanning(inputScanner);
	}

	//calculates BMI

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Double getBalance() {
		return balance;
	}

	public Timestamp getTimestamp() {
		return ts;
	}

	public String getTimeString() {
		return time;
	}

	//converts the Double height to a string
	public String getBalanceString() {
		String balanceString = String.format("%.1f", balance);
		return balanceString;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

}
