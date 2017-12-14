package model;

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
	         c = DriverManager.getConnection("jdbc:sqlite:" + fileName);
	         c.setReadOnly(false);
	         this.fileName = fileName;
	      } catch ( Exception e ) {
	    	  e.printStackTrace();
	      }
	}
	
	public void init() {
	  init("data.db");
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
							  "DESCRIPTION TEXT," +
							  "TRANSTIME DATE," + 
							  "AMOUNT DECIMAL(8,2) NOT NULL," +
							  "USER_ID INTEGER NOT NULL," +
							  "FEE REAL NOT NULL," +
							  "FOREIGN KEY(USER_ID) REFERENCES USER(ID)" + 
							  ");";
		//System.out.println(mkUsrTable + mkAdmTable + mkTransTable);
		
		try (
			Statement buildTable = c.createStatement();		
		){
			buildTable.execute(mkUsrTable);
			buildTable.execute(mkAdmTable);
			buildTable.execute(mkTransTable);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
/*--- PROFILE ---------------------------------------------------------------------------*/

	public boolean insertProfile(Profile profile) {
		String newUser = "INSERT INTO USER(FIRSTNAME, LASTNAME, BALANCE)" +
							"VALUES (?,?,?);";
		
		try (
			PreparedStatement s = c.prepareStatement(newUser);	
		) {
			s.setString(1, profile.getFirstName());
			s.setString(2, profile.getLastName());
			s.setDouble(3, profile.getBalance());
			return s.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean insertProfiles(List<Profile> profiles) {
		boolean success = false;
		for (Profile p: profiles)
			if (!insertProfile(p))
				success = false;
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
				profile.getTransactions().addAll(getTransactions(profile.getId()));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return profile;
	}
	
	public boolean deleteProfile(Profile profile) {
		boolean success = false;
		String rmUser = "DELETE FROM USER WHERE ID=?;";
		try (
			PreparedStatement removeUser = c.prepareStatement(rmUser);
		) {
			removeUser.setInt(1, profile.getId());
			success = removeUser.execute();
			removeUser.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return success;
	}
	
	public boolean deleteProfiles(List<Profile> profiles) {
		boolean success = false;
		for (Profile p: profiles)
			if (!deleteProfile(p))
				success = false;
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
	
	public ObservableList<Profile> getUsers() {
		List<Profile> profileSet = new ArrayList<Profile>();
		
		String getUsers = "SELECT * FROM USER;";
		
		try (
			PreparedStatement queryUsers = c.prepareStatement(getUsers);
			ResultSet results = queryUsers.executeQuery();		
		){
			while(results.next()) {
				Profile newUser = new Profile(
						results.getInt("ID"),
						results.getString("FIRSTNAME"),
						results.getString("LASTNAME"),
						results.getDouble("BALANCE"));
				newUser.getTransactions().addAll(getTransactions(newUser.getId()));
				profileSet.add(newUser);
			}
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
		
		try (
			PreparedStatement queryTrans = c.prepareStatement(getTrans);		
		){
			queryTrans.setInt(1, userID);
			ResultSet results = queryTrans.executeQuery();
			
			while(results.next()) {
				int id = results.getInt("ID");
				int uId = results.getInt("USER_ID"); //User foreign key
				LocalDate type = results.getDate("TRANSTIME").toLocalDate();
				String purpose = results.getString("DESCRIPTION");
				double amount = results.getDouble("AMOUNT");
				double fees = results.getDouble("FEE");
				transactionSet.add(new Transaction(id, uId, type, purpose, amount, fees));
			}
			results.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return FXCollections.observableList(transactionSet);
		
	}
	
	public ObservableList<Transaction> getTransactions() {
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
				double fees = results.getDouble("FEE");
				transactions.add(new Transaction(id, uId, type, purpose, ammount, fees));
			}
			results.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return FXCollections.observableList(transactions);
	}
	
	public boolean insertTransaction(Transaction transaction) {
		boolean success = false;
		String newTransaction = "INSERT INTO TRANS(DESCRIPTION, TRANSTIME, AMOUNT, FEE, USER_ID)"+
								"VALUES(?, ?, ?,?, ?);";
		
		try (
			PreparedStatement insertTrans = c.prepareStatement(newTransaction);		
		){
			insertTrans.setString(1, transaction.getDescription());
			insertTrans.setDate(2, Date.valueOf(transaction.getTime()));
			insertTrans.setDouble(3, transaction.getAmount());
			insertTrans.setDouble(4, transaction.getFee());
			insertTrans.setInt(5, getOwner(transaction).getId());
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
	
	public boolean updateTransaction(Transaction t) {
		boolean success = false;
		String updateTrans = "UPDATE TRANS SET DESCRIPTION=?, TRANSTIME=?, AMOUNT=?, USER_ID=? WHERE ID=?;";
		try (
			PreparedStatement transStmt = c.prepareStatement(updateTrans);		
		){
			transStmt.setString(1, t.getDescription());
			transStmt.setDate(2, Date.valueOf(t.getTime()));
			transStmt.setDouble(3, t.getAmount());
			transStmt.setInt(4, t.getUserID());
			transStmt.setInt(5, t.getID());
			success = transStmt.execute();
		} catch (SQLException ex) {
			System.err.println("Error");
			ex.printStackTrace();
		}
		return success;
	}
	
	public boolean updateTransactions(List<Transaction> ts) {
		boolean success = true;
		for (Transaction t: ts)
			if (!updateTransaction(t))
				success = false;
		return success;
	}
	
	public boolean deleteTransactions(List<Transaction> transactions) {
		boolean success = true;
		for (Transaction t: transactions) {
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
	
	public boolean insertAdmin(String login, String password) {
		String newAdmin = "INSERT INTO ADMIN(LOGIN, PASSWORD)"+
						  "VALUES(?, ?);";
		
		try (
			PreparedStatement s = c.prepareStatement(newAdmin);		
		) {
			s.setString(1, login);
			s.setString(2, password);
			return s.execute();
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public CsAdmin getAdmin() {
		CsAdmin admin = new CsAdmin("csadmin", "csci323", "Robyn", "Berg");
		admin.setUsers(getUsers());
		return admin;
	}
	
	// Fake database (and unit testing)
	public static void main( String args[] ) {
		DatabaseKit db = new DatabaseKit();
		db.init();
		db.buildSchema();
		db.insertProfile(new Profile(1, "Trish", "Duce", 200.0));
		db.insertProfile(new Profile(2, "Michael", "Cassens", 200.0));
		db.insertProfile(new Profile(3, "Oliver", "Serang", 200.0));
		db.insertProfile(new Profile(4, "Rob", "Smith", 200.0));
		
		db.insertTransaction(new Transaction(1, LocalDate.now(), "Party hats", -1000.00, .12));
		db.insertTransaction(new Transaction(1, LocalDate.now(), "T-shirt Cannon", -100.00, .12));
		db.insertTransaction(new Transaction(1, LocalDate.now(), "Donation", 15.00, .12));
		db.insertTransaction(new Transaction(2, LocalDate.now(), "Fireworks", -100.00, .12));
		db.insertTransaction(new Transaction(2, LocalDate.now(), "Bouncy castle", -1000.00, .12));
		db.insertTransaction(new Transaction(2, LocalDate.now(), "Donation", 50.00, .12));
		db.insertTransaction(new Transaction(3, LocalDate.now(), "Lots of erasors", -100.00, .12));
		db.insertTransaction(new Transaction(3, LocalDate.now(), "Ghost masks", -20.00, .12));
		db.insertTransaction(new Transaction(3, LocalDate.now(), "Donation", 2000.00, .12));
		db.insertTransaction(new Transaction(4, LocalDate.now(), "Penguin statue", -200.00, .12));
		db.insertTransaction(new Transaction(4, LocalDate.now(), "Confetti", -20.00, .12));
		db.insertTransaction(new Transaction(4, LocalDate.now(), "Donation", 30.00, .12));
		
		CsAdmin admin = db.getAdmin();
		assert admin != null: admin;
		assert admin.getUsers() != null: admin.getUsers();
		assert !admin.getUsers().isEmpty(): admin.getUsers();
		assert admin.getUsers().stream().anyMatch(usr -> usr.getTransactions() == null): 
			admin.getUsers().stream().filter(usr -> usr.getTransactions() == null);
		assert admin.getUsers().stream().anyMatch(usr -> usr.getTransactions().isEmpty()): 
			admin.getUsers().stream().filter(usr -> usr.getTransactions().isEmpty());
	  }

}