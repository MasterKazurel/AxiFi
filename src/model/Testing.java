package model;

public class Testing {

	public static void fakeAdmin(CsAdmin admin) {
		Profile trish = new Profile("Trish", "Duce", "csci323");
		trish.getTransactions().add(new Transaction("10/01/17", "$100.00", "T-shirts"));
		trish.getTransactions().add(new Transaction("10/23/17", "$23.00", "Cups"));
		trish.getTransactions().add(new Transaction("11/02/17", "$1000.00", "Party hats"));
		Profile michael = new Profile("Michael", "Cassens", "csci323");
		michael.getTransactions().add(new Transaction("11/02/17", "$100.00", "Fireworks"));
		michael.getTransactions().add(new Transaction("11/02/17", "$1000.00", "Bouncy castle"));
		michael.getTransactions().add(new Transaction("11/17/17", "$20.00", "Pencils"));
		Profile rob = new Profile("Rob", "Smith", "csci323");
		rob.getTransactions().add(new Transaction("11/22/17", "$50.00", "Chairs"));
		rob.getTransactions().add(new Transaction("11/17/17", "$20.00", "Ghost masks"));
		rob.getTransactions().add(new Transaction("11/17/17", "$2000.00", "Lots of erasors"));
		Profile oliver = new Profile("Oliver", "Serang", "csci323");
		oliver.getTransactions().add(new Transaction("11/17/17", "$200.00", "Penguin statue"));
		oliver.getTransactions().add(new Transaction("11/17/17", "$20.00", "Binders"));
		oliver.getTransactions().add(new Transaction("11/17/17", "$30.00", "Confetti"));
		admin.getUsers().add(trish);
		admin.getUsers().add(michael);
		admin.getUsers().add(rob);
		admin.getUsers().add(oliver);
	}
}
