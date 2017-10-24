package com.cyber.view;

import java.awt.CardLayout;
import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.cyber.Testing.Testing;

public abstract class CardPanel extends Panel {
	private static final long serialVersionUID = 1L;
	
	private CardLayout cl = new CardLayout();
	
/*----------------------------------------------------------------------------------------------*/
	
	public CardPanel() {
		super();
		setLayout(cl);
	}
	
/*----------------------------------------------------------------------------------------------*/
	
	@Override
	public String[] getInput() {
		throw new UnsupportedOperationException();
	}
	
	public void addCard(Container card, String name) {
		add(card, name);
	}
	
	public void show(String name) {
		cl.show(this, name);
	}
	
	public static CardPanel createCardPanel() {
		return new NewCardPanel();
	}
	
	private static class NewCardPanel extends CardPanel {
		@Override protected void create() {}
		@Override protected void setup() {}
		@Override protected void add() {}
	}

/*----------------------------------------------------------------------------------------------*/
	
	// Unit Testing
	public static void main(String[] args) {
		CardPanel panel = new Testing.TestCardPanel();
		
		JPanel card1 = new JPanel(),
				card2 = new JPanel();
		JButton switchBtn1 = new JButton("Switch"),
				switchBtn2 = new JButton("Switch");
		card1.add(new JLabel("Card 1"));
		card1.add(switchBtn1);
		card2.add(new JLabel("Card 2"));
		card2.add(switchBtn2);
		
		panel.addCard(card1, "c1");
		panel.addCard(card2, "c2");
		
		switchBtn1.addActionListener(a -> panel.show("c2"));
		switchBtn2.addActionListener(a -> panel.show("c1"));
		
		Testing.frame(panel);
	}
	
}
