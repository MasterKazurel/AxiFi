package cyber.view;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import cyber.testing.Testing;
/**
 * To view and update account info.
 * @author Sean
 *
 */
public class ViewPanel extends Panel {
private static final long serialVersionUID = 1L;
	
	private JLabel topLbl, nameLbl, balanceLbl;
	private JButton updateBtn;
	private JFormattedTextField nameFld, balanceFld;
	
/*----------------------------------------------------------------------------------------------*/
	
	public ViewPanel() {
		super();
	}
	
	@Override
	protected void create() {
		topLbl = new JLabel("Your account: ");
		nameLbl = new JLabel("Name: ");
		nameFld = new JFormattedTextField();
		balanceLbl = new JLabel("Balance: ");
		balanceFld = new JFormattedTextField();
		updateBtn = new JButton("Update");
	}

	@Override
	protected void setup() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setPreferredSize(new Dimension(400, 500));
		setBorder(new EmptyBorder(new Insets(40, 100, 100, 100)));
	}

	@Override
	protected void add() {
		add(topLbl);
		add(Box.createRigidArea(new Dimension(0, 50)));

		add(nameLbl);
		add(nameFld);
		add(Box.createRigidArea(new Dimension(0, 25)));

		balanceLbl.add(balanceFld);
		add(balanceLbl);
		add(balanceFld);
		add(Box.createRigidArea(new Dimension(0, 25)));

		add(updateBtn);
	}
	
/*----------------------------------------------------------------------------------------------*/
	
	@Override
	public String[] getInput() {
		return new String[] {
			nameFld.getText(),
			balanceFld.getText()
		};
	}
	
	public void addEditListener(ActionListener a) {
		updateBtn.addActionListener(a);
	}
	
	public void setInitialName(String name) {
		nameFld.setValue(name);
	}
	
	
/*----------------------------------------------------------------------------------------------*/
	
	// Unit Testing
	public static void main(String[] args) {
		Testing.frame(new ViewPanel());
	}

}