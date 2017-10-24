package com.cyber.view;

import javax.swing.JPanel;

import com.cyber.control.MasterControl;

public abstract class Panel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public Panel() {
		init();
	}
	
	public void init() {
		create();
		setup();
		add();
	}

	protected abstract void create();
	protected abstract void setup();
	protected abstract void add();
	public abstract String[] getInput();
}
