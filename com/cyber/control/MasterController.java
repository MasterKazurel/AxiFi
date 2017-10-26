package com.cyber.control;

import java.util.ArrayList;
import java.util.List;

import com.cyber.model.Model;
import com.cyber.view.MainFrame;
import com.cyber.view.Panel;

public class MasterController extends Controller {
	
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

	protected Model model;
	private MainFrame frame;
	private List<Controller> controllers;
	
	public MasterController() {
		super();
	}
	
	protected MasterController(Controllers id, String panelName) {
		super(id, panelName);
	}
	
	@Override
	public void run() {
		model = new Model();
		frame = new MainFrame();
		controllers = new ArrayList<Controller>();
		controllers.add(new LoginController(Controllers.LOGIN, frame.getLoginPanel()));
		controllers.add(new AccountController(Controllers.ACCOUNT, frame.getAccountPanel()));
		controllers.add(new TransactionController(Controllers.TRANS, frame.getTransactionPanel()));
		controllers.add(new CreationController(Controllers.CREATE, frame.getCreationPanel()));
		switchTo(Controllers.LOGIN, false);
	}
	
	protected void switchTo(Controllers name) {
		Controller c = find(name);
		c.start();
		frame.show(c.getPanelID().val());
		
	}
	
	protected void switchTo(Controllers name, boolean switchPanel) {
		if (!switchPanel)
			find(name).start();
		else
			switchTo(name);
	}
	
	private Controller find(Controllers name) {
		for (Controller c: controllers)
			if (c.getID().is(name)) return c;
		return null;
	}
	
}
