

import java.io.FileNotFoundException;

import javax.swing.JFrame;

public class MainFrame {

	public static void main(String[] args) throws FileNotFoundException {
		// create the frame
		JFrame myFrame = new JFrame("AxiFi");
		// set up the close operation
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// create panel
		Creation Panel = new MainPanel();
		// add panel
		myFrame.getContentPane().add(Panel);
		// pack
		myFrame.pack();
		// set visibility to true
		myFrame.setVisible(true);
	}
}
