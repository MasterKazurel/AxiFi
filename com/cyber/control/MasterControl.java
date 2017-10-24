package com.cyber.control;

import com.cyber.model.Model;
import com.cyber.view.MainFrame;
import com.cyber.view.MainFrame.Panels;

public class MasterControl implements Controller {
	
	static enum Controls {
		LOGIN("login"),
		ACCOUNT("acc"),
		TRANS("trans"),
		CREATE("create");
		private String val;
		Controls (String val) {this.val = val;}
		String val(){return val;}
	}

	protected Model model;
	private ControlManager cm;
	
	public void start() {
		model = new Model();
		cm = new ControlManager(new MainFrame());
		cm.addControl(new LoginControl(model, cm.frame().getLoginPanel()), Controls.LOGIN.val, Panels.LOGIN.val());
		cm.addControl(new AccountControl(model, cm.frame().getAccountPanel()), Controls.ACCOUNT.val, Panels.ACCOUNT.val());
		cm.addControl(new TransactionControl(model, cm.frame().getTransactionPanel()), Controls.TRANS.val, Panels.TRANS.val());
		cm.addControl(new CreationControl(model, cm.frame().getCreationPanel()), Controls.CREATE.val, Panels.CREATE.val());
		cm.start(Controls.LOGIN.val, false);
	}
	
	protected void switchTo(Controls name) {
		cm.start(name.val, true);
	}
	
}
