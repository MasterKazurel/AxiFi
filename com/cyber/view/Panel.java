package cyber.view;

import javax.swing.JPanel;

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
