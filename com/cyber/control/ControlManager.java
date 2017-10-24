package com.cyber.control;

import java.util.ArrayList;
import java.util.List;

import com.cyber.view.MainFrame;

public class ControlManager {
	private MainFrame view;
	private List<Control> controls;
	
	private class Control {
		private String name;
		private String viewName;
		private Controller ctrl;
		
		public Control(String name, Controller c, String viewName) {
			this.name = name;
			this.viewName = viewName;
			this.ctrl = c;
		}
	}

	public ControlManager(MainFrame view) {
		this.view = view;
		controls = new ArrayList<Control>();
	}
	
	public void addControl(Controller control, String name, String viewName) {
		controls.add(new Control(name, control, viewName));
	}
	
	public void removeControl(String name) {
		controls.remove(find(name));
	}
	
	public <O> void start(String name, boolean showView) {
		Control c = find(name);
		c.ctrl.start();
		if (showView) view.show(c.viewName);
	}
	
	public <O> void start(String name) {
		Control c = find(name);
		c.ctrl.start();
		view.show(c.viewName);
	}
	
	public MainFrame frame() {return view;}
	
	private Control find(String name) {
		for (Control c: controls)
			if (c.name.equals(name)) return c;
		return null;
	}
}
