package model;

import java.io.File;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DatabaseKit {
	private Connection c = null;
	private String fileName;
	
	
/*--- DATABASE ---------------------------------------------------------------------------*/
	
	public void init(String fileName) {
		  //This method will create the database file and the basic schema for the database
	      try {
	    	 //Load this class from the build path
	         Class.forName("org.sqlite.JDBC").newInstance();
	         //Link a new connection to the database or create a new one if one is not already there
	         c = DriverManager.getConnection("jdbc:sqlite::resource:resources/" + fileName);
	         this.fileName = fileName;
	      } catch ( Exception e ) {
	    	  e.printStackTrace();
	      }
		}
	
	public void init() {
	  init("data.db");
	}
	
	public boolean deleteDatabase() {
		//!!!WARNING this will delete the entire database file!!!
		File f = new File("/resources/" + fileName);
		//Make sure that no directories are harmed accidentally
		if(!f.isDirectory())
			return f.delete();
		return false;
	}
	
	public void closeConnection() {
		try {
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean buildSchema() {
		boolean success = false;
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
							  "DESCRIPTION TEXT," +
							  "TRANSTIME DATE," + 
							  "AMOUNT DECIMAL(8,2) NOT NULL," +
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
			success = 
				buildTable.execute(mkUsrTable) &&
				buildTable.execute(mkAdmTable) &&
				buildTable.execute(mkTransTable) &&
				buildTable.execute(mkFeeTable);
			buildTable.close();
		} catch(Exception e) {
			e.printStackTrace();		
		}
		return success;
	}
	
/*--- PROFILE ---------------------------------------------------------------------------*/

	public boolean insertProfile(Profile profile) {
		boolean success = false;
		String firstName = profile.getFirstName();
		String lastName = profile.getLastName();
		double balance = 0.0;
		
		String newUser = "INSERT INTO USER(FIRSTNAME, LASTNAME, BALANCE)" +
							"VALUES ('" + firstName + "' , '" + lastName + "' , " + balance +");";
		
		try {
			//Load an sql statement and execute
			Statement insertUser = c.createStatement();
			success = insertUser.execute(newUser);
			insertUser.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;
	}
	
	public Profile getProfile(String attribute, String value) {
		Profile profile = null;
		String getProfile = "SELECT * FROM USER WHERE ?=?;";
		try (
			PreparedStatement profileStmt = c.prepareStatement(getProfile);
		) {
			ResultSet results = profileStmt.executeQuery();
			if (results.next()) {
				profile = new Profile(results.getInt("ID"), 
						results.getString("FIRSTNAME"), 
						results.getString("LASTNAME"));
				profile.setTransactions(getTransactions(profile.getId()));
			}
			
		} catch (SQLException ex) {
			System.err.println("Error");
			ex.printStackTrace();
		}
		return profile;
	}
	
	public boolean removeProfile(int id) {
		boolean success = false;
		String rmUser = "DELETE FROM USER WHERE ID=?;";
		try (
			PreparedStatement removeUser = c.prepareStatement(rmUser);
		) {
			success = removeUser.execute();
			removeUser.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return success;
	}
	
	public boolean updateProfile(Profile profile) {
		boolean success = false;
		ArrayList<Transaction> transactions = (ArrayList<Transaction>) profile.getTransactions();
		
		for(int i=0; i < transactions.size(); i++) {
			//TODO Update anything that was changed
			String updateTransactions = "UPDATE TRANS SET ";
			success = false;
		}
		return success;
	}
	
	public Profile getOwner(Transaction transaction) {
		Profile user = null;
		String getUsers = "SELECT * FROM USER WHERE ID=?;";
		
		try {
			PreparedStatement queryUsers = c.prepareStatement(getUsers);
			queryUsers.setInt(1, transaction.getUserID());
			ResultSet results = queryUsers.executeQuery();
			
			while(results.next()) {
				int id = results.getInt("ID");
				String firstname = results.getString("FIRSTNAME");
				String lastname = results.getString("LASTNAME");
				double balance = results.getDouble("BALANCE");
				user = new Profile(id, firstname, lastname, balance);				
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public ObservableList<Profile> getProfiles() {
		List<Profile> profileSet = new ArrayList<Profile>();
		
		String getUsers = "SELECT * FROM USER;";
		
		try {
			PreparedStatement queryUsers = c.prepareStatement(getUsers);
			ResultSet results = queryUsers.executeQuery();
			
			while(results.next())
				profileSet.add(
					new Profile(
						results.getInt("ID"), 
						results.getString("FIRSTNAME"),
						results.getString("LASTNAME"),
						results.getDouble("BALANCE"))
					);
				
			queryUsers.close();
			results.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return FXCollections.observableList(profileSet);
		
	}	
	
/*--- TRANSACTION ---------------------------------------------------------------------------*/
	
	public ObservableList<Transaction> getTransactions(int userID) {
		List<Transaction> transactionSet = new ArrayList<Transaction>();
		String getTrans = "SELECT * FROM TRANS WHERE USER_ID=?;";
		
		try {
			PreparedStatement queryTrans = c.prepareStatement(getTrans);
			queryTrans.setInt(1, userID);
			ResultSet results = queryTrans.executeQuery();
			
			while(results.next()) {
				int id = results.getInt("ID");
				int uId = results.getInt("USER_ID"); //User foreign key
				LocalDate type = results.getDate("TRANSTIME").toLocalDate();
				String purpose = results.getString("DESCRIPTION");
				double ammount = results.getDouble("AMOUNT");
				transactionSet.add(new Transaction(id, uId, type, purpose, ammount));
			}
			queryTrans.close();
			results.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return FXCollections.observableList(transactionSet);
		
	}
	
	/*public ObservableList<Transaction> getTransactions() {
		List<Transaction> transactions = new ArrayList<Transaction>();
		String getTransactions = "SELECT * FROM TRANS;";
		
		try {
			ResultSet results = c.prepareStatement(getTransactions).executeQuery();
			while(results.next()) {
				int id = results.getInt("ID");
				int uId = results.getInt("USER_ID"); //User foreign key
				LocalDate type = results.getDate("TRANSTIME").toLocalDate();
				String purpose = results.getString("DESCRIPTION");
				double ammount = results.getDouble("AMOUNT");
				transactions.add(new Transaction(id, uId, type, purpose, ammount));
			}
			results.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return FXCollections.observableList(transactions);
	}*/
	
	public boolean insertTransaction(Transaction transaction) {
		boolean success = false;
		String newTransaction = "INSERT INTO TRANS(DESCRIPTION, TRANSTIME, AMOUNT, USER_ID)"+
								"VALUES(?, ?, ?, ?);";
		
		try {
			PreparedStatement insertTrans = c.prepareStatement(newTransaction);
			insertTrans.setString(1, transaction.getDescription());
			insertTrans.setDate(2, Date.valueOf(transaction.getTime()));
			insertTrans.setDouble(3, transaction.getAmount());
			insertTrans.setInt(4, getOwner(transaction).getId());
			success = insertTrans.execute();
			insertTrans.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return success;
	}
	
	public boolean insertTransactions(List<Transaction> transactions) {
		boolean success = true;
		for (Transaction t: transactions)
			if (!insertTransaction(t))
				success = false;
		return success;
	}
	
	public boolean updateTransactions(List<Transaction> transactions) {
		boolean success = false;
		String updateTrans = "UPDATE TRANS SET DESCRIPTION=?, TRANSTIME=?, AMOUNT=? WHERE ID=?;";
		
		try {
			for (Transaction t: transactions) {
				PreparedStatement transStmt = c.prepareStatement(updateTrans);
				transStmt.setString(1, t.getDescription());
				transStmt.setDate(2, Date.valueOf(t.getTime()));
				transStmt.setDouble(3, t.getAmount());
				transStmt.setInt(4, t.getID());
				success = transStmt.execute();
				transStmt.close();
			}
		} catch (SQLException ex) {
			System.err.println("Error");
			ex.printStackTrace();
		}
		return success;
	}
	
	public boolean deleteTransactions(List<Transaction> transactions) {
		boolean success = true;
		for (Transaction t: transactions) {
			System.out.println(t.getDescription());
			if (!deleteTransaction(t.getID()))
				success = false;
		}
		return success;
	}
	
	public boolean deleteTransaction(Transaction transaction) {
		return deleteTransaction(transaction.getID());
	}
	
	public boolean deleteTransaction(int id) {
		boolean success = false;
		String delTrans = "DELETE FROM TRANS WHERE ID=?;";
		try (
			PreparedStatement delTransStmt = c.prepareStatement(delTrans);
		) {
			delTransStmt.setInt(1, id);
			success = delTransStmt.execute();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return success;
	}
	
/*--- ADMIN ---------------------------------------------------------------------------*/
	
	public void insertAdmin(CsAdmin admin) {
		insertAdmin(admin.getLogin(), admin.getPassword());
	}
	
	public void insertAdmin(String login, String password) {
		String newAdmin = "INSERT INTO ADMIN(LOGIN, PASSWORD)"+
						  "VALUES(?, ?);";
		
		try (
			PreparedStatement insertAdmin = c.prepareStatement(newAdmin);		
		) {
			insertAdmin.setString(1, login);
			insertAdmin.setString(2, password);
			insertAdmin.execute();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public CsAdmin getAdmin() {
		CsAdmin admin = null;
		String getAdmin = "SELECT * FROM ADMIN";
		
		try (
			Statement queryAdmin = c.createStatement();
			ResultSet results = queryAdmin.executeQuery(getAdmin);
		) {
			if (results.next()) {
				admin = new CsAdmin(results.getString("LOGIN"), results.getString("PASSWORD"), "Robyn", "Berg");
				admin.setUsers(getProfiles());
				for (Profile p: admin.getUsers()) {
					p.setTransactions(getTransactions(p.getId()));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return admin;
	}
	
	@Deprecated
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
	      /*DatabaseKit db = new DatabaseKit();
	      
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
	      System.out.println("Closed.\n");*/
	  }

}