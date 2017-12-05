package model;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;

import model.*;

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
		
		Statement buildTable = null;
		try {
			//Load an sql statement to be executed
			buildTable = c.createStatement();
			
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
		
		Statement insertUser = null;
		try {
			//Load an sql statement to be executed
			insertUser = c.createStatement();
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
		
		Statement insertUser = null;
		try {
			//Load an sql statement to be executed
			insertUser = c.createStatement();
			insertUser.execute(newUser);
			insertUser.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void removeProfile(int id) {
		//Remove user based on users primary key id
		String rmUser = "DELETE FROM USER WHERE ID=" + id;
		Statement removeUser = null;
		try {
			removeUser = c.createStatement();
			removeUser.execute(rmUser);
			removeUser.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void insertTransaction(int userId, String purpose, String code, double ammount) {
		String newTransaction = "INSERT INTO TRANS(PURPOSE, TRANS_TYPE, AMMOUNT, USER_ID)"+
								"VALUES('" + purpose + "','" + code + "'," + ammount + "," + userId + ");"; 
		Statement insertTrans = null;
		try {
			insertTrans = c.createStatement();
			insertTrans.execute(newTransaction);
			insertTrans.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public ArrayList<Profile> getUsers() {
		ArrayList<Profile> profileSet = new ArrayList<Profile>();
		
		String getUsers = "SELECT * FROM USER;";
		Statement queryUsers = null;
		try {
			queryUsers = c.createStatement();
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
		return profileSet;
		
	}	
	public ArrayList<Transaction> getTransactions(int userId) {
		ArrayList<Transaction> transactionSet = new ArrayList<Transaction>();
		
		String getTransactions = "SELECT * FROM TRANS WHERE USER_ID=" + userId + ";";
		Statement queryTrans = null;
		try {
			queryTrans = c.createStatement();
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
		return transactionSet;
		
	}
	public void updateProfile(Profile profile) {
		ArrayList<Transaction> transactions = (ArrayList<Transaction>) profile.getTransactions();
		
		for(int i=0; i < transactions.size(); i++) {
			//TODO Update anything that was changed
			String updateTransactions = "UPDATE TRANS SET ";
		}
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
	public CsAdmin queryAdmin(String adm, String password) {
		//TODO this is not a query
		return new CsAdmin("csadmin", "csci323", "Robyn", "Berg");
	}
	public boolean adminLogin(String adm, String password) {
		String pwd = ""; //The queried password
		
		String checkAdmin = "SELECT PASSWORD FROM ADMIN WHERE LOGIN=" + "'" + adm + "';";
		
		Statement queryAdmin = null;
		try {
			queryAdmin = c.createStatement();
			ResultSet admQ = queryAdmin.executeQuery(checkAdmin);
				if(admQ.next()) {
					pwd = admQ.getString("PASSWORD");
				}
			queryAdmin.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		if(pwd.equals(password)) {
			//Login success
			return true;
		}
		return false;
	}
}