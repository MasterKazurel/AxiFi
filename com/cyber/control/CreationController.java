package com.cyber.control;

import com.cyber.control.MasterControl.Controllers;
import com.cyber.model.Model;
import com.cyber.view.CreationPanel;

public class CreationController extends Controller<CreationPanel> {
	
	private CreationPanel panel;

	public CreationController(Controllers name, Model model, CreationPanel panel) {
		super(name, model, panel);
		this.panel = panel;
	}

	@Override
	public void run() {
		
	}
	
}
