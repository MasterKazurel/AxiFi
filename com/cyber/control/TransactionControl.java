package com.cyber.control;

import com.cyber.model.Model;
import com.cyber.view.TransactionPanel;

public class TransactionControl extends MasterControl{
	private Model model;
	private TransactionPanel panel;
	
	public TransactionControl(Model model, TransactionPanel panel) {
		this.model = model;
		this.panel = panel;
	}

	@Override
	public void start() {
		
	}

}
