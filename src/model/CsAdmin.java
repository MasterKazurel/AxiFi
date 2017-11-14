package model;

import java.util.ArrayList;
import java.util.List;

public class CsAdmin{
        private String login;
        private String firstName;
        private String lastName;
        private String Password;
        private List<Transaction> transactions;
        
		public CsAdmin(String login, String password, String firstName, String lastName) {
			super();
			this.login = login;
			Password = password;
			this.firstName = firstName;
			this.lastName = lastName;
			setTransactions(new ArrayList<Transaction>());
		}

		public String getLogin() {
			return login;
		}

		public void setLogin(String login) {
			this.login = login;
		}

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public String getPassword() {
			return Password;
		}

		public void setPassword(String password) {
			Password = password;
		}

		public List<Transaction> getTransactions() {
			return transactions;
		}

		public void setTransactions(List<Transaction> transactions) {
			this.transactions = transactions;
		}                
		
}
