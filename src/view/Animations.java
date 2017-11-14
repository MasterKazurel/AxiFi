package view;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class Animations {

	public static TranslateTransition shake(Node n) {
		TranslateTransition x = new TranslateTransition();
		x.setDuration(Duration.millis(50));
		x.setNode(n);
		x.setByY(5);
		x.setCycleCount(4);
		x.setAutoReverse(true);
		x.play();
		return x;
	}
	
}
