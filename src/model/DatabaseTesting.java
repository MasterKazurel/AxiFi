package model;

public class DatabaseTesting {
	
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
