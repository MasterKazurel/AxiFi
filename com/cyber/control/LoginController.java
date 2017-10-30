package cyber.control;

import cyber.control.MasterControl.Controllers;
import cyber.model.Model;
import cyber.view.LoginPanel;

public class LoginController extends Controller<LoginPanel> {
	
	private LoginPanel panel;
	
	public LoginController(Controllers name, Model model, LoginPanel panel) {
		super(name, model, panel);
		this.panel = panel;
	}

	@Override
	public void run() {
		panel.addLoginListener(a -> {
			String[] loginInput = panel.getInput();
			MasterControl.switchTo(Controllers.ACCOUNT);
			/*if (model.login(loginInput[0], loginInput[1]))
				MasterControl.switchTo(Controllers.TRANS);
			else
				Message.showError(panel, "Invalid login info.");*/
		});
		panel.addCreateAccountListener(a -> {
			MasterControl.switchTo(Controllers.ACCOUNT);
		});
	}

}
