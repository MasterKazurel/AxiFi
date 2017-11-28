package model;

public class Testing {

	public static void fakeAdmin(CsAdmin admin) {
		Profile trish = new Profile("Trish", "Duce", "csci323");
		trish.getTransactions().add(new Transaction("10/01/17", "T-shirts", "$100.00"));
		trish.getTransactions().add(new Transaction("10/23/17", "Cups", "$23.00"));
		trish.getTransactions().add(new Transaction("11/02/17", "Party hats", "$1000.00"));
		Profile michael = new Profile("Michael", "Cassens", "csci323");
		michael.getTransactions().add(new Transaction("11/02/17", "Fireworks", "$100.00"));
		michael.getTransactions().add(new Transaction("11/02/17", "Bouncy castle", "$1000.00"));
		michael.getTransactions().add(new Transaction("11/17/17", "Pencils", "$20.00"));
		Profile rob = new Profile("Rob", "Smith", "csci323");
		rob.getTransactions().add(new Transaction("11/22/17", "Chairs", "$50.00"));
		rob.getTransactions().add(new Transaction("11/17/17", "Ghost masks", "$20.00"));
		rob.getTransactions().add(new Transaction("11/17/17", "Lots of erasors", "$2000.00"));
		Profile oliver = new Profile("Oliver", "Serang", "csci323");
		oliver.getTransactions().add(new Transaction("11/17/17", "Penguin statue", "$200.00"));
		oliver.getTransactions().add(new Transaction("11/17/17", "Binders", "$20.00"));
		oliver.getTransactions().add(new Transaction("11/17/17", "Confetti", "$30.00"));
		admin.getUsers().add(trish);
		admin.getUsers().add(michael);
		admin.getUsers().add(rob);
		admin.getUsers().add(oliver);
	}
}
