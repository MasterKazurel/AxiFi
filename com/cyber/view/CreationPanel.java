package com.cyber.view;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import com.cyber.Testing.Testing;

public class CreationPanel extends Panel {
	private static final long serialVersionUID = 1L;
	
	private JLabel usernmLbl, passLbl, nameLbl, balanceLbl;
	private JButton submitBtn;
	private JFormattedTextField usernmFld, passFld, nameFld,  balanceFld;
	
/*----------------------------------------------------------------------------------------------*/
	
	public CreationPanel() {
		super();
	}
	
	@Override
	protected void create() {
		usernmLbl = new JLabel("Enter a username: ");
		usernmFld = new JFormattedTextField();
		
		passLbl = new JLabel("Enter a password: ");
		passFld = new JFormattedTextField();
		
		nameLbl = new JLabel("Your name: ");
		nameFld = new JFormattedTextField();
		
		balanceLbl = new JLabel("Initial deposit: ");
		balanceFld = new JFormattedTextField();
		
		submitBtn = new JButton("Create");
	}

	@Override
	protected void setup() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setPreferredSize(new Dimension(400, 500));
		setBorder(new EmptyBorder(new Insets(40, 100, 100, 100)));
		
		passFld.setName("password");
		
		usernmFld.setName("username");
		
		nameFld.setName("name");
		
		balanceFld.setName("initial deposit");
		balanceLbl.add(balanceFld);
		
		usernmFld.requestFocus();
	}

	@Override
	protected void add() {
		add(usernmLbl);
		add(usernmFld);
		add(Box.createRigidArea(new Dimension(0, 25)));
		
		add(passLbl);
		add(passFld);
		add(Box.createRigidArea(new Dimension(0, 25)));
		
		add(nameLbl);
		add(nameFld);
		add(Box.createRigidArea(new Dimension(0, 5)));
		
		add(balanceLbl);
		add(balanceFld);
		add(Box.createRigidArea(new Dimension(0, 25)));
		
		add(submitBtn);
	}
	
/*----------------------------------------------------------------------------------------------*/
	
	@Override
	public String[] getInput() {
		return new String[]{
			usernmFld.getText(),
			passFld.getText(),
			nameFld.getText(),
			balanceFld.getText()
		};
	}
	
	public void addCreationListener(ActionListener a) {
		submitBtn.addActionListener(a);
	}
	
	
/*----------------------------------------------------------------------------------------------*/
	
	// Unit Testing
	public static void main(String[] args) {
		Testing.frame(new CreationPanel());
	}
	
}
