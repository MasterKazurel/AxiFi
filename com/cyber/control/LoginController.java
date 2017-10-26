package com.cyber.control;

import com.cyber.view.LoginPanel;
import com.cyber.view.MainFrame.Panels;
import com.cyber.view.Message;

public class LoginController extends MasterController {
	
	private LoginPanel panel;
	
	public LoginController(Controllers name, LoginPanel panel) {
		super(name, panel.getName());
		this.panel = panel;
	}

	@Override
	public void run() {
		panel.addLoginListener(a -> {
			String[] loginInput = panel.getInput();
			if (model.login(loginInput[0], loginInput[1]))
				switchTo(Controllers.TRANS);
			else
				Message.showError(panel, "Invalid login info.");
		});
		panel.addCreateAccountListener(a -> {
			switchTo(Controllers.ACCOUNT);
		});
	}

}
