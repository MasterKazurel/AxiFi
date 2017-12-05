package cyber.control;

import cyber.control.MasterControl.Controllers;
import cyber.model.Model;
import cyber.view.CreationPanel;

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
