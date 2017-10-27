package com.cyber.control;

import java.util.ArrayList;
import java.util.List;

import com.cyber.model.Model;
import com.cyber.view.MainFrame;
import com.cyber.view.Panel;

public class MasterControl {
	
	protected static enum Controllers {
		LOGIN("LOGIN"),
		ACCOUNT("ACCOUNT"),
		TRANS("TRANS"),
		CREATE("CREATE");
		private String val;
		Controllers (String val) {this.val = val;}
		String val(){return val;}
		boolean is(Controllers c) {return c.equals(this);}
	}

	protected static Model model;
	private static MainFrame frame;
	private static List<Controller<?>> controllers;
	
	public static void run() {
		model = new Model();
		frame = new MainFrame();
		controllers = new ArrayList<Controller<?>>();
		controllers.add(new LoginController(Controllers.LOGIN, model, frame.getLoginPanel()));
		controllers.add(new AccountController(Controllers.ACCOUNT, model, frame.getAccountPanel()));
		controllers.add(new TransactionController(Controllers.TRANS, model, frame.getTransactionPanel()));
		controllers.add(new CreationController(Controllers.CREATE, model, frame.getCreationPanel()));
		switchTo(Controllers.LOGIN, false);
	}
	
	static void switchTo(Controllers name) {
		Controller<?> c = find(name);
		c.run();
		frame.show(c.getPanelID().val());
	}
	
	static void switchTo(Controllers name, boolean switchPanel) {
		if (!switchPanel)
			find(name).run();
		else
			switchTo(name);
	}
	
	private static Controller find(Controllers name) {
		for (Controller c: controllers)
			if (c.getID().is(name)) return c;
		return null;
	}
	
}
