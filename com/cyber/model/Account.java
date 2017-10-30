package cyber.model;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class Account extends Storeable<Account> {
	private static final long serialVersionUID = 1L;

	public enum Level {
		ADMIN,
		USER;
		public boolean is(Level lvl) {
			return lvl.equals(this);
		}
	}
	
	private Date time;
	private String name, username, password;
	private Double balance;
	private Level lvl;
	private List<Transaction> transactions;
	
	public Account(Date timeCreated, String name, String username, 
			String password, Double balance, Level lvl, List<Transaction> transactions) {
		this.name = name;
		this.username = username;
		this.password = password;
		this.balance = balance;
		this.time = timeCreated;
		this.lvl = lvl;
	}
	
	public Account(Account a) {
		this.name = a.name;
		this.username = a.username;
		this.password = a.password;
		this.balance = a.balance;
		this.time = a.time;
		this.lvl = a.lvl;
	}
	
	@Override
	public boolean save() {
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(username + ".ser"));
			oos.writeObject(this);
			oos.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	public void setUsername(String userName) {
		this.username = userName;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Level getLvl() {
		return lvl;
	}

	public void setLvl(Level lvl) {
		this.lvl = lvl;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setTimeStamp(Timestamp ts) {
		this.time = ts;
	}

	public Double getBalance() {
		return balance;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public Date getTime() {
		return time;
	}

	public String getName() {
		return name;
	}

	//converts the Balance to a string
	public String getBalanceAsString() {
		String balanceString = String.format("%.1f", balance);
		return balanceString;
	}
}
