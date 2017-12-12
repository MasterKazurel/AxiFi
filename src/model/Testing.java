package model;

import java.time.LocalDate;

public class Testing {

	public static CsAdmin fakeDB(DatabaseKit db) {
		/*Profile trish = new Profile("Trish", "Duce", "csci323");
		trish.getTransactions().add(new Transaction(LocalDate.now(), "Party hats", 1000.00));
		trish.getTransactions().add(new Transaction(LocalDate.now(), "T-shirts", 100.00));
		trish.getTransactions().add(new Transaction(LocalDate.now(), "Cups", 23.00));
		Profile michael = new Profile("Michael", "Cassens", "csci323");
		michael.getTransactions().add(new Transaction(LocalDate.now(), "Fireworks", 100.00));
		michael.getTransactions().add(new Transaction(LocalDate.now(), "Bouncy castle", 1000.00));
		michael.getTransactions().add(new Transaction(LocalDate.now(), "Pencils", 20.00));
		Profile rob = new Profile("Rob", "Smith", "csci323");
		rob.getTransactions().add(new Transaction(LocalDate.now(), "Chairs", 50.00));
		rob.getTransactions().add(new Transaction(LocalDate.now(), "Ghost masks", 20.00));
		rob.getTransactions().add(new Transaction(LocalDate.now(), "Lots of erasors", 2000.00));
		Profile oliver = new Profile("Oliver", "Serang", "csci323");
		oliver.getTransactions().add(new Transaction(LocalDate.now(), "Penguin statue", 200.00));
		oliver.getTransactions().add(new Transaction(LocalDate.now(), "Binders", 20.00));
		oliver.getTransactions().add(new Transaction(LocalDate.now(), "Confetti", 30.00));
		admin.getUsers().add(trish);
		admin.getUsers().add(michael);
		admin.getUsers().add(rob);
		admin.getUsers().add(oliver);*/
		
		db.init();
		db.deleteDatabase();
		
		db.init();
		db.buildSchema();
		db.insertAdmin("csadmin", "csci323");
		db.insertProfile(new Profile(1, "Trish", "Duce", "csci323"));
		db.insertProfile(new Profile(2, "Michael", "Cassens", "csci323"));
		db.insertProfile(new Profile(3, "Oliver", "Serang", "csci323"));
		db.insertProfile(new Profile(4, "Rob", "Smith", "csci323"));
		
		db.insertTransaction(new Transaction(1, LocalDate.now(), "Party hats", 1000.00));
		db.insertTransaction(new Transaction(1, LocalDate.now(), "T-shirts", 100.00));
		db.insertTransaction(new Transaction(1, LocalDate.now(), "Cups", 23.00));

		db.insertTransaction(new Transaction(2, LocalDate.now(), "Fireworks", 100.00));
		db.insertTransaction(new Transaction(2, LocalDate.now(), "Bouncy castle", 1000.00));
		db.insertTransaction(new Transaction(2, LocalDate.now(), "Pencils", 20.00));

		db.insertTransaction(new Transaction(3, LocalDate.now(), "Chairs", 50.00));
		db.insertTransaction(new Transaction(3, LocalDate.now(), "Ghost masks", 20.00));
		db.insertTransaction(new Transaction(3, LocalDate.now(), "Lots of erasors", 2000.00));

		db.insertTransaction(new Transaction(4, LocalDate.now(), "Penguin statue", 200.00));
		db.insertTransaction(new Transaction(4, LocalDate.now(), "Binders", 20.00));
		db.insertTransaction(new Transaction(4, LocalDate.now(), "Confetti", 30.00));
		CsAdmin admin = db.getAdmin();
		admin.setUsers(db.getProfiles());
		for (Profile p: admin.getUsers())
			p.setTransactions(db.getTransactions(p.getId()));
		return admin;
	}
	
	public static void main (String[] args) {
		CsAdmin admin = fakeDB(new DatabaseKit());
		assert admin != null: admin;
		assert admin.getUsers() != null: admin.getUsers();
		assert !admin.getUsers().isEmpty(): admin.getUsers();
		assert admin.getUsers().stream().anyMatch(usr -> usr.getTransactions() == null): 
			admin.getUsers().stream().filter(usr -> usr.getTransactions() == null);
		assert admin.getUsers().stream().anyMatch(usr -> usr.getTransactions().isEmpty()): 
			admin.getUsers().stream().filter(usr -> usr.getTransactions().isEmpty());
	}
}
