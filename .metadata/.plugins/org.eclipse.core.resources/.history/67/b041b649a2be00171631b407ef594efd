package cyber.control;

import cyber.control.MasterControl.Controllers;
import cyber.model.Model;
import cyber.view.MainFrame.Panels;
import cyber.view.Panel;

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
	
	@Override
	public abstract void run();
}
