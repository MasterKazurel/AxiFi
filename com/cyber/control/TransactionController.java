package com.cyber.control;

import com.cyber.view.TransactionPanel;

public class TransactionController extends MasterController {
	
	private TransactionPanel panel;
	
	public TransactionController(Controllers name, TransactionPanel panel) {
		super(name, panel.getName());
		super.setName("transctrl");
		this.panel = panel;
	}

	@Override
	public void run() {
		
	}

}
