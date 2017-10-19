

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Record implements Comparable<Record> {
	int year, monthOfYear, dayOfMonth, hourOfDay, minuteOfHour;
	String transaction; 
	double balance;
	Timestamp timestamp;
	SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");


	public Record(Timestamp timestamp, double balance, String transaction) {
		this.timestamp = timestamp;
		this.balance = balance;
		this.transaction = transaction;
	}

	public Timestamp getTime() {
		return timestamp;
	}

	public String toString(){
		return "Date: " + format.format(timestamp) + ", " + "Balance: " + balance + ", " + "Transaction: " + transaction ;	
	}
	
	@Override
	//uses the Comparable interface to set up the logic to sort the Arraylist
	public int compareTo(Record o) {

        if (this.timestamp.toInstant().toEpochMilli() == ((Record) o).timestamp.toInstant().toEpochMilli())
            return 0;
        else if ((this.timestamp.toInstant().toEpochMilli()) > ((Record) o).timestamp.toInstant().toEpochMilli())
            return 1;
        else
            return -1;
	}

	
	
}
