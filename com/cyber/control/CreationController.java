package com.cyber.control;

import com.cyber.view.CreationPanel;
import com.cyber.view.MainFrame.Panels;

public class CreationController extends MasterController {
	
	private CreationPanel panel;

	public CreationController(Controllers name, CreationPanel panel) {
		super(name, panel.getName());
		this.panel = panel;
		super.setName("creationctrl");
	}

	@Override
	public void run() {
		
	}
	
}
