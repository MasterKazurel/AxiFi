package cyber.control;

import cyber.control.MasterControl.Controllers;
import cyber.model.Model;
import cyber.view.TransactionPanel;

public class TransactionController extends Controller<TransactionPanel> {
	
	private TransactionPanel panel;
	
	public TransactionController(Controllers name, Model model, TransactionPanel panel) {
		super(name, model, panel);
		this.panel = panel;
	}

	@Override
	public void run() {
		
	}

}
