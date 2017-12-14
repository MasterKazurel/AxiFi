package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class CsAdmin extends Observable {
        private StringProperty login;
        private StringProperty firstName;
        private StringProperty lastName;
        private StringProperty password;
        private ObservableList<Profile> usersObserver;
        private List<Profile> users;
        private ObservableList<Transaction> transactionsObserver;
        private List<Transaction> transactions;
        
        
		public CsAdmin(String login, String password, String firstName, String lastName) {
			setLogin(login);
			setPassword(password);
			setFirstName(firstName);
			setLastName(lastName);
			users = new ArrayList<Profile>();
			usersObserver = FXCollections.observableList(users);
			transactions = new ArrayList<Transaction>();
			aggregateTrans();
			transactionsObserver = FXCollections.observableList(transactions);
		}
		
		private void aggregateTrans() {
            for (Profile user: users) {
                transactionsObserver.addAll(user.getTransactions());
                user.getTransactions().addListener((ListChangeListener.Change<? extends Transaction> c) -> {
                    while (c.next()) {
                        if (c.wasAdded())
                        	transactionsObserver.addAll(c.getAddedSubList());
                        if (c.wasRemoved())
                        	transactionsObserver.removeAll(c.getRemoved());
                    }
                });
            }
        }

		public String getLogin() {
			return login.getValue();
		}

		public void setLogin(String login) {
			this.login = new SimpleStringProperty(login);
		}

		public String getFirstName() {
			return firstName.getValue();
		}

		public void setFirstName(String firstName) {
			this.firstName = new SimpleStringProperty(firstName);
		}

		public String getLastName() {
			return lastName.getValue();
		}

		public void setLastName(String lastName) {
			this.lastName = new SimpleStringProperty(lastName);
		}

		public String getPassword() {
			return password.getValue();
		}

		public void setPassword(String password) {
			this.password = new SimpleStringProperty(password);
		}

		public ObservableList<Profile> getUsers() {
			return usersObserver;
		}
		
		public void setUsers(List<Profile> users) {
			this.users = users;
			this.usersObserver = FXCollections.observableList(users);
			aggregateTrans();
		}
		
		public ObservableList<Transaction> getTransactions() {
			return transactionsObserver;
		}
		
}
