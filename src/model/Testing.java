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
		

		db.initDatabase("data.db");
		db.deleteDatabase("data.db");
		
		db.initDatabase("data.db");
		db.buildSchema();
		db.insertAdmin("csadmin", "csci323");
		db.insertProfile(new Profile(1, "Trish", "Duce", "csci323"));
		db.insertProfile(new Profile(2, "Michael", "Cassens", "csci323"));
		db.insertProfile(new Profile(3, "Oliver", "Serang", "csci323"));
		
		db.insertTransaction(1, LocalDate.now().toString(), "Party hats", 1000.00);
		db.insertTransaction(1, LocalDate.now().toString(), "T-shirts", 100.00);
		db.insertTransaction(1, LocalDate.now().toString(), "Cups", 23.00);

		db.insertTransaction(2, LocalDate.now().toString(), "Fireworks", 100.00);
		db.insertTransaction(2, LocalDate.now().toString(), "Bouncy castle", 1000.00);
		db.insertTransaction(2, LocalDate.now().toString(), "Pencils", 20.00);

		db.insertTransaction(3, LocalDate.now().toString(), "Chairs", 50.00);
		db.insertTransaction(3, LocalDate.now().toString(), "Ghost masks", 20.00);
		db.insertTransaction(3, LocalDate.now().toString(), "Lots of erasors", 2000.00);

		db.insertTransaction(4, LocalDate.now().toString(), "Penguin statue", 200.00);
		db.insertTransaction(4, LocalDate.now().toString(), "Binders", 20.00);
		db.insertTransaction(4, LocalDate.now().toString(), "Confetti", 30.00);
		CsAdmin admin = db.getAdmin();
		admin.setUsers(db.getUsers());
		for (Profile p: admin.getUsers())
			p.setTransactions(db.getTransactions(p.getId()));
		return admin;
	}
	
	public static void main (String[] args) {
		fakeDB(new DatabaseKit());
	}
}
