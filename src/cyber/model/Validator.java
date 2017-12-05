package cyber.model;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import cyber.view.Message;

public class Validator {
	
	/** Deprecated, use {@link #validateInput(String, String, JTextComponent...)} instead.
	 * 
	 * @param height
	 * @param weight
	 * @param goal
	 * @return
	 */
	@Deprecated
	public boolean checkNumInput(JTextField height, JTextField weight, JTextField goal) {
		boolean broke = false;
		if (weight.getText().equals("") || !(Pattern.matches("\\d\\d\\d", weight.getText()))) {
			JOptionPane.showMessageDialog(null, "Invalid weight");
			weight.setFocusable(true);
			broke = true;
		} else if (height.getText().equals("") || !(Pattern.matches("\\d\\d", height.getText()))) {
			JOptionPane.showMessageDialog(null, "Invalid height");
			height.setFocusable(true);
			broke = true;
		} else if (goal.getText().equals("") || !(Pattern.matches("\\d\\d\\d", goal.getText()))) {
			JOptionPane.showMessageDialog(null, "Invalid goal. Please be healthy!");
			goal.setFocusable(true);
			broke = true;
		}
		return broke;
	}

	//checks user input in the other text boxes for making an account
	@Deprecated
	public boolean validateWordInput(JTextField userField, JTextField passField, JTextField name) {
		boolean valid = false;
		if (userField.getText().equals("") || !(Pattern.matches("^[a-zA-Z]+$$", userField.getText()))) {
			JOptionPane.showMessageDialog(null, "Username has invalid characters! Only A-Z allowed.");
			userField.setFocusable(true);
			valid = false;

		} else if (passField.getText().equals("") || !(Pattern.matches("^[a-zA-Z]+$$", passField.getText()))) {
			JOptionPane.showMessageDialog(null, "Password has invalid characters! Only A-Z allowed.");
			passField.setFocusable(true);
			valid = false;

		} else if (name.getText().equals("") || !(Pattern.matches("^[a-zA-Z]+$$", name.getText()))) {
			JOptionPane.showMessageDialog(null, "Name has invalid characters! Only A-Z allowed.");
			name.setFocusable(true);
			valid = false;
		}
		return valid;
	}
	
	public static boolean validateInput(String regex, String errorMessage, JTextComponent... fields) {
		boolean valid = true;
		for (JTextComponent f: fields) {
			if (f.getText() == null || f.getText().trim().isEmpty()) {
				Message.logError("Empty field: " + f.getName());
				valid = false;
			} else if (Pattern.matches(regex, f.getText())) {
				Message.logError("Invalid input for: " + f.getName() + "; " + errorMessage);
				valid = false;
			}
			if (!valid) f.requestFocus();
		}
		Message.showErrors(null);
		return valid;
	}
	
}
