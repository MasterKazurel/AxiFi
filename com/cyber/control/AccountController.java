package com.cyber.control;

import com.cyber.control.MasterControl.Controllers;
import com.cyber.model.Model;
import com.cyber.view.AccountPanel;

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
