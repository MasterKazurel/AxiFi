package com.cyber.control;

import com.cyber.control.MasterControl.Controllers;
import com.cyber.model.Model;
import com.cyber.view.LoginPanel;
import com.cyber.view.Message;

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
			if (MasterControl.model.login(loginInput[0], loginInput[1]))
				MasterControl.switchTo(Controllers.ACCOUNT);
			else
				Message.showError(panel, "Invalid login info.");
		});
		panel.addCreateAccountListener(a -> {
			MasterControl.switchTo(Controllers.ACCOUNT);
		});
	}

}
