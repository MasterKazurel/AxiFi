package model;

public class Profile {
	private String firstName;
	private String lastName;
	private String password;
	
	private double balance;
	

	public Profile(String firstName, String lastName, String password, double balance) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.balance = balance;
	}

	public Profile(String firstName, String lastName, String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.balance = 0;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
	
}
