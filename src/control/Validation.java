package control;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import javafx.scene.Node;
import javafx.scene.control.Label;

public class Validation<N extends Node> {
	private Label lbl;
	private String origTxt,
					origStyle;
	private N n;
	private List<Predicate<N>> tests;
	private List<String> errorMsgs;
	
	Validation(Label lbl, N n, Predicate<N> test, String errorMsg) {
		this.lbl = lbl;
		origTxt = lbl.getText();
		origStyle = lbl.getStyle();
		this.n = n;
		this.tests = new ArrayList<Predicate<N>>(Arrays.asList(test));
		this.errorMsgs = new ArrayList<String>(Arrays.asList(errorMsg));
	}
	
	Validation<N> add(Predicate<N> test, String errorMsg) {
		tests.add(test);
		errorMsgs.add(errorMsg);
		return this;
	}
	
	boolean test() {
		boolean pass = true;
		for (int i = 0; i < tests.size(); i++)
			if (tests.get(i).test(n)) {
				showError(errorMsgs.get(i));
				pass = false;
			} else clearLbl();
		return pass;
	}
	
	private void showError(String msg) {
		if (!lbl.isVisible()) {
			lbl.setVisible(true);
			lbl.setText("");
		}
		lbl.setText(msg);
		lbl.setStyle("-fx-text-fill: red; "); 
	}
	
	private void clearLbl() {
		lbl.setText(origTxt);
		lbl.setStyle(origStyle);
		System.out.println(origTxt);
	}
	
	static void setupOnFocusLost(Validation<?>... vs) {
		for (Validation<?> v: vs)
			v.n.focusedProperty().addListener((obs, lost, gained) -> {
				if (lost)
					v.test();
			});
	}
	
	static boolean run(Validation<?>... vs) {
		boolean pass = true;
		for (Validation<?> v: vs)
			if (!v.test()) 
				pass = false;
			else v.clearLbl();
		return pass;
	}
}
