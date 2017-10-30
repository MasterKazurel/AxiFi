package cyber.view;

import java.awt.Component;

import javax.swing.JOptionPane;

public class Message {
	private static String errorLog;
	
	public static void logError(String msg) {
		errorLog += msg + "\n";
	}
	
	public static void showErrors(Component parent) {
		if (errorLog != null && !errorLog.isEmpty()) {
			JOptionPane.showMessageDialog(parent, errorLog, "Errors", JOptionPane.ERROR_MESSAGE);
			errorLog = "";
		}
	}
	
	public static void showError(Component parent, String msg) {
		JOptionPane.showMessageDialog(parent, msg, "Error", JOptionPane.ERROR_MESSAGE);
	}
}
