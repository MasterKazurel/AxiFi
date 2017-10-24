package com.cyber.control;

import com.cyber.model.Model;
import com.cyber.view.AccountPanel;

public class AccountControl extends MasterControl {
	
	private Model model;
	private AccountPanel panel;
	
	public AccountControl(Model model, AccountPanel panel) {
		this.model = model;
		this.panel = panel;
	}

	@Override
	public void start() {
		
	}

}
