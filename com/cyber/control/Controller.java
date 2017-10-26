package com.cyber.control;

import com.cyber.control.MasterController.Controllers;
import com.cyber.view.MainFrame.Panels;

public abstract class Controller extends Thread {
	private Controllers id;
	private Panels panelID;
	
	public Controller() {}
	
	protected Controller(Controllers id, String panelName) {
		this.id = id;
		this.panelID = Panels.valueOf(panelName);
	}
	
	protected Controllers getID() {return id;}
	protected Panels getPanelID() {return panelID;}
	
	public abstract void run();
}
