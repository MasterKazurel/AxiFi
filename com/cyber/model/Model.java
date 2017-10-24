package com.cyber.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.cyber.Testing.Testing;
import com.cyber.model.Account.Level;

public class Model {
	private Account user;
	private List<Account> users;
	
	public Model() {
		
	}
	
	public Account user() {return user;}
	
	public List<Account> users(Account a) {
		if (a.getLvl().is(Level.ADMIN))
			return users;
		else return null;
	}
	
	private <Entity extends Storeable<Entity>> Entity load(String fileName) {
		ObjectInputStream ois;
		Entity e = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(fileName + ".ser"));
			Object o = ois.readObject();
			e = (Entity)o;
			ois.close();
		} catch (ClassNotFoundException | IOException | ClassCastException ex) {
			ex.printStackTrace();
		}
		return e;
	}
	
	public AbstractTableModel setupTableModel(Account a) {
		return new TableModel();
	}
	
	public boolean login (String username, String password) {
		boolean success = false;
		Account a = load(username);
		if (a.getPassword().equals(password)) {
			user = a;
			success = true;
		}
		return success;
	}
	
	public static void main(String[] args) {
		Account a = Testing.dummyAccount();
		Model m = new Model();
		a.save();
		m.load(a.getUsername());
		assert a.equals(m.user()): m.user();
	}
	
}