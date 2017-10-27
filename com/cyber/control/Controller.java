package com.cyber.control;

import com.cyber.control.MasterControl.Controllers;
import com.cyber.model.Model;
import com.cyber.view.MainFrame.Panels;
import com.cyber.view.Panel;

public abstract class Controller<Pnl extends Panel> implements Runnable {
	private Controllers id;
	protected Panel panel;
	protected Model model;
	
	protected Controller(Controllers id, Model model, Panel panel) {
		this.id = id;
		this.panel = panel;
	}
	
	Controllers getID() {return id;}
	Panels getPanelID() {return Panels.valueOf(panel.getName());}
	
	public abstract void run();
}
