package view;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Animations {
	private static List<AnimationTracker> timelines;
	
	static {
		timelines = new ArrayList<AnimationTracker>();
	}
	
	private static class AnimationTracker<A extends Animation> {
		private A animation;
		private Node node;
		private Stage stage;
		public AnimationTracker(A animation, Node node)
		{ this.animation = animation; this.node = node; }
		public AnimationTracker(A animation, Stage stage)
		{ this.animation = animation; this.stage = stage; }
	}
	
	// Used for tracking previous animations
	private static <A extends Animation> A animation(Class<A> clazz, Node n) {
		A x = null;
		for (AnimationTracker<A> a: timelines)
			if (a.node != null && a.node.equals(n))
				x = a.animation;
		if (x == null) {
			try {
				x = clazz.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
			timelines.add(new AnimationTracker<A>(x, n));
		}
		return x;
	}
	
	// Used for tracking previous animations
	private static <A extends Animation> A animation(Class<A> clazz, Stage stage) {
		A x = null;
		for (AnimationTracker<A> a: timelines)
			if (a.stage != null && a.stage.equals(stage))
				x = a.animation;
		if (x == null) {
			try {
				x = clazz.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
			timelines.add(new AnimationTracker<A>(x, stage));
		}
		return x;
	}
	
	public static ObjectProperty<EventHandler<ActionEvent>> shake(Node n) {
		Timeline x = animation(Timeline.class, n);
		if (x.getKeyFrames().isEmpty()) {
			x.setCycleCount(1);
			x.setAutoReverse(true);
			x.getKeyFrames().addAll(new KeyFrame(Duration.millis(0), new KeyValue(n.translateXProperty(), 0, Interpolator.LINEAR)),
				new KeyFrame(Duration.millis(10), new KeyValue(n.translateXProperty(), -7, Interpolator.EASE_BOTH)),
				new KeyFrame(Duration.millis(30), new KeyValue(n.translateXProperty(), 7, Interpolator.EASE_BOTH)),
				new KeyFrame(Duration.millis(70), new KeyValue(n.translateXProperty(), -7, Interpolator.EASE_BOTH)),
				new KeyFrame(Duration.millis(130), new KeyValue(n.translateXProperty(), 7, Interpolator.EASE_BOTH)),
				new KeyFrame(Duration.millis(210), new KeyValue(n.translateXProperty(), -7, Interpolator.EASE_BOTH)),
				new KeyFrame(Duration.millis(310), new KeyValue(n.translateXProperty(), 0, Interpolator.EASE_BOTH)));
		}
		x.play();
		return x.onFinishedProperty();
	}
	
	public static ObjectProperty<EventHandler<ActionEvent>> shake(Stage stage) {
		Timeline t = animation(Timeline.class, stage);
		final double start = stage.getX();
		DoubleProperty x = new SimpleDoubleProperty(stage.getX());
		stage.setX(x.doubleValue());
		
		t.getKeyFrames().addAll(new KeyFrame(Duration.millis(0), new KeyValue(x, start, Interpolator.LINEAR)),
				new KeyFrame(Duration.millis(10), new KeyValue(x, start - 7, Interpolator.EASE_BOTH)),
				new KeyFrame(Duration.millis(30), new KeyValue(x, start + 7, Interpolator.EASE_BOTH)),
				new KeyFrame(Duration.millis(70), new KeyValue(x, start - 7, Interpolator.EASE_BOTH)),
				new KeyFrame(Duration.millis(130), new KeyValue(x, start + 7, Interpolator.EASE_BOTH)),
				new KeyFrame(Duration.millis(210), new KeyValue(x, start - 7, Interpolator.EASE_BOTH)),
				new KeyFrame(Duration.millis(310), new KeyValue(x, start, Interpolator.EASE_BOTH)));
		x.addListener((obs, oldValue, newValue) -> {
			stage.setX(x.doubleValue());
		});
		t.play();
		stage.setX(start);
		return t.onFinishedProperty();
	}
	
	public static FadeTransition fadeIn(Node n) {
		return fadeIn(n, Duration.millis(200));
	}
	
	public static FadeTransition fadeIn(Node n, Duration duration) {
		FadeTransition x = animation(FadeTransition.class, n);
		x.setCycleCount(1);
		x.setDuration(duration);
		x.setFromValue(0.0);
		x.setToValue(1.0);
		x.setNode(n);
		x.play();
		return x;
	}
	
	public static FadeTransition fadeOut(Node n) {
		return fadeIn(n, Duration.millis(100));
	}
	
	public static FadeTransition fadeOut(Node n, Duration duration) {
		FadeTransition x = animation(FadeTransition.class, n);
		x.setCycleCount(1);
		x.setDuration(duration);
		x.setFromValue(1.0);
		x.setToValue(0.0);
		x.setNode(n);
		x.play();
		return x;
	}
	
	public static ScaleTransition shrinkOut(Node n) {
		return shrinkOut(n, Duration.millis(100));
	}
	
	public static ScaleTransition shrinkOut(Node n, Duration duration) {
		ScaleTransition x = animation(ScaleTransition.class, n);
		x.setCycleCount(1);
		x.setDuration(duration);
		x.setFromX(1.0);
		x.setToX(0.0);
		x.setFromY(1.0);
		x.setToY(0.0);
		x.play();
		return x;
	}
	
}
