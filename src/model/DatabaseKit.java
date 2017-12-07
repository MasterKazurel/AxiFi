package model;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DatabaseKit {
	private Connection c = null;
	
	public void initDatabase(String dbName) {
	  //This method will create the database file and the basic schema for the database
	      try {
	    	 //Load this class from the build path
	         Class.forName("org.sqlite.JDBC");
	         //Link a new connection to the database or create a new one if one is not already there
	         c = DriverManager.getConnection("jdbc:sqlite:" + dbName);
	      } catch ( Exception e ) {
	    	  e.printStackTrace();
	      }
	}
	
	public void deleteDatabase(String filepath) {
		//!!!WARNING this will delete the entire database file!!!
		File f = new File(filepath);
		//Make sure that no directories are harmed accidentally
		if(!f.isDirectory()) {
			f.delete();
		}
	}
	
	public void closeConnection() {
		try {
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void buildSchema() {
		//SQL statements
		String mkUsrTable = "CREATE TABLE USER (" +
				"ID INTEGER PRIMARY KEY AUTOINCREMENT," +
				"FIRSTNAME TEXT NOT NULL," +
				"LASTNAME TEXT NOT NULL," +
				"BALANCE DECIMAL(8,2)" +
				");";
		
		String mkAdmTable = "CREATE TABLE ADMIN(" +
							"LOGIN CHAR(50) PRIMARY KEY," +
							"PASSWORD CHAR(50) NOT NULL" +
							");";
		
		String mkTransTable = "CREATE TABLE TRANS(" +
							  "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
							  "PURPOSE TEXT," +
							  "TRANS_TYPE CHAR(50)," + 
							  "AMMOUNT DECIMAL(8,2) NOT NULL," +
							  "USER_ID INTEGER NOT NULL," +
							  "FOREIGN KEY(USER_ID) REFERENCES USER(ID)" + 
							  ");";
		
		String mkFeeTable = "CREATE TABLE FEES(" +
							  "ID INTEGER PRIMARY KEY AUTOINCREMENT," + 
							  "FEE REAL NOT NULL," +
							  "TRANS_ID INTEGER NOT NULL," +
							  "FOREIGN KEY(TRANS_ID) REFERENCES TRANS(ID)" +
							  ");";
		
		try {
			//Load an sql statement to be executed
			Statement buildTable = c.createStatement();
			
			//Execute the SQL statements to build the schema
			buildTable.execute(mkUsrTable);
			buildTable.execute(mkAdmTable);
			buildTable.execute(mkTransTable);
			buildTable.execute(mkFeeTable);
			buildTable.close();
		}catch(Exception e) {
			e.printStackTrace();		
		}
	}
	
	public void insertProfile(String firstName, String lastName) {
		String newUser = "INSERT INTO USER(FIRSTNAME, LASTNAME, BALANCE)" +
							"VALUES ('" + firstName + "' , '" + lastName + "' , 0.00);"; 
		
		try {
			//Load an sql statement to be executed
			Statement insertUser = c.createStatement();
			insertUser.execute(newUser);
			insertUser.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void insertProfile(Profile profile) {
		String firstName = profile.getFirstName();
		String lastName = profile.getLastName();
		double balance = 0.0;
		
		String newUser = "INSERT INTO USER(FIRSTNAME, LASTNAME, BALANCE)" +
							"VALUES ('" + firstName + "' , '" + lastName + "' , " + balance +");"; 
		
		try {
			//Load an sql statement and execute
			Statement insertUser = c.createStatement();
			insertUser.execute(newUser);
			insertUser.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void removeProfile(int id) {
		String rmUser = "DELETE FROM USER WHERE ID=" + id;
		try {
			Statement removeUser = c.createStatement();
			removeUser.execute(rmUser);
			removeUser.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void insertTransaction(int userId, String purpose, String code, double ammount) {
		String newTransaction = "INSERT INTO TRANS(PURPOSE, TRANS_TYPE, AMMOUNT, USER_ID)"+
								"VALUES('" + purpose + "','" + code + "'," + ammount + "," + userId + ");"; 
		
		try {
			Statement insertTrans = c.createStatement();
			insertTrans.execute(newTransaction);
			insertTrans.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public ObservableList<Profile> getUsers() {
		List<Profile> profileSet = new ArrayList<Profile>();
		
		String getUsers = "SELECT * FROM USER;";
		
		try {
			Statement queryUsers = c.createStatement();
			ResultSet tr = queryUsers.executeQuery(getUsers);
			
			Profile user;
			while(tr.next()) {
				int id = tr.getInt("ID");
				String firstname = tr.getString("FIRSTNAME");
				String lastname = tr.getString("LASTNAME");
				double balance = tr.getDouble("BALANCE");
				
				user = new Profile(id, firstname, lastname, balance);				
				profileSet.add(user);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return FXCollections.observableList(profileSet);
		
	}	
	
	public ObservableList<Transaction> getTransactions(int userId) {
		List<Transaction> transactionSet = new ArrayList<Transaction>();
		
		String getTransactions = "SELECT * FROM TRANS WHERE USER_ID=" + userId + ";";
		
		try {
			Statement queryTrans = c.createStatement();
			ResultSet tr = queryTrans.executeQuery(getTransactions);
			
			Transaction loadTransaction;
			while(tr.next()) {
				
				int id = tr.getInt("ID");
				int uId = tr.getInt("USER_ID"); //User foreign key
				String purpose = tr.getString("PURPOSE");
				String type = tr.getString("TRANS_TYPE");
				double ammount = tr.getDouble("AMMOUNT");
				loadTransaction = new Transaction(id, uId, purpose, type, ammount);
				transactionSet.add(loadTransaction);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return FXCollections.observableList(transactionSet);
		
	}
	
	public void updateProfile(Profile profile) {
		ArrayList<Transaction> transactions = (ArrayList<Transaction>) profile.getTransactions();
		
		for(int i=0; i < transactions.size(); i++) {
			//TODO Update anything that was changed
			String updateTransactions = "UPDATE TRANS SET ";
		}
	}
	
	public void insertAdmin(CsAdmin admin) {
		insertAdmin(admin.getLogin(), admin.getPassword());
	}
	
	public void insertAdmin(String login, String password) {
		String newAdmin = "INSERT INTO ADMIN(LOGIN, PASSWORD)"+
						  "VALUES('" + login + "','" + password + "');";
		Statement insertAdmin = null;
		try {
			insertAdmin = c.createStatement();
			insertAdmin.execute(newAdmin);
			insertAdmin.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public CsAdmin getAdmin() {
		CsAdmin admin = null;
		String getAdmin = "SELECT * FROM ADMIN";
		Statement queryAdmin = null;
		try {
			queryAdmin = c.createStatement();
			ResultSet results = queryAdmin.executeQuery(getAdmin);
			if (results.next()) {
				admin = new CsAdmin(results.getString("LOGIN"), results.getString("PASSWORD"), "Robyn", "Berg");
				admin.setUsers(getUsers());
				for (Profile p: admin.getUsers())
					p.setTransactions(getTransactions(p.getId()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return admin;
	}
	
	public boolean adminLogin(String login, String password) {
		String pwd = ""; //The queried password
		
		String checkAdmin = "SELECT PASSWORD FROM ADMIN WHERE LOGIN=" + "'" + login + "';";
		
		Statement queryAdmin = null;
		try {
			queryAdmin = c.createStatement();
			ResultSet admQ = queryAdmin.executeQuery(checkAdmin);
				if(admQ.next()) {
					pwd = admQ.getString("PASSWORD");
				}
			queryAdmin.close();
		}catch(Exception e) {
			buildSchema();
			e.printStackTrace();
		}
		if(pwd.equals(password)) {
			//Login success
			return true;
		}
		return false;
	}
	
	public static void main( String args[] ) {
	      DatabaseKit db = new DatabaseKit();
	      
	      String testDb = "test.db";
	      System.out.println("Purging test database..");
	      db.initDatabase(testDb);
	      db.deleteDatabase(testDb);
	      System.out.println("Purge complete!\n");
	      System.out.println("Creating new test database..");
	      db.initDatabase(testDb);
	      System.out.println("Building database schema..");
	      db.buildSchema();
	      System.out.println("Building schema complete!\n");
	      System.out.println("Inserting new profiles...");
	      db.insertProfile("Joe", "Volcano");
	      db.insertProfile("Frog", "King");
	      db.insertProfile("Meteor", "Knight");
	      db.insertProfile("Nikolai", "Romanov");
	      db.insertProfile("Dracula", "Vampire");
	      db.insertProfile("Girl", "Chan");
	      System.out.println("Inserted new profiles!\n");
	      System.out.println("Inserting new transactions!");
	      db.insertTransaction(1, "memes", "1234", 20.99);
	      db.insertTransaction(1, "elemental iron", "10", 5.50);
	      db.insertTransaction(2, "the human soul", "42", 1.99);
	      db.insertTransaction(2, "a pound of butter", "21300412", 1000.20);
	      db.insertTransaction(4, "Vodka", "88", 19.95);
	      db.insertTransaction(5, "a replicant", "808", 5000.153);
	      db.insertTransaction(5, "Sympathy", "12", 9000000.15);
	      db.insertTransaction(6, "Thaaat is the price...", "123No321", -50000000);
	      System.out.println("Inserted new transactions!\n");
	      System.out.println("Creating new admin profile...");
	      db.insertAdmin("CsAdmin", "iforgotwhatistsupposedtobe");
	      System.out.println("Created new admin profile!\n");
	      
	      System.out.println("Closing database connection...");
	      db.closeConnection();
	      System.out.println("Closed.\n");
	  }

}