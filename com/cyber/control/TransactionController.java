package com.cyber.control;

import com.cyber.control.MasterControl.Controllers;
import com.cyber.model.Model;
import com.cyber.view.TransactionPanel;

public class TransactionController extends Controller<TransactionPanel> {
	
	private TransactionPanel panel;
	
	public TransactionController(Controllers name, Model model, TransactionPanel panel) {
		super(name, model, panel);
		this.panel = panel;
	}

	@Override
	public void run() {
		
	}

}
