package com.cyber.control;

import com.cyber.model.Model;
import com.cyber.view.LoginPanel;
import com.cyber.view.Message;

public class LoginControl extends MasterControl {
	
	private Model model;
	private LoginPanel loginPnl;
	
	public LoginControl(Model model, LoginPanel loginPnl) {
		this.model = model;
		this.loginPnl = loginPnl;
	}

	@Override
	public void start() {
		loginPnl.addLoginListener(a -> {
			String[] loginInput = loginPnl.getInput();
			if (model.login(loginInput[0], loginInput[1]))
				super.switchTo(Controls.TRANS);
			else
				Message.showError(loginPnl, "Invalid login info.");
		});
		loginPnl.addCreateAccountListener(a -> {
			super.switchTo(Controls.ACCOUNT);
		});
	}

}
