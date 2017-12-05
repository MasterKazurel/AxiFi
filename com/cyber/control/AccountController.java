package cyber.control;

import cyber.control.MasterControl.Controllers;
import cyber.model.Model;
import cyber.view.AccountPanel;

public class AccountController extends Controller<AccountPanel> {
	
	private AccountPanel panel;
	
	public AccountController(Controllers name, Model model, AccountPanel panel) {
		super(name, model, panel);
		this.panel = panel;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
