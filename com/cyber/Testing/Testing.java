package com.cyber.Testing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.cyber.model.Account;
import com.cyber.model.Account.Level;
import com.cyber.model.Transaction;
import com.cyber.model.Transaction.Code;
import com.cyber.view.CardPanel;
import com.cyber.view.MainFrame.Panels;

public class Testing {
	
	public static void main(String[] args) {
		assert Panels.valueOf("login").is(Panels.LOGIN): Panels.valueOf("login");
	}

	public static Account dummyAccount() {
		List<Transaction> ts = new ArrayList<Transaction>();
		ts.add(new Transaction(new Date(), 0.00, "shoes", Code.BLANK));
		ts.add(new Transaction(new Date(), 5.00, "bling", Code.BLANK));
		ts.add(new Transaction(new Date(), 2000000.00, "Lambo", Code.BLANK));
		return new Account(new Date(), "Admin", "csadmin", "csci323", 100.00, Level.ADMIN, ts);
	}
	
	// For easy UI testing
	public static void frame(JPanel panel) {
		JFrame frame = new JFrame("ViewPanel test");
		frame.getContentPane().add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public static class TestCardPanel extends CardPanel {

		@Override
		protected void create() {
			
		}

		@Override
		protected void setup() {
			
		}

		@Override
		protected void add() {
			
		}
		
	}
	
}
