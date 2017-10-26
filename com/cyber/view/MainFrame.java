package com.cyber.view;

import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.UIManager;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private CardPanel cards;
	private LoginPanel loginPnl;
	private AccountPanel accPnl;
	private TransactionPanel transPnl;
	private CreationPanel createPnl;
	
	public enum Panels {
		LOGIN("LOGIN"),
		CREATE("CREATE"),
		EDIT("EDIT"),
		VIEW("VIEW"), 
		TRANS("TRANS"),
		ACCOUNT("ACCOUNT");
		private String val;
		
		private Panels(String val) {
			this.val = val;
		}
		
		public String val() {return val;}
		public boolean is(Panels panel) {
			return panel.equals(val);
		}
	}

	//creates the initial login JPanel in the MainPanel
	public MainFrame() {
		super("AxiFi");
		setupPanels();
		setupFrame();
	}
	
	private void setupPanels() {
		cards = CardPanel.createCardPanel();
		loginPnl = new LoginPanel();
		accPnl = new AccountPanel();
		transPnl = new TransactionPanel();
		createPnl = new CreationPanel();
		
		//Necessary for controller's identification
		loginPnl.setName(Panels.LOGIN.val());
		accPnl.setName(Panels.ACCOUNT.val());
		transPnl.setName(Panels.TRANS.val());
		createPnl.setName(Panels.CREATE.val());
		
		cards.addCard(loginPnl, Panels.LOGIN.val);
		cards.addCard(accPnl, Panels.ACCOUNT.val);
		cards.addCard(transPnl, Panels.TRANS.val);
		cards.addCard(createPnl, Panels.CREATE.val);
	}
	
	private void setupFrame() {
		//LAF
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e)
		{ e.printStackTrace(); }
		
		getContentPane().add(cards);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage("cyberdyne.png"));
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void show(String panel) {
		cards.show(panel);
	}
	
	public LoginPanel getLoginPanel() {return loginPnl;}
	public AccountPanel getAccountPanel() {return accPnl;}
	public TransactionPanel getTransactionPanel() {return transPnl;}
	public CreationPanel getCreationPanel() {return createPnl;}

}
