package cyber.view;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import cyber.model.Account;
import cyber.testing.Testing;

public class LoginPanel extends Panel {
	private static final long serialVersionUID = 1L;

	private JLabel userLbl, passLbl, compName;
	private JButton loginBtn, createAccBtn;
	private JTextField usernmFld;
	private JPasswordField passPwFld;
	private JPanel loginPnl;
	private JComboBox<Account> accsBox;
	
/*----------------------------------------------------------------------------------------------*/
	public LoginPanel() {
		super();
	}
	
	@Override
	protected void create() {
		compName = new JLabel("AxiFi: Developed by Cyberdyne");
		loginBtn = new JButton("Login");
		createAccBtn = new JButton("Sign up");
		usernmFld = new JTextField(15);
		passPwFld = new JPasswordField(15);
		userLbl = new JLabel("Username: ");
		passLbl = new JLabel("Password: ");
		accsBox = new JComboBox<Account>();
	}

	@Override
	protected void setup() {
		loginPnl = new JPanel();
		loginPnl.setPreferredSize(new Dimension(400, 500));
		loginPnl.setBorder(new EmptyBorder(new Insets(100, 100, 100, 100)));
		loginPnl.setLocation(500, 280);
		loginPnl.setLayout(null);

		compName.setBounds(120, 10, 200, 20);
		userLbl.setBounds(100, 40, 200, 20);
		passLbl.setBounds(100, 90, 200, 20);
		usernmFld.setBounds(100, 60, 200, 20);
		passPwFld.setBounds(100, 110, 200, 20);
		loginBtn.setBounds(140, 150, 120, 20);
		createAccBtn.setBounds(100, 185, 200, 20);
	}

	@Override
	protected void add() {
		loginPnl.add(compName);
		loginPnl.add(createAccBtn);
		loginPnl.add(loginBtn);
		loginPnl.add(usernmFld);
		loginPnl.add(passPwFld);
		loginPnl.add(userLbl);
		loginPnl.add(passLbl);
		add(loginPnl);
	}
	
/*----------------------------------------------------------------------------------------------*/
	@Override
	public String[] getInput() {
		return new String[] {
			usernmFld.getText(), 
			String.valueOf(passPwFld.getPassword())
		};
	}
	
	public void addLoginListener(ActionListener a) {
		usernmFld.addActionListener(a);
		passPwFld.addActionListener(a);
		loginBtn.addActionListener(a);
	}
	
	public void addAccountsBoxListener(ActionListener a) {
		accsBox.addActionListener(a);
	}
	
	public void addCreateAccountListener(ActionListener a) {
		createAccBtn.addActionListener(a);
	}
	
	public void showAccounts(List<Account> accs) {
		accsBox.setVisible(true);
		accs.forEach(a -> accsBox.addItem(a));
	}
	
	
/*----------------------------------------------------------------------------------------------*/
	
	// Unit Testing
	public static void main(String[] args) {
		Testing.frame(new LoginPanel());
	}
	
}