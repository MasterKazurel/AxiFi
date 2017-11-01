package cyber.view;

import javax.swing.JButton;
import javax.swing.JTextArea;

import cyber.testing.Testing;
import cyber.view.MainFrame.Panels;

public class AccountPanel extends CardPanel {
	private static final long serialVersionUID = 1L;
	
	private JTextArea nameArea, infoArea, userArea, passArea;
	private JButton updateBtn;
	private ViewPanel viewPnl;
	private EditPanel editPnl;
	
/*----------------------------------------------------------------------------------------------*/
	public AccountPanel() {
		super();
	}
	
	@Override
	protected void create() {
		viewPnl = new ViewPanel();
		editPnl = new EditPanel();
	}

	@Override
	protected void setup() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void add() {
		addCard(viewPnl, Panels.VIEW.val());
		addCard(editPnl, Panels.EDIT.val());
	}
	
/*----------------------------------------------------------------------------------------------*/
	public ViewPanel viewPanel() {
		show(Panels.VIEW.val());
		return viewPnl;
	}
	
	public EditPanel editPanel() {
		show(Panels.EDIT.val());
		return editPnl;
	}
	
/*----------------------------------------------------------------------------------------------*/
	// Unit Testing
	public static void main(String[] args) {
		Testing.frame(new AccountPanel());
	}

}
