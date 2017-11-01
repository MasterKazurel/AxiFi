package cyber.view;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import cyber.model.Model;
import cyber.testing.Testing;

public class TransactionPanel extends Panel {
	private static final long serialVersionUID = 1L;
	
	private JTable table;
	private JScrollPane scrollPane;
	
/*----------------------------------------------------------------------------------------------*/

	public TransactionPanel() {
		super();
	}

	@Override
	public void init() {
		
		
	}
	
	@Override
	protected void create() {
		table = new JTable();
		scrollPane = new JScrollPane();
	}

	@Override
	protected void setup() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void add() {
		scrollPane.add(table);
		
		add(scrollPane);
	}
	
	
/*----------------------------------------------------------------------------------------------*/
	
	@Override
	public String[] getInput() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setTableModel(AbstractTableModel m) {
		table.setModel(m);
	}
	
	
/*----------------------------------------------------------------------------------------------*/
	
	// Unit Testing
	public static void main(String[] args) {
		TransactionPanel panel = new TransactionPanel();
		panel.setTableModel(new Model().setupTableModel(Testing.dummyAccount()));
		Testing.frame(panel);
	}
	
}
