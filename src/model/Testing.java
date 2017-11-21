package model;

import java.time.LocalDate;

public class Testing {

	public static void fakeAdmin(CsAdmin admin) {
		Profile trish = new Profile("Trish", "Duce", "csci323");
		trish.getTransactions().add(new Transaction(LocalDate.now(), 100.00, "T-shirts"));
		trish.getTransactions().add(new Transaction(LocalDate.now(), 23.00, "Cups"));
		trish.getTransactions().add(new Transaction(LocalDate.now(), 1000.00, "Party hats"));
		Profile michael = new Profile("Michael", "Cassens", "csci323");
		michael.getTransactions().add(new Transaction(LocalDate.now(), 100.00, "Fireworks"));
		michael.getTransactions().add(new Transaction(LocalDate.now(), 1000.00, "Bouncy castle"));
		michael.getTransactions().add(new Transaction(LocalDate.now(), 20.00, "Pencils"));
		Profile rob = new Profile("Rob", "Smith", "csci323");
		rob.getTransactions().add(new Transaction(LocalDate.now(), 50.00, "Chairs"));
		rob.getTransactions().add(new Transaction(LocalDate.now(), 20.00, "Ghost masks"));
		rob.getTransactions().add(new Transaction(LocalDate.now(), 2000.00, "Lots of erasors"));
		Profile oliver = new Profile("Oliver", "Serang", "csci323");
		oliver.getTransactions().add(new Transaction(LocalDate.now(), 200.00, "Penguin statue"));
		oliver.getTransactions().add(new Transaction(LocalDate.now(), 20.00, "Binders"));
		oliver.getTransactions().add(new Transaction(LocalDate.now(), 30.00, "Confetti"));
		admin.getUsers().add(trish);
		admin.getUsers().add(michael);
		admin.getUsers().add(rob);
		admin.getUsers().add(oliver);
	}
}
