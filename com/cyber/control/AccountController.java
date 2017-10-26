package com.cyber.control;

import com.cyber.view.AccountPanel;
import com.cyber.view.MainFrame.Panels;

public class AccountController extends MasterController {
	
	private AccountPanel panel;
	
	public AccountController(Controllers name, AccountPanel panel) {
		super(name, panel.getName());
		this.panel = panel;
		super.setName("accctrl");
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
